package org.okten.carservice.controller;

import org.okten.carservice.dto.ErrorDto;
import org.okten.carservice.exception.CarOwnerDoesNotExistException;
import org.okten.carservice.exception.UserAlreadyExistsException;
import org.okten.carservice.exception.UserCannotBeDeletedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

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

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDto> handleNoSuchElementException(NoSuchElementException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.builder()
                        .details(List.of(exception.getMessage()))
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler({UserCannotBeDeletedException.class, CarOwnerDoesNotExistException.class})
    public ResponseEntity<ErrorDto> handleCarOwnerException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDto.builder()
                        .details(List.of(exception.getMessage()))
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handlerUserAlreadyExistsException(UserAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDto.builder()
                        .details(List.of(exception.getMessage()))
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
