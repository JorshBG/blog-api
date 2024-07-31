package com.jorshbg.practiceapispring.dto;

import jakarta.validation.constraints.NotNull;

import java.util.LinkedHashMap;

public class ApiEntityResponse<T> {

    @NotNull
    private T data;

    private LinkedHashMap<String, String> links;

    @NotNull
    public T getData() {
        return data;
    }

    public void setData(@NotNull T data) {
        this.data = data;
    }

    public LinkedHashMap<String, String> getLinks() {
        return links;
    }

    public void setLinks(LinkedHashMap<String, String> links) {
        this.links = links;
    }
}
