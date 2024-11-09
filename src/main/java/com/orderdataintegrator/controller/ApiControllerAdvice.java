package com.orderdataintegrator.controller;

import com.orderdataintegrator.dto.ApiResponse;
import com.orderdataintegrator.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException e) {
        if (e.getHttpStatus() != null && e.getHttpStatus().is4xxClientError()) {
            log.error(e.getMessage());
        } else {
            log.error("", e);
        }

        HttpStatus httpStatus = e.getHttpStatus() != null ? e.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
        ApiResponse<String> stringApiResponse = new ApiResponse<>(e.getResponseMessage(), null); // TODO 데이터 정리 필요
        return ResponseEntity.status(httpStatus).body(stringApiResponse);
    }


}
