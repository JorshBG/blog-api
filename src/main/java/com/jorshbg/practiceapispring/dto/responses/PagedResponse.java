package com.jorshbg.practiceapispring.dto.responses;

import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class PagedResponse<T> {

    private Iterable<T> data;

    private LinkedHashMap<String, String> links;

    private LinkedHashMap<String, String> metadata;

}
