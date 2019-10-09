package entities;

import utils.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Image extends Rectangle {

    private BufferedImage image;

    public Image(String url, int x, int y, int width, int height) {
        this.image = Resources.getImage(url);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Image(BufferedImage image, int x, int y, int width, int height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(this.image, this.x, this.y, this.width, this.height, null);
    }


}
