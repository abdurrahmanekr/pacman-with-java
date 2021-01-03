package main.java.pacmanoyunu;

import com.sun.istack.internal.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Oyun {
    public static int MAP_SIZE = 15;
    public static final int DELAY = 10;
    public static final int PROCESS_DELAY = 300;

    private int animationComplate = 1;

    private ActionListener listener;
    private Timer timer;

    private boolean isStarted;
    private int pacmanSpeed = 1;

    private int heart = 3;
    private int highScore = 0;

    /**
     * Pacman yönü
     */
    private Yon pacmanAspect = Yon.EAST;

    /**
     * Pacman'in bulunduğu nokta
     */
    private Point pacmanPosition = new Point(1, 1);

    /**
     * Düşmanların bulunduğu noktalar
     */
    private Dusman[] enemies = new Dusman[4];

    /**
     * 0 -> boşluk
     * 1 -> yatay duvar
     * 2 -> yem
     * 3 -> dikey duvar
     * 4 -> köşe 1
     * 5 -> köşe 2
     * 6 -> köşe 3
     * 7 -> köşe 4
     */
    private byte[][] map = new byte[][] {
            {4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5},
            {3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3},
            {3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3},
            {3, 2, 3, 2, 4, 1, 1, 1, 1, 5, 2, 3, 2, 2, 3},
            {3, 2, 3, 2, 3, 0, 0, 0, 0, 3, 2, 3, 2, 2, 3},
            {3, 2, 3, 2, 3, 0, 0, 0, 0, 3, 2, 3, 2, 2, 3},
            {3, 2, 3, 2, 3, 0, 0, 0, 0, 3, 2, 3, 2, 2, 3},
            {3, 2, 3, 2, 3, 0, 0, 0, 0, 3, 2, 3, 2, 2, 3},
            {3, 2, 3, 2, 3, 0, 0, 0, 0, 3, 2, 3, 2, 2, 3},
            {3, 2, 3, 2, 6, 1, 1, 1, 1, 7, 2, 3, 2, 2, 3},
            {3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3},
            {3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3},
            {3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3},
            {3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3},
            {6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7},
    };

    Oyun() {
        enemies[0] = new Dusman(Yon.EAST, 1, new Point(14, 14));
        enemies[1] = new Dusman(Yon.EAST, 1, new Point(14, 14));
        enemies[2] = new Dusman(Yon.EAST, 1, new Point(14, 14));
        enemies[3] = new Dusman(Yon.EAST, 1, new Point(14, 14));
    }

    Oyun(@NotNull ActionListener listener) {
        super();

        this.listener = listener;
    }

    public void start() {
        isStarted = true;

        timer = new Timer(DELAY, new GameProcess(this));
        timer.start();
    }

    class GameProcess implements ActionListener {
        private Oyun oyun;

        GameProcess(Oyun oyun) {
            this.oyun = oyun;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int anim = oyun.getAnimationComplate() + (Oyun.PROCESS_DELAY / Oyun.DELAY / 4); // 4 karede bitirsin

            if (anim >= Oyun.PROCESS_DELAY)
                oyun.setAnimationComplate(1);
            else
                oyun.setAnimationComplate(anim);

            oyun.listener.actionPerformed(e);
        }
    }

    public Point getPacmanPosition() {
        return pacmanPosition;
    }

    public Yon getPacmanAspect() {
        return pacmanAspect;
    }

    public int getPacmanSpeed() {
        return pacmanSpeed;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public byte getMapBlock(int i, int j) {
        return this.map[i][j];
    }

    public Dusman[] getEnemies() {
        return enemies;
    }

    public int getAnimationComplate() {
        return animationComplate;
    }

    public void setAnimationComplate(int animationComplate) {
        this.animationComplate = animationComplate;
    }

    public void keyPressed(KeyEvent e) {
        if (!isStarted && e.getKeyCode() != KeyEvent.VK_SPACE)
            return;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE: // oyunu başlat
                this.start();
                break;
            case KeyEvent.VK_RIGHT:
                this.pacmanAspect = Yon.EAST;
                System.out.println("sağa tıkladı");
                break;
            case KeyEvent.VK_LEFT:
                this.pacmanAspect = Yon.WEST;
                System.out.println("sola tıkladı");
                break;
            case KeyEvent.VK_DOWN:
                this.pacmanAspect = Yon.SOUTH;
                System.out.println("aşağı tıkladı");
                break;
            case KeyEvent.VK_UP:
                this.pacmanAspect = Yon.NORTH;
                System.out.println("yukarı tıkladı");
                break;
        }

    }

    public void keyReleased(KeyEvent e) {

    }
}
