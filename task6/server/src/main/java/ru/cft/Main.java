package ru.cft;

public class Main {
    public static void main(String[] args) {
        PropertiesLoader propertiesLoader = new PropertiesLoader();

        ChatServer chatServer = new ChatServer(propertiesLoader.getPort());
        chatServer.startServer();
    }
}