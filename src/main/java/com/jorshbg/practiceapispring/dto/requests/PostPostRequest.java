package com.jorshbg.practiceapispring.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostPostRequest {

    @NotNull
    @Size(min = 2, max = 100)
    private String title;

    @NotNull
    private String content;

    @NotNull
    private Long authorId;

}
