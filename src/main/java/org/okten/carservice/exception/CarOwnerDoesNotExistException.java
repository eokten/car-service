package org.okten.carservice.exception;

public class CarOwnerDoesNotExistException extends RuntimeException {

    public CarOwnerDoesNotExistException(String message) {
        super(message);
    }
}
