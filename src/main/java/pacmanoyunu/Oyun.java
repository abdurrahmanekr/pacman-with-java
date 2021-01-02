package main.java.pacmanoyunu;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Oyun {
    public static int MAP_SIZE = 15;

    private boolean isEnd;
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

    public Point getPacmanPosition() {
        return pacmanPosition;
    }

    public Yon getPacmanAspect() {
        return pacmanAspect;
    }

    public int getPacmanSpeed() {
        return pacmanSpeed;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public byte getMapBlock(int i, int j) {
        return this.map[i][j];
    }

    public Dusman[] getEnemies() {
        return enemies;
    }

    public void keyPressed(KeyEvent e) {
        if (isEnd && e.getKeyCode() != KeyEvent.VK_SPACE)
            return;

        switch (e.getKeyCode()) {
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
