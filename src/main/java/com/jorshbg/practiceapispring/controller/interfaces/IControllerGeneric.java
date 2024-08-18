package com.jorshbg.practiceapispring.controller.interfaces;

import com.jorshbg.practiceapispring.dto.responses.ApiEntityResponse;
import com.jorshbg.practiceapispring.dto.responses.PagedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


public interface IControllerGeneric<E, I> {

    ResponseEntity<PagedResponse<E>> getActiveRecords(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "10") int size);

    ResponseEntity<ApiEntityResponse<E>> getActiveById(@PathVariable I id);

    ResponseEntity<ApiEntityResponse<E>> down(@PathVariable I id);

    ResponseEntity<ApiEntityResponse<E>> up(@PathVariable I id);

}
