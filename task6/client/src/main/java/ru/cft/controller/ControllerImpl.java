package ru.cft.controller;

import ru.cft.client.Client;
import ru.cft.message.Message;
import ru.cft.message.MessageType;

import java.util.List;

public class ControllerImpl implements Controller {
    private final Client client;

    public ControllerImpl(Client client) {
        this.client = client;
    }

    @Override
    public void setNewNickname(String nickname) {
        client.setNewNickname(nickname);
    }

    @Override
    public void sendMessage(String message) {
        Message incomingMessage = new Message();
        incomingMessage.setMessageType(MessageType.MESSAGE);
        incomingMessage.setMessageText(message);

        client.sendMessage(incomingMessage);
    }

    @Override
    public List<String> getChatMembers() {
        return client.getChatMembers();
    }

    @Override
    public void setConnectionToServer(String serverIp) {
        client.setConnection(serverIp);
    }

    @Override
    public void disconnectFromServer() {
        client.disconnectFromServer();
    }
}
