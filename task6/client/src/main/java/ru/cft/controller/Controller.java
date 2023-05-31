package ru.cft.controller;

import java.util.List;

public interface Controller {
    void setNewNickname(String nickname);
    void sendMessage(String message);
    List<String> getChatMembers();
    void setConnectionToServer(String serverIp);
    void disconnectFromServer();
}
