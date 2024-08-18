package com.jorshbg.practiceapispring.service;

import com.jorshbg.practiceapispring.dto.requests.TagDTO;
import com.jorshbg.practiceapispring.dto.responses.ApiEntityResponse;
import com.jorshbg.practiceapispring.dto.responses.PagedResponse;
import com.jorshbg.practiceapispring.dto.responses.TagResponse;
import com.jorshbg.practiceapispring.exception.BlogException;
import com.jorshbg.practiceapispring.mapper.TagMapper;
import com.jorshbg.practiceapispring.model.Tag;
import com.jorshbg.practiceapispring.repository.ITagRepository;
import com.jorshbg.practiceapispring.service.interfaces.IServiceGeneric;
import com.jorshbg.practiceapispring.util.ApiResponseUtility;
import com.jorshbg.practiceapispring.util.UpdatesTrackerUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class TagService implements IServiceGeneric<TagResponse, Long, TagDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagService.class);

    @Autowired
    private ITagRepository repositoryTag;

    @Autowired
    private HttpServletRequest request;

    @Override
    public ApiEntityResponse<TagResponse> getActiveRecordById(Long id) throws ResponseStatusException {
        LOGGER.info("Getting tag by id: {}", id);
        Tag tag = repositoryTag.findByIdAndActive(id, true).orElseThrow(() ->
                new BlogException(HttpStatus.NOT_FOUND, "Tag with id: " + id + " not found"));
        LOGGER.info("Tag found: {}", tag);
        TagResponse response = TagMapper.INSTANCE.toResponse(tag);
        LOGGER.info("Tag converted to response");
        return new ApiResponseUtility().getResponse(response,
                new LinkedHashMap<>(Map.of("self", this.getServletPath() + "/" + id))
        );
    }

    @Override
    public ApiEntityResponse<TagResponse> save(TagDTO entity) throws ResponseStatusException {
        entity.setName(entity.getName().toLowerCase());
        LOGGER.info("Creating new resource of tag, Data entry: {}", entity);
        if (repositoryTag.findByName(entity.getName().toLowerCase()).isPresent()) {
            throw new BlogException(HttpStatus.CONFLICT, "Tag with name: " + entity.getName() + " already exists");
        }
        Tag tag = repositoryTag.saveAndFlush(TagMapper.INSTANCE.fromDTO(entity));
        LOGGER.info("Tag created successfully with id: {}", tag.getId());
        LOGGER.info("Tag created: {}", tag);
        TagResponse response = TagMapper.INSTANCE.toResponse(tag);
        LOGGER.info("Tag converted to response");
        LOGGER.info("Response: {}", response);
        return new ApiResponseUtility().getResponse(response,
                new LinkedHashMap<>(Map.of("self", this.getServletPath() + "/" + response.getId()))
        );
    }

    @Override
    public void deactivate(Long id) throws ResponseStatusException {
        LOGGER.info("The tag with id: {} is setting to down in 'active' property", id);
        Tag tag = repositoryTag.findById(id).orElseThrow(() ->
                new BlogException(HttpStatus.NOT_FOUND, "Tag with id: " + id + " not found"));
        LOGGER.info("Tag found: {}", tag);
        tag.setActive(false);
        repositoryTag.saveAndFlush(tag);
        LOGGER.info("Active property is false state now");
    }

    @Override
    public ApiEntityResponse<TagResponse> activate(Long id) throws ResponseStatusException {
        LOGGER.info("The tag with id: {} is setting to activate", id);
        Tag tag = repositoryTag.findById(id).orElseThrow(() ->
                new BlogException(HttpStatus.NOT_FOUND, "Tag with id: " + id + " not found"));
        LOGGER.info("Tag found: {}", tag);
        tag.setActive(true);
        repositoryTag.saveAndFlush(tag);
        LOGGER.info("Active property is true state now");
        TagResponse response = TagMapper.INSTANCE.toResponse(tag);
        LOGGER.info("Tag converted to response");
        return new ApiResponseUtility().getResponse(response,
                new LinkedHashMap<>(Map.of("self", this.getServletPath() + "/" + response.getId()))
        );
    }

    @Override
    public ApiEntityResponse<TagResponse> update(Long id, TagDTO update) throws ResponseStatusException {
        LOGGER.info("Updating tag");
        LOGGER.info("Entry data: {}", update);
        Tag tag = repositoryTag.findById(id).orElseThrow(() ->
                new BlogException(HttpStatus.NOT_FOUND, "Tag with id: " + id + " not found"));
        LOGGER.info("Tag found: {}. Starting with mapping", tag);
        LOGGER.info("Converting tag founded to auxiliar object to check field updates");
        Tag copyToCheckUpdate = new Tag();
        TagMapper.INSTANCE.copy(tag, copyToCheckUpdate);
        LOGGER.info("Successful copy. Starting to update tag");
        TagMapper.INSTANCE.update(update, tag);
        repositoryTag.saveAndFlush(tag);
        LOGGER.info("Tag updated. Starting to search changes.");
        UpdatesTrackerUtil<Tag> tracker = new UpdatesTrackerUtil<>();
        Map<String, Map<String, String>> changes = null;
        try {
            changes = tracker.getUpdates(copyToCheckUpdate, tag);
        } catch (IllegalAccessException e) {
            LOGGER.error("Unable to catch changes of the entity. Error: {}", e.getMessage(), e);
        }
        LOGGER.info("Mapping finished. Fields updated: {}", changes);
        TagResponse response = TagMapper.INSTANCE.toResponse(tag);
        LOGGER.info("Tag converted to response");
        return new ApiResponseUtility().getResponse(response, this.selfLink());
    }


    @Override
    public PagedResponse<TagResponse> getAllActiveRecords(int page, int size, Sort.Direction direction, String orderBy) throws ResponseStatusException {
        page = Math.max(page - 1, 0);
        Page<Tag> paginated = repositoryTag.findByActive(true, PageRequest.of(page, size, direction, orderBy));
        if (!(page < paginated.getTotalPages())) {
            throw new BlogException(HttpStatus.NOT_FOUND, "No page found");
        }
        if (!paginated.hasContent()) {
            throw new BlogException(HttpStatus.NOT_FOUND, "No data found");
        }
        LOGGER.info("Query complete to paginate. Page: {}, Size: {}, Total amount data: {}", page, size, paginated.getTotalElements());
        Iterable<TagResponse> tags = TagMapper.INSTANCE.toResponses(paginated.getContent());
        LOGGER.info("Conversion to TagResponse complete");
        return new ApiResponseUtility().getPagedResponse(tags, paginated, "tags");
    }

    private String getServletPath(){
        return request.getContextPath() + request.getServletPath();
    }

    private LinkedHashMap<String, String> selfLink() {
        return new LinkedHashMap<>(Map.of("self", request.getContextPath() + request.getServletPath()));
    }
}
