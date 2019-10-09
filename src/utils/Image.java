package utils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by alex on 25/06/17.
 */
public class Image {

    private BufferedImage image;
    private Rectangle area;
    private boolean visible;

    public Image(BufferedImage image, Rectangle area) {
        this.image = image;
        this.area = area;
        this.visible = true;
    }

    private void draw(Graphics2D g2, int x, int y) {
        if (this.visible) {
            g2.drawImage(this.image, x, y, null);
        }
    }

    public void drawCenteredHorizontally(Graphics2D g2, int y) {
        int x = (this.area.width / 2)  - this.image.getWidth() / 2;
        this.draw(g2, x, y);
    }

    public void drawCenteredVertically(Graphics2D g2, int x) {
        int y = (this.area.height / 2) - this.image.getHeight() / 2;
        this.draw(g2, x, y);
    }

    public void drawCentered(Graphics2D g2) {
        int x = (this.area.width / 2)  - this.image.getWidth() / 2;
        int y = (this.area.height / 2) - this.image.getHeight() / 2;
        this.draw(g2, x, y);
    }

    public void setVisible(boolean value) {
        this.visible = value;
    }
}
