package ru.cft.view.window;

import ru.cft.view.icon.Icon;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ChangeNicknameInputWindow extends JFrame {

    private final JLabel nickNameLabel;
    private final JPanel panel;
    private final JLabel output;
    private final JTextField input;
    private final JButton button;

    public ChangeNicknameInputWindow() {
        super("Change nickname");
        setIconImage(Icon.getAppIcon());
        nickNameLabel = new JLabel("Nickname:");
        panel = new JPanel();
        output = new JLabel();
        input = new JTextField(10);
        add(panel);
        panel.add(nickNameLabel);
        input.setText("%nickname%");
        panel.add(input);
        button = new JButton("apply");
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
        this.button.addActionListener(e);
    }
}
