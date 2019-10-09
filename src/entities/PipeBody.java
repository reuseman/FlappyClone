package entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PipeBody extends Rectangle {
    public BufferedImage image;
    private TexturePaint bodyImage;

    public PipeBody(BufferedImage image, int x, int y, int height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = this.image.getWidth();
        this.height = height;
        this.bodyImage = new TexturePaint(this.image, this);
    }

    public void draw(Graphics2D g2) {
        g2.setPaint(this.bodyImage);
        g2.fill(this);
    }

    public void setX(int x) {
        this.x = x;
        this.bodyImage = new TexturePaint(this.image, this);
    }
}
