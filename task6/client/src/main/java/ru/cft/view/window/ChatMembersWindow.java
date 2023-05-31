package ru.cft.view.window;

import ru.cft.view.icon.Icon;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChatMembersWindow extends JFrame {

    public ChatMembersWindow(List<String> chatMembers) {
        super("Chat members");
        setIconImage(Icon.getAppIcon());
        JList<String> displayList = new JList<>(chatMembers.toArray(new String[0]));
        JScrollPane scrollPane = new JScrollPane(displayList);
        getContentPane().add(scrollPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setSize(new Dimension(300, 250));
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
