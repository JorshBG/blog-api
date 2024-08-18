package com.jorshbg.practiceapispring.controller;

import com.jorshbg.practiceapispring.controller.interfaces.IControllerGeneric;
import com.jorshbg.practiceapispring.dto.requests.TagDTO;
import com.jorshbg.practiceapispring.dto.responses.ApiEntityResponse;
import com.jorshbg.practiceapispring.dto.responses.PagedResponse;
import com.jorshbg.practiceapispring.dto.responses.TagResponse;
import com.jorshbg.practiceapispring.exception.BlogException;
import com.jorshbg.practiceapispring.service.TagService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("tags")
public class TagController implements IControllerGeneric<TagResponse, Long> {

    @Autowired
    private TagService tagService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TagController.class);

    @Autowired
    private HttpServletRequest request;

    @GetMapping
    @Override
    public ResponseEntity<PagedResponse<TagResponse>> getActiveRecords(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        LOGGER.info("Attempt to get all tags. Mapping: {}", request.getPathInfo());
        var response = tagService.getAllActiveRecords(page, size, Sort.Direction.ASC, "id");
        LOGGER.info("Query finished");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ApiEntityResponse<TagResponse>> getActiveById(@PathVariable Long id) {
        LOGGER.info("Attempt to get a tag with ID: {}. Mapping: {}", id, request.getPathInfo());
        var response = tagService.getActiveRecordById(id);
        LOGGER.info("Query finished");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiEntityResponse<TagResponse>> store(@Validated @RequestBody TagDTO tagRequest) {
        LOGGER.info("Attempt to store a tag. Mapping: {}. RequestBody: {}", request.getPathInfo(), tagRequest);
        var response = tagService.save(tagRequest);
        URI uriResource = null;
        try {
            uriResource = new URI(request.getServletPath() + "/" + response.getData().getId());
        } catch (URISyntaxException e) {
            throw new BlogException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
        LOGGER.info("Insertion finished. Response URI: {}", uriResource);
        return ResponseEntity.created(uriResource).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiEntityResponse<TagResponse>> update(
            @PathVariable Long id,
            @Validated @RequestBody TagDTO tagRequest
    ) {
        LOGGER.info("Attempt to update a tag. Mapping: {}, Method: {}. RequestBody: {}",
                request.getPathInfo(),
                request.getMethod(),
                tagRequest);
        var response = tagService.update(id, tagRequest);
        LOGGER.info("Put update finished");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<ApiEntityResponse<TagResponse>> down(@PathVariable Long id) {
        LOGGER.info("Attempt to deactivate a tag with ID: {}", id);
        tagService.deactivate(id);
        LOGGER.info("Deleting finished");
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/activate/{id}")
    public ResponseEntity<ApiEntityResponse<TagResponse>> up(@PathVariable Long id) {
        LOGGER.info("Attempt to activate a tag with ID: {}", id);
        var response = tagService.activate(id);
        LOGGER.info("Activate finished");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiEntityResponse<TagResponse>> patchUpdate(
            @PathVariable Long id,
            @Validated @RequestBody TagDTO tagRequest
    ) {
        LOGGER.info("Attempt to update a tag. Mapping: {}, Method: {}. RequestBody: {}",
                request.getPathInfo(),
                request.getMethod(),
                tagRequest);
        var response = tagService.update(id, tagRequest);
        LOGGER.info("Patch update finished");
        return ResponseEntity.ok(response);
    }

}
