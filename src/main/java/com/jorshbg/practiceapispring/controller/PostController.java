package com.jorshbg.practiceapispring.controller;

import com.jorshbg.practiceapispring.dto.PagedResponse;
import com.jorshbg.practiceapispring.dto.PostResponse;
import com.jorshbg.practiceapispring.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<PagedResponse<PostResponse>> getAllPosts(@RequestParam(value = "page", defaultValue = "1") int page, @Autowired HttpServletRequest request) {
        return ResponseEntity.ok().body(postService.getAllPosts(page, request));
    }
}
