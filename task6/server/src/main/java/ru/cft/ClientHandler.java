package ru.cft;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.message.Message;
import ru.cft.message.MessageType;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private static final List<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName;

    private final Gson gson = new Gson();

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            this.bufferedReader = new BufferedReader((new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)));
            setUserName();
            if (!isUserNameValid(userName)) {
                kickUser("User with nickname \"" + this.userName + "\" already exists");
            } else {
                acceptNewUser();
            }
        } catch (IOException e) {
            close(this.socket, this.bufferedWriter, this.bufferedReader);
        }
    }

    @Override
    public void run() {
        String incomingMessage;
        while (!socket.isClosed()) {
            try {
                incomingMessage = bufferedReader.readLine();
                if (incomingMessage != null) {
                    handleIncomingMessage(incomingMessage);
                } else {
                    removeClientHandler();
                    close(this.socket, this.bufferedWriter, this.bufferedReader);
                }
            } catch (IOException e) {
                close(this.socket, this.bufferedWriter, this.bufferedReader);
                break;
            }
        }
    }

    public void sendMessageToAllClients(String message) {
        Message incomingMessage = new Message();
        incomingMessage.setMessageText(this.userName + ": " + message);
        incomingMessage.setMessageType(MessageType.MESSAGE);
        for (var clientHandler : clientHandlers) {
            try {
                logger.info(incomingMessage.getMessageText());
                clientHandler.bufferedWriter.write(gson.toJson(incomingMessage));
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
            } catch (IOException e) {
                close(this.socket, this.bufferedWriter, this.bufferedReader);
            }
        }
    }

    public void removeClientHandler() {
        sendMessageToAllClients(" has left the chat!");
        sendRefreshedChatMemberListToAllUsers();
        clientHandlers.remove(this);
    }

    private void kickUser(String reason) {
        try {
            Message message = new Message();
            message.setMessageType(MessageType.MESSAGE);
            message.setMessageText("You have been kicked from the chat! Reason: " + reason);
            logger.info(this.userName + " " + message.getMessageText());
            this.bufferedWriter.write(gson.toJson(message));
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
            close(this.socket, this.bufferedWriter, this.bufferedReader);
            removeClientHandler();
        } catch (IOException e) {
            close(this.socket, this.bufferedWriter, this.bufferedReader);
        }
    }

    private boolean isUserNameValid(String userName) {
        for (var clientHandler : clientHandlers) {
            if (userName.equals(clientHandler.userName)) {
                return false;
            }
        }
        return true;
    }

    public void close(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleIncomingMessage(String message) {
        Message incomingMessage = gson.fromJson(message, Message.class);
        switch (incomingMessage.getMessageType()) {
            case MESSAGE -> sendMessageToAllClients(incomingMessage.getMessageText());
            case DISCONNECT -> removeClientHandler();
        }
    }

    private void acceptNewUser() {
        clientHandlers.add(this);
        sendRefreshedChatMemberListToAllUsers();
        sendMessageToAllClients("has joined the chat!");
    }

    private void setUserName() throws IOException {
        this.userName = gson.fromJson(bufferedReader.readLine(), Message.class).getMessageText();
    }

    private void sendRefreshedChatMemberListToAllUsers() {
        List<String> chatMembers = clientHandlers.stream()
                .map(val -> val.userName)
                .toList();

        Message message = new Message();
        message.setMessageType(MessageType.REFRESH_CHAT_MEMBERS_LIST);
        message.setMessageContent(chatMembers);

        try {
            this.bufferedWriter.write(gson.toJson(message));
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
        } catch (IOException e) {
            close(this.socket, this.bufferedWriter, this.bufferedReader);
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientHandler that)) return false;
        return userName.equals(that.userName);
    }
}
