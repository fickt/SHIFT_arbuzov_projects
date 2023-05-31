package ru.cft.view;

import ru.cft.controller.Controller;
import ru.cft.observable.Subscriber;
import ru.cft.view.mainframe.MainFrame;
import ru.cft.view.message.Message;
import ru.cft.view.window.ChangeNicknameInputWindow;
import ru.cft.view.window.ChatMembersWindow;
import ru.cft.view.window.ConnectionInputWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class View implements Subscriber {
    private final Controller controller;
    private final MainFrame mainFrame = new MainFrame();
    private final JMenu start = new JMenu("Start");

    public View(Controller controller) {
        this.controller = controller;
        setupMenuBar();
        setupListenersForMainFrame();
        mainFrame.revalidate();
    }

    private void setupListenersForMainFrame() {
        mainFrame.setWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.disconnectFromServer();
            }
        });

        mainFrame.setTextFieldListener(e -> {
            if (!mainFrame.getTextFieldContent().isBlank()) {
                controller.sendMessage(mainFrame.getTextFieldContent());
                mainFrame.setTextFieldContent("");
            }
        });
    }

    private void setupMenuBar() {
        setupJoinChatItemToMenuBar();
        setupChangeNicknameItemToMenuBar();
        setupChatMembersItemToMenuBar();
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(start);
        mainFrame.setJMenuBar(menuBar);
    }

    private void setupJoinChatItemToMenuBar() {
        JMenuItem joinChatItem = new JMenuItem("Join chat");
        joinChatItem.addActionListener((e) -> {
            ConnectionInputWindow connectionInputWindow = new ConnectionInputWindow();

            connectionInputWindow.setButtonActionListener(event -> {
                String serverIp = connectionInputWindow.getInput();
                controller.setConnectionToServer(serverIp);
                connectionInputWindow.setVisible(false);
            });
        });
        start.add(joinChatItem);
    }

    private void setupChangeNicknameItemToMenuBar() {
        JMenuItem changeNicknameItem = new JMenuItem("Change nickname");

        changeNicknameItem.addActionListener((e) -> {

            ChangeNicknameInputWindow changeNicknameInputWindow = new ChangeNicknameInputWindow();

            changeNicknameInputWindow.setButtonActionListener(e2 -> {
                controller.setNewNickname(changeNicknameInputWindow.getInput());
                changeNicknameInputWindow.setVisible(false);
            });

        });
        start.add(changeNicknameItem);
    }

    private void setupChatMembersItemToMenuBar() {
        JMenuItem chatMembersItem = new JMenuItem("Chat members");
        chatMembersItem.addActionListener((e) -> {
            new ChatMembersWindow(controller.getChatMembers());
        });
        start.add(chatMembersItem);
    }

    @Override
    public void updateMessage(String message) {
        Message incomingMessage = new Message(message);
        mainFrame.updateMessage(incomingMessage);
        mainFrame.revalidate();
    }

    @Override
    public void updateWarning(String warningMessage) {
        JOptionPane.showMessageDialog(mainFrame, warningMessage, "Warning!", JOptionPane.ERROR_MESSAGE);
    }
}

