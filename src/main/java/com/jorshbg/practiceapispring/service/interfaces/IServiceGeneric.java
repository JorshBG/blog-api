package com.jorshbg.practiceapispring.service.interfaces;

import com.jorshbg.practiceapispring.dto.responses.ApiEntityResponse;
import com.jorshbg.practiceapispring.dto.responses.PagedResponse;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

public interface IServiceGeneric<T, E> {

    ApiEntityResponse<T> getById(E id) throws ResponseStatusException;

    ApiEntityResponse<T> save(T entity) throws ResponseStatusException;

    void delete(E id) throws ResponseStatusException;

    ApiEntityResponse<T> update(T entity) throws ResponseStatusException;

    PagedResponse<T> getAll(int page, int size) throws ResponseStatusException;

    PagedResponse<T> getAll(int page, int size, Sort.Direction direction, String orderBy) throws ResponseStatusException;

}
