package com.jorshbg.practiceapispring.util;

import com.jorshbg.practiceapispring.dto.ApiEntityResponse;
import com.jorshbg.practiceapispring.dto.PagedResponse;
import com.jorshbg.practiceapispring.dto.PostResponse;
import com.jorshbg.practiceapispring.dto.UserResponse;
import com.jorshbg.practiceapispring.model.Post;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ApiResponseUtility {

    public static <T> ApiEntityResponse<T> getResponse(T entity, LinkedHashMap<String, String> links) {
        ApiEntityResponse<T> response = new ApiEntityResponse<>();
        response.setData(entity);
        response.setLinks(links);
        return response;
    }

    /**
     *
     * @param data Iterable with the Type of Response. Ej.: UserResponse, PostResponse, etc. Don't use Entities like User or Post
     * @param paginated Pagination from the repository
     * @param requestMappingName URI request mapping for the entity. Ej.: users posts comments
     */
    public static <T, E> PagedResponse<T> getPagedResponse(Iterable<T> data, Page<E> paginated, String requestMappingName) {
        PagedResponse<T> response = new PagedResponse<>();

        int total = paginated.getTotalPages();
        int currentPage = paginated.getNumber() + 1;

        response.setData(data);
        response.setLinks(createLinksForPagination(requestMappingName, total, currentPage));
        response.setMetadata(createMetadataForPagination(paginated));

        return response;
    }

    private static <T> LinkedHashMap<String, String> createMetadataForPagination(Page<T> pagination){
        LinkedHashMap<String, String> metadata = new LinkedHashMap<>();
        metadata.put("totalPages", String.valueOf(pagination.getTotalPages()));
        metadata.put("totalElements", String.valueOf(pagination.getTotalElements()));
        metadata.put("elementsPerPage", String.valueOf(pagination.getSize()));
        metadata.put("pageNumber", String.valueOf(pagination.getNumber() + 1));
        return metadata;
    }

    private static <T> LinkedHashMap<String, String> createLinksForPagination(String entityName, int total, int currentPage){
        LinkedHashMap<String, String> links = new LinkedHashMap<>();
        links.put("self", entityName +"?page=" + currentPage);
        links.put("next",
                (currentPage >= total) ?
                        null :
                        entityName +"?page=" + (currentPage + 1)
        );
        links.put("prev",
                (currentPage <= 1) ?
                        null :
                        entityName +"?page=" + (currentPage - 1)
        );
        return links;
    }
}
