package com.jorshbg.practiceapispring.dto;

import com.jorshbg.practiceapispring.model.User;

public class PostResponse {

    private int id;
    private String title;
    private String content;
    private User author;

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
