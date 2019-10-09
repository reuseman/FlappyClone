package entities;

import utils.Commons;
import utils.Resources;
import utils.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class Bird {
    public enum Direction {
        UP, DOWN, NONE
    }

    private Sprite sprite;
    private BufferedImage deadImage;

    private Dimension movementArea;
    private double gravity;
    private double ySpeed;
    private Direction direction;

    private Sound wingSound;
    private int jumpCounter;
    private boolean alive;

    private UpAction upAction;
    private DownAction downAction;
    private boolean keyAlreadyPressed;

    public Bird(double gravity, Dimension dimension, Dimension movementArea, Sound wingSound) {
        this.sprite = new Sprite(4, 80);
        this.sprite.addSprite(Resources.getImage("/resources/images/bird0.png"));
        this.sprite.addSprite(Resources.getImage("/resources/images/bird1.png"));
        this.sprite.setDefaultFrame(1);
        this.sprite.addSprite(Resources.getImage("/resources/images/bird2.png"));
        this.sprite.addSprite(Resources.getImage("/resources/images/bird1.png"));
        this.deadImage = Resources.getImage("/resources/images/bird_dead.png");

        this.direction = Direction.NONE;
        this.movementArea = movementArea;
        this.sprite.width = dimension.width;
        this.sprite.height = dimension.height;
        this.gravity = gravity;
        this.ySpeed = 0;
        this.sprite.x = Commons.DEFAULT_BIRD_START_X - this.sprite.width / 2 - 1;
        this.sprite.y = this.movementArea.height / 2 - this.sprite.height / 2;

        this.wingSound = wingSound;
        this.jumpCounter = 0;
        this.alive = true;

        this.keyAlreadyPressed = false;
        this.upAction = new UpAction();
        this.downAction = new DownAction();
    }

    public void update() {
        if (this.alive) {
            if (this.direction == Direction.DOWN) {
                this.moveDown();
            } else if (this.direction == Direction.UP) {
                this.moveUp();
            }
        } else
            this.moveDown();
    }

    private void moveUp() {
        this.ySpeed = -5;
        this.sprite.y += this.ySpeed;
    }

    private void moveDown() {
        this.ySpeed += this.gravity;
        this.sprite.y += this.ySpeed;

        if (this.sprite.getMaxY() >= this.movementArea.height)
            this.sprite.y = this.movementArea.height - this.sprite.height;
    }

    public void startAnimation() {
        if (!this.sprite.isActive()) {
            this.sprite.setEnabled();
            new Thread(this.sprite).start();
        }
    }

    public void draw(Graphics2D g2) {
        if (this.alive)
            this.sprite.draw(g2);
        else
            g2.drawImage(this.deadImage, this.sprite.x, this.sprite.y, this.sprite.width, this.sprite.height, null);
    }

    public void reset() {
        this.direction = Direction.NONE;
        this.ySpeed = 0;
        this.gravity = Commons.DEFAULT_GRAVITY;
        this.sprite.x = Commons.DEFAULT_BIRD_START_X - this.sprite.width / 2 - 1;
        this.sprite.y = this.movementArea.height / 2 - this.sprite.height / 2;
        this.jumpCounter = 0;
        this.setAlive();
        this.keyAlreadyPressed = false;
    }

    public void setAlive() {
        this.alive =true;
    }

    public void setDead() {
        this.alive = false;
        this.sprite.setDisabled();
    }

    public boolean isAlive() {
        return this.alive;
    }

    public int getJumpCounter() {
        return jumpCounter;
    }

    public Rectangle getRectangle() {
        return this.sprite;
    }

    public UpAction getUpAction() {
        return this.upAction;
    }

    public DownAction getDownAction() {
        return this.downAction;
    }

    private class UpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (keyAlreadyPressed)
                direction = Direction.DOWN;
            else if (alive) {
                direction = Direction.UP;
                wingSound.stop();
                wingSound.play();
                keyAlreadyPressed = true;
                jumpCounter++;
            }
        }
    }

    private class DownAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            direction = Direction.DOWN;
            keyAlreadyPressed = false;
        }
    }
}
