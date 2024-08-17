package com.jorshbg.practiceapispring.dto.requests;

import lombok.Data;

@Data
public class PostPatchRequest {

    private String title;
    private String content;

}
