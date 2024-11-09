package com.orderdataintegrator.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@Getter
public class ApiException extends RuntimeException {

    private final String responseMessage;
    private final HttpStatus httpStatus;

    public ApiException(String responseMessage, HttpStatus httpStatus) {
        super();
        this.httpStatus = Objects.requireNonNull(httpStatus, "httpStatus cannot be null");
        this.responseMessage = responseMessage != null ? responseMessage : "";
    }

    public ApiException(String responseMessage, HttpStatus httpStatus, String exceptionMessage) {
        super(exceptionMessage);
        this.httpStatus = Objects.requireNonNull(httpStatus, "httpStatus cannot be null");
        this.responseMessage = responseMessage != null ? responseMessage : "";
    }

    public ApiException(String responseMessage, HttpStatus httpStatus, String exceptionMessage, Throwable cause) {
        super(exceptionMessage, cause);
        this.httpStatus = Objects.requireNonNull(httpStatus, "httpStatus cannot be null");
        this.responseMessage = responseMessage != null ? responseMessage : "";
    }

    public ApiException(String responseMessage, HttpStatus httpStatus, Throwable cause) {
        super(cause);
        this.httpStatus = Objects.requireNonNull(httpStatus, "httpStatus cannot be null");
        this.responseMessage = responseMessage != null ? responseMessage : "";
    }
}
