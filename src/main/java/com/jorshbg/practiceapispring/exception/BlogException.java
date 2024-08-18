package com.jorshbg.practiceapispring.exception;

import com.jorshbg.practiceapispring.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;
import java.io.Serializable;

public class BlogException extends ResponseStatusException implements Serializable {

    @Serial
    private static final long serialVersionUID = 8922132615594426349L;

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogException.class);

    public BlogException(HttpStatusCode status) {
        super(status);
    }

    public BlogException(String message, String unauthorizedUrl) {
        super(HttpStatus.UNAUTHORIZED);
        LOGGER.error("An attempt to use the API without credentials in {}: {}", unauthorizedUrl, message);
    }

    public BlogException(User user) {
        super(HttpStatus.FORBIDDEN);
        LOGGER.error("The user with username {} is trying to use a resource that it does not have access", user.getUsername());
    }

    public <T> BlogException(T resource) {
        super(HttpStatus.NOT_FOUND);
        LOGGER.warn("The resource {} was not found", resource);
    }

    public BlogException(HttpStatusCode status, String reason) {
        super(status, reason);
        switch(status.value()){
            case 500: LOGGER.error("Internal Server Error: {}", reason);
            break;
            case 400: LOGGER.error("Bad request: {}", reason);
            break;
            default: LOGGER.warn("An error has been throw: {}", reason);
        }
    }

    public BlogException(HttpStatusCode status, String reason, Throwable cause) {
        super(status, reason, cause);
        LOGGER.error("Status: {}, Reason: {}", status, reason, cause);
    }
}
