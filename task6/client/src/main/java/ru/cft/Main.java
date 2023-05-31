package ru.cft;

import ru.cft.controller.ControllerImpl;
import ru.cft.client.Client;
import ru.cft.messagehandler.MessageHandler;
import ru.cft.view.View;

public class Main {
    public static void main(String[] args) {
        MessageHandler messageHandler = new MessageHandler();
        Client client = new Client(messageHandler);
        View view = new View(new ControllerImpl(client));

        messageHandler.addSubscriber(view);
    }
}