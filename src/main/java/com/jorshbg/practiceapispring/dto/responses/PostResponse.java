package com.jorshbg.practiceapispring.dto.responses;

import com.jorshbg.practiceapispring.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {

    private int id;
    private String title;
    private String content;
    private User author;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
}
