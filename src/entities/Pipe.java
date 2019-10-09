package entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Pipe {
    public enum HeadDirection {
        UP, DOWN
    }

    private PipeHead pipeHead;
    private PipeBody pipeBody;

    public Pipe(int x, int y, int height, BufferedImage pipeHeadImage, BufferedImage pipeBodyImage, HeadDirection direction) {
        if (direction == HeadDirection.UP) {
            this.pipeHead = new PipeHead(pipeHeadImage, x, y);
            this.pipeBody = new PipeBody(pipeBodyImage, x, y + this.pipeHead.height, height - pipeHead.height);
        } else if (direction == HeadDirection.DOWN) {
            this.pipeBody = new PipeBody(pipeBodyImage, x, y, height - pipeHeadImage.getHeight());
            this.pipeHead = new PipeHead(pipeHeadImage, x, this.pipeBody.height);
        }
    }

    public void draw(Graphics2D g2) {
        this.pipeHead.draw(g2);
        this.pipeBody.draw(g2);
    }

    public void setX(int x) {
        this.pipeHead.x = x;
        this.pipeBody.setX(x);
    }

    public int getX() {
        return this.pipeHead.x;
    }

    public int getY() {return this.pipeHead.y; }

    public int getWidth() {
        return this.pipeHead.width;
    }

    public int getHeight() {
        return this.pipeHead.height + this.pipeBody.height;
    }

    public boolean intersect(Rectangle rectangle) {
        return rectangle.intersects(pipeHead) || rectangle.intersects(pipeBody);
    }
}
