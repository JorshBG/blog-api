package com.jorshbg.practiceapispring.controller;

import com.jorshbg.practiceapispring.dto.requests.CommentPostRequest;
import com.jorshbg.practiceapispring.dto.requests.CommentPutRequest;
import com.jorshbg.practiceapispring.dto.responses.ApiEntityResponse;
import com.jorshbg.practiceapispring.dto.responses.CommentResponse;
import com.jorshbg.practiceapispring.dto.responses.PagedResponse;
import com.jorshbg.practiceapispring.mapper.CommentMapper;
import com.jorshbg.practiceapispring.model.Comment;
import com.jorshbg.practiceapispring.service.CommentService;
import com.jorshbg.practiceapispring.util.ApiResponseUtility;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<PagedResponse<CommentResponse>> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @Autowired HttpServletRequest request
    ){
        return ResponseEntity.ok(commentService.getAll(page, size, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiEntityResponse<CommentResponse>> getById(@PathVariable Long id, @Autowired HttpServletRequest request){
        return ResponseEntity.ok(
                new ApiResponseUtility().getResponse(
                        CommentMapper.INSTANCE.toCommentResponse(commentService.getById(id)),
                        getDefaultLinks(request)
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiEntityResponse<CommentResponse>> store(@Validated @RequestBody CommentPostRequest newComment, @Autowired HttpServletRequest request) throws URISyntaxException {
        Comment comment = commentService.save(newComment);
        return ResponseEntity.created(new URI(request.getContextPath() + request.getServletPath() + "/" + comment.getId())).body(
                new ApiResponseUtility().getResponse(
                        CommentMapper.INSTANCE.toCommentResponse(comment),
                        getDefaultLinks(request)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommentResponse> delete(@PathVariable Long id){
        this.commentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiEntityResponse<CommentResponse>> update(@Validated @RequestBody CommentPutRequest update, @PathVariable Long id, @Autowired HttpServletRequest request){
        return ResponseEntity.ok(
                new ApiResponseUtility().getResponse(
                        CommentMapper.INSTANCE.toCommentResponse(commentService.update(id, update)),
                        getDefaultLinks(request)
                )
        );
    }

    private LinkedHashMap<String, String> getDefaultLinks(HttpServletRequest request){
        String uri = request.getContextPath() + request.getServletPath();
        LinkedHashMap<String, String> links = new LinkedHashMap<>();
        links.put("self", uri);
        return links;
    }
}
