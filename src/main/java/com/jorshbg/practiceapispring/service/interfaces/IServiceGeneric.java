package com.jorshbg.practiceapispring.service.interfaces;

import com.jorshbg.practiceapispring.dto.responses.ApiEntityResponse;
import com.jorshbg.practiceapispring.dto.responses.PagedResponse;
import com.jorshbg.practiceapispring.dto.responses.TagResponse;
import com.jorshbg.practiceapispring.model.Tag;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

/**
 * Generic Service to implement in new Blog service to access to db resources
 * @param <T> Response type
 * @param <E> ID type of the model
 * @param <R> Request type
 */
public interface IServiceGeneric<T, E, R> {

    ApiEntityResponse<T> getById(E id) throws ResponseStatusException;

    ApiEntityResponse<T> save(R entity) throws ResponseStatusException;

    void delete(E id) throws ResponseStatusException;

    ApiEntityResponse<T> update(R entity) throws ResponseStatusException, IllegalAccessException;

    PagedResponse<T> getAll(int page, int size) throws ResponseStatusException;

    PagedResponse<T> getAll(int page, int size, Sort.Direction direction, String orderBy) throws ResponseStatusException;

}
