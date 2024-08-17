package com.jorshbg.practiceapispring.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentPostRequest {

    @NotNull
    private String content;

    @NotNull
    private Long authorId;

    @NotNull
    private Long postId;
}
