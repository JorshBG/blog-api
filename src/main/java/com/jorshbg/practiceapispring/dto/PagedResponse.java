package com.jorshbg.practiceapispring.dto;

import java.util.LinkedHashMap;

public class PagedResponse<T> {

    private Iterable<T> data;

    private LinkedHashMap<String, String> links;

    private LinkedHashMap<String, String> metadata;

    public Iterable<T> getData() {
        return data;
    }

    public void setData(Iterable<T> data) {
        this.data = data;
    }

    public LinkedHashMap<String, String> getLinks() {
        return links;
    }

    public void setLinks(LinkedHashMap<String, String> links) {
        this.links = links;
    }

    public LinkedHashMap<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(LinkedHashMap<String, String> metadata) {
        this.metadata = metadata;
    }
}
