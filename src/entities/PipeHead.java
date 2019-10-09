package entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PipeHead extends Rectangle {
    private BufferedImage image;

    public PipeHead(BufferedImage image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(this.image, this.x, this.y, this.width, this.height, null);
    }
}
