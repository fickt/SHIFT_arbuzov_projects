package ru.cft.view.mainframe;


import ru.cft.view.icon.Icon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    private final JPanel messagePanel = new JPanel();
    private final JScrollPane scrollPane = new JScrollPane(messagePanel);
    private final JPanel bottomPanel = new JPanel();
    private final JTextField textField = new JTextField();
    public MainFrame() {
        super("ICQ");
        setIconImage(Icon.getAppIcon());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        textField.setBounds(10, 10, 10, 30);
        bottomPanel.add(textField);
        JLabel label = new JLabel("Press enter to send the message");
        label.setHorizontalAlignment(JLabel.CENTER);
        bottomPanel.add(label);
        bottomPanel.setLayout(new GridLayout(2, 0));

        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(new Color(134, 255, 154));
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setPreferredSize(new Dimension(500, 500));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void updateMessage(JLabel incomingMessage) {
        messagePanel.add(incomingMessage);
    }

    public void setWindowListener(WindowAdapter windowAdapter) {
        addWindowListener(windowAdapter);
    }

    public void setTextFieldListener(ActionListener actionListener) {
        textField.addActionListener(actionListener);
    }

    public String getTextFieldContent() {
        return this.textField.getText();
    }

    public void setTextFieldContent(String text) {
        this.textField.setText(text);
    }
}
