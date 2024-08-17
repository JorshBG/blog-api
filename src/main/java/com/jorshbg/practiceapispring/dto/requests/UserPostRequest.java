package com.jorshbg.practiceapispring.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserPostRequest {

    @NotNull
    @Size(max = 40)
    private String username;

    @NotNull
    @Size(max = 60)
    private String password;

    @NotNull
    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    @NotNull
    private String lastName;

    @NotNull
    @Email
    @Size(max = 253)
    private String email;

    @Size(min = 5, max = 25)
    private String phone;

    private LocalDate birthday;

    @Size(min = 5, max = 254)
    private String biography;

}
