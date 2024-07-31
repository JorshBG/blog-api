package com.jorshbg.practiceapispring.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jorshbg.practiceapispring.model.Post;
import com.jorshbg.practiceapispring.model.User;

import java.time.LocalDateTime;

public class CommentResponse {

    private Long id;

    private String content;

    private User author;

    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author.getUsername();
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
