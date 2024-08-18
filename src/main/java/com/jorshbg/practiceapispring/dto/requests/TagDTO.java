package com.jorshbg.practiceapispring.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TagDTO {

    @NotNull
    @Size(min = 2, max = 60)
    private String name;
}
