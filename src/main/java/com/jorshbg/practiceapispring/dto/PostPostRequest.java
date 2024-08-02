package com.jorshbg.practiceapispring.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jorshbg.practiceapispring.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PostPostRequest {

    @NotNull
    @Size(min = 2, max = 100)
    private String title;

    private String content;

    @NotNull
    private Long authorId;

    public @NotNull @Size(min = 2, max = 100) String getTitle() {
        return title;
    }

    public void setTitle(@NotNull @Size(min = 2, max = 100) String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public @NotNull Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(@NotNull Long authorId) {
        this.authorId = authorId;
    }
}
