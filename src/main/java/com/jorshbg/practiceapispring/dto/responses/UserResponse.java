package com.jorshbg.practiceapispring.dto.responses;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserResponse {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private LocalDate birthday;

    private String biography;

    private LocalDateTime lastModifiedAt;

    private LocalDateTime createdAt;

}
