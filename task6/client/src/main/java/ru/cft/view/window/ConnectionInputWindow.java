package ru.cft.view.window;

import ru.cft.view.icon.Icon;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ConnectionInputWindow extends JFrame {
    private final JLabel chatIpLabel;
    private final JPanel panel;
    private final JLabel output;
    private final JTextField input;
    private final JButton button;

    public ConnectionInputWindow() {
        super("Join chat");
        setIconImage(Icon.getAppIcon());
        chatIpLabel = new JLabel("Chat IP:");
        panel = new JPanel();
        output = new JLabel();
        input = new JTextField(10);
        add(panel);
        panel.add(chatIpLabel);
        input.setText("localhost:8089");
        button = new JButton("connect");
        panel.add(input);
        panel.add(button);
        panel.add(output);
        setSize(300, 85);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public String getInput() {
        return this.input.getText();
    }

    public void setButtonActionListener(ActionListener e) {
        button.addActionListener(e);
    }
}
