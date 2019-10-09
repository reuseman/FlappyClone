package utils;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

/**
 * @author Alexù
 * È una classe abbozzo.
 */

public class Message {
    public static final Color DEFAULT_COLOR = Color.white;
    public static final Font DEFAULT_FONT = new Font("DejaVu Sans", Font.BOLD, 50);
    public static final Color DEFAULT_BORDER_COLOR = Color.black;
    public static final int DEFAULT_BORDER_WIDTH = 2;

    private String message;
    private Color color;
    private Font font;
    private Rectangle area;

    private Color borderColor;
    private int borderWidth;
    private boolean drawBorder;
    private boolean visible;

    public Message(String message, Rectangle area, boolean border) {
        this.message = message;
        this.area = area;
        this.color = Message.DEFAULT_COLOR;
        this.font = Message.DEFAULT_FONT;

        this.drawBorder = border;
        this.borderColor = Message.DEFAULT_BORDER_COLOR;
        this.borderWidth = Message.DEFAULT_BORDER_WIDTH;
        this.visible = true;
    }

   public Message(String message, Rectangle area, Color color, boolean border) {
       this.message = message;
       this.area = area;
       this.color = color;
       this.font = Message.DEFAULT_FONT;

       this.drawBorder = border;
       this.borderColor = Message.DEFAULT_BORDER_COLOR;
       this.borderWidth = Message.DEFAULT_BORDER_WIDTH;
       this.visible = true;
   }

    public Message(String message, Rectangle area, Color color, Font font, boolean border) {
        this.message = message;
        this.area = area;
        this.color = color;
        this.font = font;

        this.drawBorder = border;
        this.borderColor = Message.DEFAULT_BORDER_COLOR;
        this.borderWidth = Message.DEFAULT_BORDER_WIDTH;
        this.visible = true;
    }

    public void draw(Graphics2D g2, int x, int y) {
        if (this.visible) {
            g2.setColor(this.color);
            g2.setFont(this.font);
            g2.drawString(this.message, x, y);
        }
    }

    public void drawCenteredHorizontally(Graphics2D g2, int y) {
        FontMetrics fontMetrics = g2.getFontMetrics(this.font);
        int x = this.area.x + (this.area.width - fontMetrics.stringWidth(this.message)) / 2;

        this.draw(g2, x, y);
        if (drawBorder && this.borderColor != null && this.borderWidth >= 1)
            this.drawBorder(g2, x, y);
    }

    public void drawCenteredVertically(Graphics2D g2, int x) {
        FontMetrics fontMetrics = g2.getFontMetrics(this.font);
        int y = this.area.y + (this.area.height - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();

        this.draw(g2, x, y);
        if (drawBorder && this.borderColor != null && this.borderWidth >= 1)
            this.drawBorder(g2, x, y);
    }

    public void drawCentered(Graphics2D g2) {
        FontMetrics fontMetrics = g2.getFontMetrics(this.font);
        int x = this.area.x + (this.area.width - fontMetrics.stringWidth(this.message)) / 2;
        int y = this.area.y + (this.area.height - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();

        this.draw(g2, x, y);
        if (drawBorder && this.borderColor != null && this.borderWidth >= 1)
            this.drawBorder(g2, x, y);
    }

    private void drawBorder(Graphics2D g2, int x, int y) {
        FontRenderContext fontRenderContext = new FontRenderContext(null, true, false);
        TextLayout textLayout = new TextLayout(this.message, this.font, fontRenderContext);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(x, y);
        Shape outline = textLayout.getOutline(affineTransform);

        g2.setColor(this.borderColor);
        BasicStroke stroke = new BasicStroke(this.borderWidth);
        g2.setStroke(stroke);
        g2.draw(outline);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setBorderColor(Color strokeColor) {
        this.borderColor = strokeColor;
    }

    public void setBorder(Color strokeColor, int strokeWidth) {
        this.borderColor = strokeColor;
        this.borderWidth = strokeWidth;
    }

    public void setVisible(boolean value) {
        this.visible = value;
    }

    public boolean isVisible() {
        return this.visible;
    }
}
