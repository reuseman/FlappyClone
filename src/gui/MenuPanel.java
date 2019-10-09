package gui;

import entities.Background;
import utils.ButtonsFactory;
import utils.Commons;
import utils.Image;
import utils.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {

    private FrameGame frameGame;
    private Background background;
    private utils.Image image;
    private JButton startButton;

    public MenuPanel(FrameGame frameGame) {
        this.setSize(Commons.FRAME_AREA);
        this.setMinimumSize(Commons.FRAME_AREA);
        this.setPreferredSize(Commons.FRAME_AREA);
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        this.frameGame = frameGame;
        this.background = new Background(Background.Type.DAY);
        this.image = new Image(Resources.getImage("/resources/images/title.png"), new Rectangle(0, 0, 500, 500));
        this.startButton = ButtonsFactory.getButton(Resources.getImage("/resources/images/play.png"),
                Resources.getImage("/resources/images/play_pressed.png"),
                Resources.getImage("/resources/images/play_rollover.png"),
                Resources.getImage("/resources/images/play.png"), true);
        this.startButton.addActionListener(new StartListener());

        this.add(Box.createHorizontalGlue());
        this.add(this.startButton);
        this.add(Box.createHorizontalGlue());
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        this.background.draw(g2);
        this.image.drawCenteredHorizontally(g2, 60);

        Toolkit.getDefaultToolkit().sync();
    }

    public class StartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource().equals(startButton)) {
                frameGame.switchPanel();
            }
        }
    }
}
