package ru.cft.observable;

public interface Subscriber {
    void updateMessage(String message);
    void updateWarning(String warningMessage);
}
