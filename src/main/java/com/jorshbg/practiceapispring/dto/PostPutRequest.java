package com.jorshbg.practiceapispring.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PostPutRequest {

    @NotNull
    @Size(min = 2, max = 100)
    private String title;

    @NotNull
    private String content;

    public @NotNull @Size(min = 2, max = 100) String getTitle() {
        return title;
    }

    public void setTitle(@NotNull @Size(min = 2, max = 100) String title) {
        this.title = title;
    }

    public @NotNull String getContent() {
        return content;
    }

    public void setContent(@NotNull String content) {
        this.content = content;
    }
}
