package entities;

import utils.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Land extends Rectangle implements Runnable {

    private int speed;
    private Image[] ground;
    private boolean running;

    public Land(int x, int y) {
        BufferedImage tempImage = Resources.getImage("/resources/images/ground.png");

        this.x = x;
        this.y = y;
        this.width = tempImage.getWidth();
        this.height = tempImage.getHeight();
        this.speed = 2;

        this.ground = new Image[2];
        this.ground[0] = new Image(tempImage, x, y, this.width, this.height);
        this.ground[1] = new Image(tempImage, x + this.width, y, this.width, this.height);

        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            this.update();
            try {
                Thread.sleep(1000 / 60);        //TODO FRAME ESTERNI
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        this.ground[0].x -= this.speed;
        this.ground[1].x -= this.speed;

        if (this.ground[0].getMaxX() < 0) {
            this.ground[0].x = (int) this.ground[1].getMaxX();
        } else if (this.ground[1].getMaxX() < 0) {
            this.ground[1].x = (int) this.ground[0].getMaxX();
        }
    }

    public void draw(Graphics2D g2) {
        this.ground[0].draw(g2);
        this.ground[1].draw(g2);
    }

    public void setRunning(boolean value) {
        this.running = value;
    }

    public boolean isRunning() {
        return this.running;
    }
}
