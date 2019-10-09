package utils;

import java.awt.*;

/**
 * La classe crea una sorta di flash formato da un gradiente circolare.
 * Per renderlo più sfumato bisogna utilizzare 3 colori e punti di distanza al posto di 2.
 * È una classe abbozzo
 *
 * @author Alex
 */

public class Flash {
    private Rectangle area;
    private Color color;
    private boolean active;

    public Flash(Rectangle area, Color color) {
        this.area = area;
        this.color = color;
    }

    public void draw(Graphics2D g2) {
        if (this.active) {
            Color[] colors = {new Color(0, 0, 0, 0), new Color(this.color.getRed(), this.color.getGreen(),
                    this.color.getBlue(), 50)};
            float[] distance = {0.5f, 1.0f};
            RadialGradientPaint gp2 = new RadialGradientPaint(this.area.width / 2, this.area.height / 2,
                    this.area.width / 2, distance, colors);

            g2.setPaint(gp2);
            g2.fill(this.area);
        }
    }

    public void setActive(boolean value) {
        this.active = value;
    }

    public boolean isActive() {
        return this.active;
    }
}
