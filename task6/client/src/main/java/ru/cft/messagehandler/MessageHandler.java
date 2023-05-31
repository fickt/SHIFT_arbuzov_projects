package ru.cft.messagehandler;

import ru.cft.datemanager.DateManager;
import ru.cft.message.Message;
import ru.cft.observable.Publisher;
import ru.cft.observable.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class MessageHandler implements Publisher {
    private final List<Subscriber> subscribers = new ArrayList<>();
    private List<String> chatMembers = new ArrayList<>();

    public void handleIncomingMessage(Message message) {
        switch (message.getMessageType()) {
            case MESSAGE -> notifySubscribers(message.getMessageText());
            case REFRESH_CHAT_MEMBERS_LIST -> chatMembers = message.getMessageContent();

            default -> throw new IllegalArgumentException();
        }
    }

    public List<String> getChatMembers() {
        return this.chatMembers;
    }

    @Override
    public void notifySubscribers(String message) {
        for (var subscriber : this.subscribers) {
            subscriber.updateMessage(message + DateManager.getTodayDate());
        }
    }

    @Override
    public void notifySubscribersWarning(String warningMessage) {
        for (var subscriber : this.subscribers) {
            subscriber.updateWarning(warningMessage);
        }
    }

    @Override
    public void addSubscriber(Subscriber subscriber) {
        this.subscribers.add(subscriber);
    }
}
