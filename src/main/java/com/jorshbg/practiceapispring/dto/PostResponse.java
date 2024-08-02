package com.jorshbg.practiceapispring.dto;

import com.jorshbg.practiceapispring.model.User;

import java.time.LocalDateTime;

public class PostResponse {

    private int id;
    private String title;
    private String content;
    private User author;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
