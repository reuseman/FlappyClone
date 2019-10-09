package entities;

import utils.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Background {

    private static final BufferedImage CITY_DAY = Resources.getImage("/resources/images/city_day.png");
    private static final BufferedImage CITY_NIGHT = Resources.getImage("/resources/images/city_night.png");

    private Rectangle sky;
    private BufferedImage city;
    private Land land;
    private TexturePaint cityTexture;
    private Type type;

    public enum Type {
        DAY, NIGHT
    }

    public Background(Type type) {
        this.type = type;
        if (this.type == Type.NIGHT)
            this.city = Background.CITY_NIGHT;
        else
            this.city = Background.CITY_DAY;

        this.sky = new Rectangle(0, 0, 500, 500 - this.city.getHeight());
        this.cityTexture = new TexturePaint(this.city, new Rectangle(0, 450, this.city.getWidth(), this.city.getHeight()));
        this.land = new Land(0, 450);

        new Thread(this.land).start();
    }

    public void draw(Graphics2D g2) {
        if (this.type == Type.NIGHT)
            g2.setColor(new Color(19, 135, 146));
        else
            g2.setColor(new Color(78, 192, 202));

        g2.fill(this.sky);
        g2.setPaint(this.cityTexture);
        g2.fill(new Rectangle(0, 450 - this.city.getHeight(), 500, this.city.getHeight()));
        this.land.draw(g2);
    }

    public void reset() {
        if (!this.land.isRunning()) {
            this.land.setRunning(true);
            new Thread(this.land).start();
        }
    }

    public void stopMovement() {
        this.land.setRunning(false);
    }

    public void changeType() {
        if (this.type == Type.DAY) {
            this.type = Type.NIGHT;
            this.city = Background.CITY_NIGHT;
        } else {
            this.type = Type.DAY;
            this.city = Background.CITY_DAY;
        }
        this.cityTexture = new TexturePaint(this.city, new Rectangle(0, 450, this.city.getWidth(), this.city.getHeight()));
    }
}
