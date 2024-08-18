package com.jorshbg.practiceapispring.service.interfaces;

import com.jorshbg.practiceapispring.dto.responses.ApiEntityResponse;
import com.jorshbg.practiceapispring.dto.responses.PagedResponse;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

/**
 * Generic Service to implement in new Blog service to access to db resources
 * @param <T> Response type
 * @param <ID> ID type of the model
 * @param <R> Request type
 */
public interface IServiceGeneric<T, ID, R> {

    ApiEntityResponse<T> getActiveRecordById(ID id) throws ResponseStatusException;

    ApiEntityResponse<T> save(R entity) throws ResponseStatusException;

    void deactivate(ID id) throws ResponseStatusException;

    ApiEntityResponse<T> activate(ID id) throws ResponseStatusException;

    ApiEntityResponse<T> update(ID id, R entity) throws ResponseStatusException, IllegalAccessException;

    PagedResponse<T> getAllActiveRecords(int page, int size, Sort.Direction direction, String orderBy) throws ResponseStatusException;

}
