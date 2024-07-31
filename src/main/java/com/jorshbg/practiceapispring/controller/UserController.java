package com.jorshbg.practiceapispring.controller;

import com.jorshbg.practiceapispring.dto.*;
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
    public ResponseEntity<PagedResponse<UserResponse>> getAll(@RequestParam(value = "page", defaultValue = "1") int page, @Autowired HttpServletRequest request) {
        return ResponseEntity.ok().body(userService.readAll(page, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiEntityResponse<UserResponse>> getById(@PathVariable Long id, @Autowired HttpServletRequest request) {
        return ResponseEntity.ok().body(ApiResponseUtility.userResponse(userService.readById(id), request));
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<PagedResponse<PostResponse>> getPosts(@PathVariable Long id, @RequestParam(value = "page", defaultValue = "1") int page,@Autowired HttpServletRequest request) {
        return ResponseEntity.ok().body(postService.getByAuthor(id, page, request));
    }

    @GetMapping("/{userId}/posts/{postId}/comments")
    public ResponseEntity<PagedResponse<CommentResponse>> getCommentByPost(@PathVariable Long userId, @PathVariable Long postId, @RequestParam(value = "page", defaultValue = "1") int page,@Autowired HttpServletRequest request) {
        return ResponseEntity.ok().body(commentService.getByAuthorAndPost(userId, postId, page, request));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<PagedResponse<CommentResponse>> getComments(@PathVariable Long id, @RequestParam(value = "page", defaultValue = "1") int page,@Autowired HttpServletRequest request) {
        return ResponseEntity.ok().body(commentService.getByAuthor(id, page, request));
    }

    @PostMapping
    public ResponseEntity<ApiEntityResponse<UserResponse>> store(@Validated @RequestBody UserPostRequest user, @Autowired HttpServletRequest request) throws URISyntaxException {
        var response = userService.create(user);
        return ResponseEntity.created(new URI("/users/" + response.getId())).body(ApiResponseUtility.userResponse(response, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiEntityResponse<UserResponse>> totalUpdate(@PathVariable Long id, @Validated @RequestBody UserPutRequest updated, @Autowired HttpServletRequest request) {
        return ResponseEntity.ok().body(ApiResponseUtility.userResponse(userService.updatePut(id, updated), request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiEntityResponse<UserResponse>> partialUpdate(@PathVariable Long id, @Validated @RequestBody UserPatchRequest update, @Autowired HttpServletRequest request) {
        return ResponseEntity.ok().body(ApiResponseUtility.userResponse(userService.updatePatch(id, update), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> delete(@PathVariable Long id){
        this.userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
