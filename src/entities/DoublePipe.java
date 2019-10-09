package entities;

import utils.Commons;
import utils.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public class DoublePipe {

    public static final int DEFAULT_SPEED = -2;

    public static final BufferedImage PIPE_DOWN = Resources.getImage("/resources/images/pipe_down.png");
    public static final BufferedImage PIPE_UP = Resources.getImage("/resources/images/pipe_up.png");
    public static final BufferedImage PIPE = Resources.getImage("/resources/images/pipe.png");
    public static final int GAP = 140;
    public static final int MINIMUM_PIPE_HEIGHT = DoublePipe.PIPE_DOWN.getHeight();
    public static final int MAXIMUM_PIPE_HEIGHT = Commons.GAME_AREA.height - DoublePipe.MINIMUM_PIPE_HEIGHT - DoublePipe.GAP;

    private Pipe topPipe;
    private Pipe bottomPipe;
    private int gap;
    private int xSpeed;

    private int tempBottomHeight, tempTopHeight;

    public DoublePipe(int x, int height) {
        this.gap = DoublePipe.GAP;
        this.setRandomPipesHeight(height);
        this.topPipe = new Pipe(x, 0, this.tempTopHeight, DoublePipe.PIPE_DOWN, DoublePipe.PIPE, Pipe.HeadDirection.DOWN);
        this.bottomPipe = new Pipe(x, height - this.tempBottomHeight, this.tempBottomHeight, DoublePipe.PIPE_UP, DoublePipe.PIPE, Pipe.HeadDirection.UP);
        this.xSpeed = DoublePipe.DEFAULT_SPEED;
    }

    public void update() {
        this.topPipe.setX(this.topPipe.getX() + this.xSpeed);
        this.bottomPipe.setX(this.bottomPipe.getX() + this.xSpeed);
    }

    public void draw(Graphics2D g2) {
        this.topPipe.draw(g2);
        this.bottomPipe.draw(g2);
    }

    public boolean intersect(Rectangle rectangle) {
        return this.topPipe.intersect(rectangle) || this.bottomPipe.intersect(rectangle);
    }

    public void setRandomPipesHeight(int height) {
        this.tempTopHeight = ThreadLocalRandom.current().nextInt(DoublePipe.MINIMUM_PIPE_HEIGHT, DoublePipe.MAXIMUM_PIPE_HEIGHT);
        this.tempBottomHeight = height - this.tempTopHeight - this.gap;
    }

    public int getPipeX() {
        return this.topPipe.getX();
    }

    public int getMaxPipeX() {
        return this.topPipe.getX() + this.topPipe.getWidth();
    }
}
