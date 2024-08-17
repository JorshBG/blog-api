package com.jorshbg.practiceapispring.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentPutRequest {

    @NotNull
    private String content;

}
