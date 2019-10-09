package gui;

import entities.Stats;
import utils.*;
import utils.Image;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GameOverPanel extends JPanel {
    private Stats stats;
    private Message gameOverMessage;
    private Message gameOverMessageBorder;
    private Message scoreMessage;
    private Message bestScoreMessage;
    private Message totalJumpsMessage;
    private Message totalGamesMessage;
    private FrameGame frameGame;
    private utils.Image scoreBoardimage;
    private JButton startButton;
    private JButton exitButton;

    public GameOverPanel(FrameGame frameGame) {
        this.frameGame = frameGame;
        this.setSize(Commons.FRAME_AREA);
        this.setPreferredSize(new Dimension(Commons.FRAME_AREA));
        this.setMinimumSize(new Dimension(Commons.FRAME_AREA));
        this.setBounds(new Rectangle(0, 0, Commons.FRAME_AREA.width, Commons.FRAME_AREA.height));
        this.setLayout(null);
        this.setOpaque(false);
        this.setVisible(false);

        this.scoreBoardimage = new Image(Resources.getImage("/resources/images/scoreboard.png"),
                new Rectangle(0, 0, 500, 500));

        this.initButtons();
        this.initKeyBindings();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(0, 0, 0, 50));
        g2.fillRect(0, 0, Commons.GAME_AREA.width, Commons.GAME_AREA.height);

        this.gameOverMessageBorder.drawCenteredHorizontally(g2, 100);
        this.gameOverMessage.drawCenteredHorizontally(g2, 100);

        this.scoreBoardimage.drawCentered(g2);
        this.scoreMessage.draw(g2, 300, 203);
        this.bestScoreMessage.draw(g2, 300, 238);
        this.totalJumpsMessage.draw(g2, 300, 269);
        this.totalGamesMessage.draw(g2, 300, 304);

        Toolkit.getDefaultToolkit().sync();
    }

    public void initMessages() {
        Rectangle area = new Rectangle(new Point(0, 0), Commons.FRAME_AREA);
        Font font = Resources.getFont("/resources/fonts/04B_19__.TTF", 59);

        this.gameOverMessage = new Message("Game Over!", area, new Color(252, 160, 72), font, true);
        this.gameOverMessage.setBorder(Color.white, 3);
        this.gameOverMessageBorder = new Message("Game Over!", area, new Color(252, 160, 72), font, true);
        this.gameOverMessageBorder.setBorder(new Color(84, 56, 71), 6);

        Color color = new Color(252, 120, 88);
        font = Resources.getFont("/resources/fonts/04B_19__.TTF", 19);
        this.scoreMessage = new Message("" + stats.getCurrentScore(), area, color, font, true);
        this.bestScoreMessage = new Message("" + stats.getBestScore(), area, color, font, true);
        this.totalJumpsMessage = new Message("" + stats.getTotalJumps(), area, color, font, true);
        this.totalGamesMessage = new Message("" + stats.getTotalMatches(), area, color, font, true);
    }

    public void initButtons() {
        this.startButton = ButtonsFactory.getButton(Resources.getImage("/resources/images/play.png"),
                Resources.getImage("/resources/images/play_pressed.png"),
                Resources.getImage("/resources/images/play_rollover.png"),
                Resources.getImage("/resources/images/play.png"), true);
        this.startButton.addActionListener(new RestartListener());
        this.startButton.setLocation(500 / 2 - this.startButton.getWidth() - 10, 500 - this.startButton.getHeight() - 105);
        this.startButton.setVisible(true);
        this.add(this.startButton);

        this.exitButton = ButtonsFactory.getButton(Resources.getImage("/resources/images/home.png"),
                Resources.getImage("/resources/images/home_pressed.png"),
                Resources.getImage("/resources/images/home_rollover.png"),
                Resources.getImage("/resources/images/home.png"), true);
        this.exitButton.addActionListener(new ExitListener());
        this.exitButton.setLocation(500 / 2 + 10, 500 - this.exitButton.getHeight() - 105);
        this.exitButton.setVisible(true);
        this.add(this.exitButton);
    }

    public void initKeyBindings() {
        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "restartGame");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "restartGame");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "exitGame");
        actionMap.put("restartGame", new RestartGameAction());
        actionMap.put("exitGame", new ExitGameAction());
    }

    public void setStats(Stats stats) {
        this.stats = stats;
        this.initMessages();
    }

    public class RestartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource().equals(startButton)) {
                frameGame.restartGame();
                frameGame.hideGameOverPanel();
            }
        }
    }

    public class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource().equals(exitButton)) {
                frameGame.switchPanel();
                frameGame.hideGameOverPanel();
            }
        }
    }

    public class RestartGameAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            frameGame.restartGame();
            frameGame.hideGameOverPanel();
        }
    }

    public class ExitGameAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            frameGame.switchPanel();
            frameGame.hideGameOverPanel();
        }
    }
}
