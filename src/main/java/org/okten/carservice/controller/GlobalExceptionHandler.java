package org.okten.carservice.controller;

import org.okten.carservice.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String INVALID_FIELD_VALUE_FORMAT = "%s %s";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException exception) {
        List<String> errorDetails = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::getFieldErrorMessage)
                .toList();

        return ResponseEntity
                .badRequest()
                .body(ErrorDto.builder()
                        .details(errorDetails)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    private String getFieldErrorMessage(FieldError fieldError) {
        return INVALID_FIELD_VALUE_FORMAT.formatted(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
