package ru.cft.figureapp.exception;

public class NegativeArgException extends RuntimeException {
    public NegativeArgException(String message) {
        super(message);
    }
}
