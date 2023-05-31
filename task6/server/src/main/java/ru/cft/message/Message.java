package ru.cft.message;

import java.util.ArrayList;
import java.util.List;

public class Message {
    private MessageType messageType;
    private String messageText;
    private List<String> messageContent = new ArrayList<>();

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public List<String> getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(List<String> messageContent) {
        this.messageContent = messageContent;
    }
}
