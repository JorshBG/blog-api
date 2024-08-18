package com.jorshbg.practiceapispring.service;

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
public class TagService implements IServiceGeneric<TagResponse, Long, Tag> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagService.class);

    @Autowired
    private ITagRepository repositoryTag;

    @Autowired
    private HttpServletRequest request;

    @Override
    public ApiEntityResponse<TagResponse> getById(Long id) throws ResponseStatusException {
        LOGGER.info("Getting tag by id: {}", id);
        Tag tag = repositoryTag.findById(id).orElseThrow(() -> new BlogException(HttpStatus.NOT_FOUND, "Tag with id: " + id + " not found"));
        LOGGER.info("Tag found: {}", tag);
        TagResponse response = TagMapper.INSTANCE.toResponse(tag);
        LOGGER.info("Tag converted to response");
        return ApiResponseUtility.getResponse(response, this.selfLink());
    }

    @Override
    public ApiEntityResponse<TagResponse> save(Tag entity) throws ResponseStatusException {
        LOGGER.info("Creating new resource of tag, Data entry: {}", entity);
        Tag tag = repositoryTag.saveAndFlush(entity);
        LOGGER.info("Tag created successfully with id: {}", tag.getId());
        TagResponse response = TagMapper.INSTANCE.toResponse(tag);
        LOGGER.info("Tag converted to response");
        return ApiResponseUtility.getResponse(response, this.selfLink());
    }

    @Override
    public void delete(Long id) throws ResponseStatusException {
        LOGGER.info("The tag with id: {} is setting to down in 'active' property", id);
        Tag tag = repositoryTag.findById(id).orElseThrow(() -> new BlogException(HttpStatus.NOT_FOUND, "Tag with id: " + id + " not found"));
        LOGGER.info("Tag found: {}", tag);
        tag.setActive(false);
        repositoryTag.saveAndFlush(tag);
        LOGGER.info("Active property is false state now");
    }

    @Override
    public ApiEntityResponse<TagResponse> update(Tag update) throws ResponseStatusException, IllegalAccessException {
        LOGGER.info("Updating tag with id: {}", update.getId());
        LOGGER.info("Entry data: {}", update);
        Tag tag = repositoryTag.findById(update.getId()).orElseThrow(() -> new BlogException(HttpStatus.NOT_FOUND, "Tag with id: " + update.getId() + " not found"));
        LOGGER.info("Tag found: {}. Starting with mapping", tag);
        TagMapper.INSTANCE.update(update, tag);
        UpdatesTrackerUtil<Tag> tracker = new UpdatesTrackerUtil<>();
        var changes = tracker.getUpdates(tag, update);
        LOGGER.info("Mapping finished. Fields updated: {}", changes);
        TagResponse response = TagMapper.INSTANCE.toResponse(tag);
        LOGGER.info("Tag converted to response");
        return ApiResponseUtility.getResponse(response, this.selfLink());
    }

    @Override
    public PagedResponse<TagResponse> getAll(int page, int size) throws ResponseStatusException {
        Page<Tag> paginated = repositoryTag.findAll(PageRequest.of(page, size));
        return ApiResponseUtility.getPagedResponse(this.convert(page, size, paginated), paginated, "tags");
    }

    @Override
    public PagedResponse<TagResponse> getAll(int page, int size, Sort.Direction direction, String orderBy) throws ResponseStatusException {
        Page<Tag> paginated = repositoryTag.findAll(PageRequest.of(page, size, direction, orderBy));
        return ApiResponseUtility.getPagedResponse(this.convert(page, size, paginated), paginated, "tags");
    }

    private Iterable<TagResponse> convert(int page, int size, Page<Tag> paginated) {
        LOGGER.info("Query complete to paginate. Page: {}, Size: {}, Total amount data: {}", page, size, paginated.getTotalElements());
        Iterable<TagResponse> tags = TagMapper.INSTANCE.toResponses(paginated.getContent());
        LOGGER.info("Conversion to TagResponse complete");
        return tags;
    }

    private LinkedHashMap<String, String> selfLink(){
        return new LinkedHashMap<>(Map.of("self", request.getPathInfo()));
    }
}
