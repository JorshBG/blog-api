package com.jorshbg.practiceapispring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

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

    public @NotNull @Size(max = 40) String getUsername() {
        return username;
    }

    public void setUsername(@NotNull @Size(max = 40) String username) {
        this.username = username;
    }

    public @NotNull @Size(max = 60) String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @Size(max = 60) String password) {
        this.password = password;
    }

    public @NotNull @Size(max = 50) String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull @Size(max = 50) String firstName) {
        this.firstName = firstName;
    }

    public @Size(max = 50) @NotNull String getLastName() {
        return lastName;
    }

    public void setLastName(@Size(max = 50) @NotNull String lastName) {
        this.lastName = lastName;
    }

    public @NotNull @Email @Size(max = 253) String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Email @Size(max = 253) String email) {
        this.email = email;
    }

    public @Size(min = 5, max = 25) String getPhone() {
        return phone;
    }

    public void setPhone(@Size(min = 5, max = 25) String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public @Size(min = 5, max = 254) String getBiography() {
        return biography;
    }

    public void setBiography(@Size(min = 5, max = 254) String biography) {
        this.biography = biography;
    }
}
