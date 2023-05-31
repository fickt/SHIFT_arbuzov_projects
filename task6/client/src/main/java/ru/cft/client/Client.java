package ru.cft.client;

import com.google.gson.Gson;
import ru.cft.message.Message;
import ru.cft.message.MessageType;
import ru.cft.messagehandler.MessageHandler;

import java.io.*;
import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Client {
    private static final String MSG_CONNECTION_FAILED = "Connection to server is failed!";
    private final MessageHandler messageHandler;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName = "%nickname%";

    private String host;
    private int port;

    public Client(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    private final Gson gson = new Gson();

    public void setConnection(String serverIp) {
        try {
            setServerIp(serverIp);
            this.socket = new Socket(this.host, this.port);
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            Message message = new Message();
            message.setMessageType(MessageType.MESSAGE);
            message.setMessageText(this.userName);

            sendMessage(message);
            listenForMessage();
        } catch (ConnectException e) {
            messageHandler.notifySubscribersWarning(MSG_CONNECTION_FAILED);
        } catch (IOException e) {
            messageHandler.notifySubscribersWarning(MSG_CONNECTION_FAILED);
            close();
        }
    }

    public void setNewNickname(String nickname) {
        this.userName = nickname;
    }

    public List<String> getChatMembers() {
        return this.messageHandler.getChatMembers();
    }

    public void sendMessage(Message message) {
        try {
            bufferedWriter.write(gson.toJson(message));
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            close();
        }
    }

    public void disconnectFromServer() {
        try {
            if (bufferedWriter != null) {
                Message message = new Message();
                message.setMessageText(this.userName);
                message.setMessageType(MessageType.DISCONNECT);
                bufferedWriter.write(gson.toJson(message));
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            close();
        }
        close();
    }

    private void close() {
        try {
            if (this.socket != null) {
                this.socket.close();
            }

            if (this.bufferedReader != null) {
                this.bufferedReader.close();
            }
            if (this.bufferedWriter != null) {
                this.bufferedWriter.close();
            }
        } catch (IOException e) {
            messageHandler.notifySubscribersWarning(MSG_CONNECTION_FAILED);
            e.printStackTrace();
        }
    }

    private void listenForMessage() {
        new Thread(() -> {
            while (!socket.isClosed()) {
                try {
                    String incomingMessage = bufferedReader.readLine();
                    if (incomingMessage != null) {
                        messageHandler.handleIncomingMessage(gson.fromJson(incomingMessage, Message.class));
                    } else {
                        messageHandler.notifySubscribersWarning(MSG_CONNECTION_FAILED);
                        close();
                        break;
                    }
                } catch (IOException e) {
                    messageHandler.notifySubscribersWarning(MSG_CONNECTION_FAILED);
                    close();
                    break;
                }
            }
        }).start();
    }

    private void setServerIp(String serverIp) {
        if (serverIp.contains("localhost")) {
            serverIp = serverIp.replace("localhost", "127.0.0.1");
        }
        String[] serverIpAsArray = serverIp.split(":");
        try {
            if (!isServerIpValid(serverIpAsArray[0], serverIpAsArray[1])) {
                throw new IllegalArgumentException();
            }
            this.host = serverIpAsArray[0];
            this.port = Integer.parseInt(serverIpAsArray[1]);
        } catch (Exception e) {
            messageHandler.notifySubscribersWarning(serverIp + " - invalid server address");
        }
    }

    private boolean isServerIpValid(String host, String port) {
        try {
            Inet4Address.getAllByName(host);
            return port.length() == 4;
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
