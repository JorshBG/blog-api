package com.jorshbg.practiceapispring.util;

import com.jorshbg.practiceapispring.dto.responses.ApiEntityResponse;
import com.jorshbg.practiceapispring.dto.responses.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class ApiResponseUtility {

    /**
     * Get the entity into a response with links and data
     * @param entity Data of the entity to response
     * @param links Links about relations of entity
     * @param <T> Type of entity
     */
    public <T> ApiEntityResponse<T> getResponse(T entity, LinkedHashMap<String, String> links) {
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
    public <T, E> PagedResponse<T> getPagedResponse(Iterable<T> data, Page<E> paginated, String requestMappingName) {
        PagedResponse<T> response = new PagedResponse<>();

        int total = paginated.getTotalPages();
        int currentPage = paginated.getNumber() + 1;

        response.setData(data);
        response.setLinks(this.createLinksForPagination(requestMappingName, total, currentPage));
        response.setMetadata(this.createMetadataForPagination(paginated));

        return response;
    }


    private <T> LinkedHashMap<String, String> createMetadataForPagination(Page<T> pagination){
        LinkedHashMap<String, String> metadata = new LinkedHashMap<>();
        metadata.put("totalPages", String.valueOf(pagination.getTotalPages()));
        metadata.put("totalElements", String.valueOf(pagination.getTotalElements()));
        metadata.put("elementsPerPage", String.valueOf(pagination.getNumberOfElements()));
        metadata.put("pageNumber", String.valueOf(pagination.getNumber() + 1));
        return metadata;
    }



    private LinkedHashMap<String, String> createLinksForPagination(String entityName, int total, int currentPage){
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
