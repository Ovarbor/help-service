package ru.help.exception;

public class NotFoundValidationException extends RuntimeException {
    public NotFoundValidationException(String message) {
        super(message);
    }
}
