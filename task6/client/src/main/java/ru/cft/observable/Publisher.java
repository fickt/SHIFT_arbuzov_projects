package ru.cft.observable;

public interface Publisher {
    void notifySubscribers(String message);
    void notifySubscribersWarning(String warningMessage);
    void addSubscriber(Subscriber subscriber);
}
