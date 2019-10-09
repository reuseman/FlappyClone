package gui;

import utils.Commons;
import utils.Resources;

import javax.swing.*;
import java.awt.*;

public class FrameGame extends JFrame {
    private GameOverPanel gameOverPanel;
    private MenuPanel menuGame;
    private GamePanel panelGame;

    public FrameGame() {
        this.setSize(Commons.FRAME_AREA);
        this.setPreferredSize(Commons.FRAME_AREA);
        this.setMinimumSize(new Dimension(Commons.FRAME_AREA));
        this.setBounds(new Rectangle(0, 0, Commons.FRAME_AREA.width, Commons.FRAME_AREA.height));
        this.setTitle("Flappy Bird! by Alex");
        this.setIconImage(Resources.getImage("/resources/images/icon.png"));
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.gameOverPanel = new GameOverPanel(this);
        this.gameOverPanel.setLocation(0, 0);
        this.getContentPane().add(this.gameOverPanel);
        this.gameOverPanel.setVisible(false);

        this.menuGame = new MenuPanel(this);
        this.menuGame.setLocation(0, 0);
        this.getContentPane().add(this.menuGame);
        this.menuGame.setVisible(true);

        this.panelGame = new GamePanel(this);
        this.panelGame.setLocation(0, 0);
        this.getContentPane().add(this.panelGame);
        this.panelGame.setVisible(false);

        this.pack();
    }

    public void switchPanel() {
        if (this.menuGame.isVisible()) {
            if (!this.panelGame.gamePlayed) {
                this.panelGame.gamePlayed = true;
                this.panelGame.initThreads();
            } else
                this.restartGame();
            this.panelGame.setVisible(true);
            this.menuGame.setVisible(false);
        } else {
            this.menuGame.setVisible(true);
            this.panelGame.setVisible(false);
        }
    }

    public void showGameOverPanel() {
        this.gameOverPanel.setStats(this.panelGame.getStats());
        this.gameOverPanel.requestFocus(true);
        this.gameOverPanel.setVisible(true);
        this.gameOverPanel.repaint();
    }

    public void hideGameOverPanel() {
        this.gameOverPanel.setVisible(false);
    }

    public void restartGame() {
        this.panelGame.restartGame();
    }
}
