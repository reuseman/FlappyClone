package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sprite extends Rectangle implements Runnable {

    private ArrayList<BufferedImage> sprites;
    private BufferedImage defaultFrame;
    private BufferedImage currentFrame;
    private boolean active;
    private int milliseconds;
    private int counter;

    public Sprite(int frame, int milliseconds) {
        this.sprites = new ArrayList<BufferedImage>();
        this.milliseconds = milliseconds;
        this.active = false;
    }

    public void addSprite(BufferedImage image) {
        this.sprites.add(image);
    }

    public void draw(Graphics2D g2) {
        if (this.active)
            g2.drawImage(this.currentFrame, this.x, this.y, this.width, this.height, null);
        else
            g2.drawImage(this.defaultFrame, this.x, this.y, this.width, this.height, null);
    }

    public void setDefaultFrame(int index) {
        this.defaultFrame = this.sprites.get(index);
    }

    public void setEnabled() {
        this.active = true;
    }

    public void setDisabled() {
        this.active = false;
    }

    public boolean isActive() {
        return this.active;
    }

    @Override
    public void run() {
        this.counter = 0;

        if (this.sprites.size() == 1)
            this.active = false;

        while (this.active) {
            this.currentFrame = this.sprites.get(this.counter);
            this.counter++;
            if (this.counter == this.sprites.size())
                this.counter = 0;

            try {
                Thread.sleep(this.milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
