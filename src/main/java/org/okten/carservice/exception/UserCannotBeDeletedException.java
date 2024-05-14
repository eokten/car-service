package org.okten.carservice.exception;

public class UserCannotBeDeletedException extends RuntimeException {

    public UserCannotBeDeletedException(String message) {
        super(message);
    }
}
