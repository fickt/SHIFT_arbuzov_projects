package ru.cft.view.icon;

import javax.swing.*;
import java.awt.*;

public class Icon {
    private static final Image APP_ICON = new ImageIcon(
            Toolkit
                    .getDefaultToolkit()
                    .getImage(Icon.class.getResource("/icq_logo.png")))
            .getImage();

    public static Image getAppIcon() {
        return APP_ICON;
    }
}
