package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.Image;

/**
 * @author Alex
 */

public class ButtonsFactory {
    private static ClickSoundListener clickSoundListener = new ClickSoundListener();

    public static JButton getButton(Image buttonIcon, Image pressedIcon, Image rolloverIcon,
                                    Image disabledIcon, Boolean enableSound) {
        JButton button = new JButton();

        button.setMinimumSize(new Dimension(buttonIcon.getWidth(null), buttonIcon.getHeight(null)));
        button.setMaximumSize(new Dimension(buttonIcon.getWidth(null), buttonIcon.getHeight(null)));
        button.setSize(buttonIcon.getWidth(null), buttonIcon.getHeight(null));
        button.setPreferredSize(new Dimension(buttonIcon.getWidth(null), buttonIcon.getHeight(null)));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setIcon(new ImageIcon(buttonIcon));
        button.setPressedIcon(new ImageIcon(pressedIcon));
        button.setRolloverIcon(new ImageIcon(rolloverIcon));
        button.setDisabledIcon(new ImageIcon(disabledIcon));

        if (enableSound)
            button.addMouseListener(clickSoundListener);

        return button;
    }
}

