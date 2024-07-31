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

@Component
public class ApiResponseUtility {


    public static ApiEntityResponse<UserResponse> userResponse(UserResponse userResponse, HttpServletRequest request) {
        ApiEntityResponse<UserResponse> response = new ApiEntityResponse<>();

        response.setData(userResponse);

        LinkedHashMap<String, String> links = new LinkedHashMap<>();
        links.put("self", getBaseUrl(request).concat("/users"));
        links.put("comments", getBaseUrl(request).concat("/users/").concat(userResponse.getId().toString()).concat("/comments"));
        links.put("posts", getBaseUrl(request).concat("/users/").concat(userResponse.getId().toString()).concat("/posts"));

        response.setLinks(links);

        return response;
    }

    /**
     *
     * @param data Iterable with the Type of Response. Ej.: UserResponse, PostResponse, etc. Don't use Entities like User or Post
     * @param paginated Pagination from the repository
     * @param requestMappingName URI request mapping for the entity. Ej.: users posts comments
     */
    public static <T, E> PagedResponse<T> getPagedResponse(Iterable<T> data, Page<E> paginated, String requestMappingName, HttpServletRequest request) {
        PagedResponse<T> response = new PagedResponse<>();

        int total = paginated.getTotalPages();
        int currentPage = paginated.getNumber() + 1;

        response.setData(data);
        response.setLinks(createLinksForPagination(request, requestMappingName, total, currentPage));
        response.setMetadata(createMetadataForPagination(paginated));

        return response;
    }



    private static String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();             // http
        String serverName = request.getServerName();     // localhost
        int serverPort = request.getServerPort();        // 8080
        String contextPath = request.getContextPath();   // / (si hay un contexto)

        // Construye la URL base
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if (serverPort != 80 && serverPort != 443) {
            url.append(":").append(serverPort);
        }

        url.append(contextPath);

        return url.toString();
    }

    public static PagedResponse<Post> getPagedPostResponse(Iterable<PostResponse> posts, Page<Post> paginated, HttpServletRequest request) {
        return null;
    }

    private static <T> LinkedHashMap<String, String> createMetadataForPagination(Page<T> pagination){
        LinkedHashMap<String, String> metadata = new LinkedHashMap<>();
        metadata.put("totalPages", String.valueOf(pagination.getTotalPages()));
        metadata.put("totalElements", String.valueOf(pagination.getTotalElements()));
        metadata.put("elementsPerPage", String.valueOf(pagination.getSize()));
        metadata.put("pageNumber", String.valueOf(pagination.getNumber() + 1));
        return metadata;
    }

    private static <T> LinkedHashMap<String, String> createLinksForPagination(HttpServletRequest request, String entityName, int total, int currentPage){
        LinkedHashMap<String, String> links = new LinkedHashMap<>();
        links.put("self", getBaseUrl(request).concat(entityName +"?page=" + currentPage));
        links.put("next",
                (currentPage >= total) ?
                        null :
                        getBaseUrl(request).concat(entityName +"?page=" + (currentPage + 1))
        );
        links.put("prev",
                (currentPage <= 1) ?
                        null :
                        getBaseUrl(request).concat(entityName +"?page=" + (currentPage - 1))
        );
        return links;
    }
}
