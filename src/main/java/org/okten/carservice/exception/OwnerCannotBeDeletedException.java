package org.okten.carservice.exception;

public class OwnerCannotBeDeletedException extends RuntimeException {

    public OwnerCannotBeDeletedException(String message) {
        super(message);
    }
}
