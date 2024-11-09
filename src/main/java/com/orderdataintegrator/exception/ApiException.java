package com.orderdataintegrator.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorMessage;

    public ApiException(HttpStatus httpStatus, String errorMessage) {
        super();
        this.httpStatus = Objects.requireNonNull(httpStatus, "httpStatus cannot be null");
        this.errorMessage = errorMessage != null ? errorMessage : "";
    }

    public ApiException(String message, HttpStatus httpStatus, String errorMessage) {
        super(message);
        this.httpStatus = Objects.requireNonNull(httpStatus, "httpStatus cannot be null");
        this.errorMessage = errorMessage != null ? errorMessage : "";
    }

    public ApiException(String message, Throwable cause, HttpStatus httpStatus, String errorMessage) {
        super(message, cause);
        this.httpStatus = Objects.requireNonNull(httpStatus, "httpStatus cannot be null");
        this.errorMessage = errorMessage != null ? errorMessage : "";
    }

    public ApiException(Throwable cause, HttpStatus httpStatus, String errorMessage) {
        super(cause);
        this.httpStatus = Objects.requireNonNull(httpStatus, "httpStatus cannot be null");
        this.errorMessage = errorMessage != null ? errorMessage : "";
    }
}
