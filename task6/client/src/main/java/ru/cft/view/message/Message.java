package ru.cft.view.message;

import javax.swing.*;
import java.awt.*;

public class Message extends JLabel {
    private static final Font MESSAGE_FONT = new Font("Verdana", Font.PLAIN, 11);
    public Message(String text) {
        super(text);
        setFont(MESSAGE_FONT);
    }
}
