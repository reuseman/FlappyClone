package utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URISyntaxException;

/**
 * @author Alex
 */

public class ClickSoundListener extends MouseAdapter {
    private Sound clickSound;

    public ClickSoundListener() {
        String basePath = new File("").getAbsolutePath();
        basePath = "file://".concat(basePath);

        try {
            this.clickSound = new Sound(basePath + "/src/resources/sounds/sfx_click.wav");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        this.clickSound.play();
    }
}
