package com.jorshbg.practiceapispring.controller;

import com.jorshbg.practiceapispring.dto.*;
import com.jorshbg.practiceapispring.mapper.PostMapper;
import com.jorshbg.practiceapispring.model.Post;
import com.jorshbg.practiceapispring.service.CommentService;
import com.jorshbg.practiceapispring.service.PostService;
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
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<PagedResponse<PostResponse>> getAll(@RequestParam(value = "page", defaultValue = "1") int page, @Autowired HttpServletRequest request) {
        return ResponseEntity.ok().body(postService.getAllPosts(page, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiEntityResponse<PostResponse>> getById(@PathVariable Long id, @Autowired HttpServletRequest request) {
        return ResponseEntity.ok().body(ApiResponseUtility.getResponse(postService.getById(id), this.getDefaultPostLinks(request)));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<PagedResponse<CommentResponse>> getCommentById(@PathVariable Long id, @RequestParam(value = "page", defaultValue = "1") int page, @Autowired HttpServletRequest request) {
        return ResponseEntity.ok(this.commentService.getByPost(id, page, request));
    }

    @PostMapping
    public ResponseEntity<ApiEntityResponse<PostResponse>> store(@Validated @RequestBody PostPostRequest post, @Autowired HttpServletRequest request) throws URISyntaxException {
        Post created = this.postService.save(post);
        String uri = request.getContextPath() + request.getServletPath() + "/"+ created.getId();
        return ResponseEntity.created(new URI(uri)).body(ApiResponseUtility.getResponse(PostMapper.INSTANCE.toPostResponse(created), this.getDefaultPostLinks(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiEntityResponse<PostResponse>> totalUpdate(@PathVariable Long id, @Validated @RequestBody PostPutRequest post, @Autowired HttpServletRequest request) {
        return ResponseEntity.ok(ApiResponseUtility.getResponse(PostMapper.INSTANCE.toPostResponse(this.postService.update(id, post)), this.getDefaultPostLinks(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostResponse> delete(@PathVariable Long id) {
        this.postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiEntityResponse<PostResponse>> partialUpdate(@PathVariable Long id, @Validated @RequestBody PostPatchRequest post, @Autowired HttpServletRequest request) {
        return ResponseEntity.ok(ApiResponseUtility.getResponse(PostMapper.INSTANCE.toPostResponse(this.postService.partialUpdate(id, post)), this.getDefaultPostLinks(request)));
    }

    private LinkedHashMap<String, String> getDefaultPostLinks(HttpServletRequest request){
        LinkedHashMap<String, String> links = new LinkedHashMap<>();
        String uri = request.getContextPath() + request.getServletPath();
        links.put("self", uri);
        links.put("comments", uri + "/comments");
        return links;
    }
}
