package com.jorshbg.practiceapispring.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jorshbg.practiceapispring.model.Post;
import com.jorshbg.practiceapispring.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

public class CommentPostRequest {

    private String content;

    @NotNull
    private Long authorId;

    @NotNull
    private Long postId;

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

    public @NotNull Long getPostId() {
        return postId;
    }

    public void setPostId(@NotNull Long postId) {
        this.postId = postId;
    }
}
