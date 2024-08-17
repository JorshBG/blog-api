package com.jorshbg.practiceapispring.dto.responses;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class ApiEntityResponse<T> {

    @NotNull
    private T data;

    private LinkedHashMap<String, String> links;
}
