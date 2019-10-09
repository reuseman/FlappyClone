package gui;

import entities.Background;
import entities.Bird;
import entities.DoublePipe;
import entities.Stats;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GamePanel extends JPanel {
    public boolean gamePlayed;
    private FrameGame frameGame;
    private Background background;
    private Bird bird;
    private ConcurrentLinkedQueue<DoublePipe> doublePipes;
    private Flash flash;

    private Sound sfxHit;
    private Sound sfxPoint;
    private Sound sfxWing;
    private Message tutorialMessage;
    private Message scoreMessage;

    private Stats stats;

    private boolean gameStarted;
    private volatile boolean gameRunning;
    private volatile boolean pipeRunning;
    private Thread pipeGeneratorThread;
    private Thread gameThread;

    public GamePanel(FrameGame frameGame) {
        this.frameGame = frameGame;
        this.gamePlayed = false;
        this.setSize(Commons.FRAME_AREA);
        this.setLayout(null);
        this.initSounds();
        this.initGameObjects();
        this.initMessages();
        this.initKeyBindings();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponents(graphics);
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        this.background.draw(g2);

        for (DoublePipe pipe : this.doublePipes) {
            pipe.draw(g2);
        }

        if (scoreMessage.isVisible())
            this.scoreMessage.drawCenteredHorizontally(g2, 60);
        if (!this.pipeRunning && bird.isAlive())
            this.tutorialMessage.drawCenteredHorizontally(g2, 330);

        this.bird.draw(g2);

        if (this.flash.isActive()) {
            this.flash.draw(g2);
            this.flash.setActive(false);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    public void initSounds() {
        String basePath = new File("").getAbsolutePath();
        basePath = "file://".concat(basePath);

        try {
            this.sfxHit = new Sound(basePath + "/src/resources/sounds/sfx_hit.wav");
            this.sfxPoint = new Sound(basePath + "/src/resources/sounds/sfx_point.wav");
            this.sfxWing = new Sound(basePath + "/src/resources/sounds/sfx_wing.wav");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void initGameObjects() {
        this.background = new Background(Background.Type.DAY);
        this.bird = new Bird(Commons.DEFAULT_GRAVITY, new Dimension(34, 24), Commons.GAME_AREA, this.sfxWing);
        this.doublePipes = new ConcurrentLinkedQueue<>();
        this.flash = new Flash(new Rectangle(0, 0, 500, 500), Color.white);
        this.stats = new Stats();
    }

    public void initMessages() {
        this.tutorialMessage = new Message("PREMI SPACE!", new Rectangle(0, 0, 500, 500),
                Color.white, Resources.getFont("/resources/fonts/04B_19__.TTF", 40), true);
        this.scoreMessage = new Message("" + stats.getCurrentScore(), new Rectangle(0, 0, 500, 500),
                Color.white, Resources.getFont("/resources/fonts/04B_19__.TTF", 40), true);
    }

    public void initKeyBindings() {
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "birdUp");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "birdDown");
        this.getActionMap().put("birdUp", this.bird.getUpAction());
        this.getActionMap().put("birdDown", this.bird.getDownAction());
    }

    public void initThreads() {
        this.gameStarted = false;
        this.gameRunning = true;
        this.pipeRunning = false;
        this.gameThread = new Thread(new GameThread());
        this.gameThread.start();
    }

    public void restartGame() {
        if (!this.gameRunning) {
            this.bird.reset();
            this.background.reset();
            this.pipeRunning = false;
            this.doublePipes.clear();
            this.doublePipes = new ConcurrentLinkedQueue<>();

            this.stats.setCurrentScore(0);
            this.scoreMessage.setMessage("" + this.stats.getCurrentScore());
            this.scoreMessage.setVisible(true);
            if (this.stats.getCurrentMatches() % 5 == 0)
                this.background.changeType();

            this.gameStarted = false;
            this.gameRunning = true;
            this.gameThread = new Thread(new GameThread());
            this.gameThread.start();
        }
    }

    public Stats getStats() {
        return this.stats;
    }

    public class GameThread implements Runnable {
        @Override
        public void run() {
            while (gameRunning) {
                bird.update();

                if (!pipeRunning) {
                    if (bird.getRectangle().getMaxY() == Commons.GAME_AREA.height) {
                        scoreMessage.setVisible(false);
                        gameRunning = false;
                        frameGame.showGameOverPanel();
                    } else if (!gameStarted && bird.getJumpCounter() == 1) {
                        pipeRunning = true;
                        gameStarted = true;
                        pipeGeneratorThread = new Thread(new PipeGeneratorThread());
                        pipeGeneratorThread.start();
                        bird.startAnimation();
                    }
                } else {
                    for (DoublePipe pipe : doublePipes) {
                        pipe.update();
                        if (pipe.intersect(bird.getRectangle()) ||
                                bird.getRectangle().getMaxY() >= Commons.GAME_AREA.height ||
                                (bird.getRectangle().getMaxX() >= pipe.getPipeX() &&
                                        bird.getRectangle().getMaxX() <= pipe.getMaxPipeX() &&
                                        bird.getRectangle().getY() <= 0)) {
                            flash.setActive(true);
                            bird.setDead();
                            background.stopMovement();
                            sfxPoint.stop();
                            sfxHit.play();

                            pipeRunning = false;
                            pipeGeneratorThread.interrupt();

                            stats.addCurrentMatches(1);
                            stats.addTotalMatches(1);
                            stats.addTotalDeaths(1);
                            stats.addTotalJumps(bird.getJumpCounter());
                            stats.addTotalScore(stats.getCurrentScore());
                            stats.save();
                        } else if (bird.getRectangle().x == pipe.getMaxPipeX()) {
                            sfxPoint.play();
                            stats.addCurrentScore(1);
                            scoreMessage.setMessage("" + stats.getCurrentScore());
                        }
                    }
                }

                repaint();

                try {
                    Thread.sleep(1000 / 60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class PipeGeneratorThread implements Runnable {
        @Override
        public void run() {
            while (pipeRunning) {
                for (Iterator<DoublePipe> iterator = doublePipes.iterator(); iterator.hasNext(); ) {
                    DoublePipe doublePipe = iterator.next();
                    if (doublePipe.getMaxPipeX() < 0)
                        iterator.remove();
                }

                doublePipes.add(new DoublePipe(600, 450));

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
