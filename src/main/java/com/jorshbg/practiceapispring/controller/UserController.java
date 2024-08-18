package com.jorshbg.practiceapispring.controller;

import com.jorshbg.practiceapispring.dto.requests.UserPatchRequest;
import com.jorshbg.practiceapispring.dto.requests.UserPostRequest;
import com.jorshbg.practiceapispring.dto.requests.UserPutRequest;
import com.jorshbg.practiceapispring.dto.responses.*;
import com.jorshbg.practiceapispring.service.CommentService;
import com.jorshbg.practiceapispring.service.PostService;
import com.jorshbg.practiceapispring.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<PagedResponse<UserResponse>> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @Autowired HttpServletRequest request
    ) {
        return ResponseEntity.ok().body(userService.readAll(page, size, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiEntityResponse<UserResponse>> getById(@PathVariable Long id, @Autowired HttpServletRequest request) {
        return ResponseEntity.ok().body(new ApiResponseUtility().getResponse(userService.readById(id), this.getDefaultUserLinks(request, id)));
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<PagedResponse<PostResponse>> getPosts(@PathVariable Long id, @RequestParam(value = "page", defaultValue = "1") int page, @Autowired HttpServletRequest request) {
        return ResponseEntity.ok().body(postService.getByAuthor(id, page, request));
    }

    @GetMapping("/{userId}/posts/{postId}/comments")
    public ResponseEntity<PagedResponse<CommentResponse>> getCommentByPost(@PathVariable Long userId, @PathVariable Long postId, @RequestParam(value = "page", defaultValue = "1") int page, @Autowired HttpServletRequest request) {
        return ResponseEntity.ok().body(commentService.getByAuthorAndPost(userId, postId, page, request));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<PagedResponse<CommentResponse>> getComments(@PathVariable Long id, @RequestParam(value = "page", defaultValue = "1") int page, @Autowired HttpServletRequest request) {
        return ResponseEntity.ok().body(commentService.getByAuthor(id, page, request));
    }

    @PostMapping
    public ResponseEntity<ApiEntityResponse<UserResponse>> store(@Validated @RequestBody UserPostRequest user, @Autowired HttpServletRequest request) throws URISyntaxException {
        var response = userService.create(user);
        var uri = request.getContextPath() + request.getServletPath() + "/" + response.getId();
        return ResponseEntity.created(new URI(uri)).body(new ApiResponseUtility().getResponse(response, this.getDefaultUserLinks(request, response.getId())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiEntityResponse<UserResponse>> totalUpdate(@PathVariable Long id, @Validated @RequestBody UserPutRequest updated, @Autowired HttpServletRequest request) {
        return ResponseEntity.ok().body(new ApiResponseUtility().getResponse(userService.updatePut(id, updated), this.getDefaultUserLinks(request, id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiEntityResponse<UserResponse>> partialUpdate(@PathVariable Long id, @Validated @RequestBody UserPatchRequest update, @Autowired HttpServletRequest request) {
        return ResponseEntity.ok().body(new ApiResponseUtility().getResponse(userService.updatePatch(id, update), this.getDefaultUserLinks(request, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> delete(@PathVariable Long id) {
        this.userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private LinkedHashMap<String, String> getDefaultUserLinks(HttpServletRequest request, Long id) {
        String uri = request.getContextPath() + request.getServletPath();
        LinkedHashMap<String, String> links = new LinkedHashMap<>();
        links.put("self", uri);
        links.put("comments", uri + "/comments");
        links.put("posts", uri + "/posts");
        links.put("comments_in_post", uri + "/posts/{{id}}/comments");
        return links;
    }
}
