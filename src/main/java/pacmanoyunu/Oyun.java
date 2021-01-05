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

            if (anim >= Oyun.PROCESS_DELAY) {
                oyun.setAnimationComplate(1);

                // ilk animasyon bitti artık oynayabilir
                oyun.move();
            }
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

    public void move() {
        Point p = pacmanPosition;

        boolean right = map[p.y][p.x + 1] == 2 || map[p.y][p.x + 1] == 0;
        boolean left = map[p.y][p.x - 1] == 2 || map[p.y][p.x - 1] == 0;
        boolean top = map[p.y - 1][p.x] == 2 || map[p.y - 1][p.x] == 0;
        boolean bottom = map[p.y + 1][p.x] == 2 || map[p.y + 1][p.x] == 0;

        if (pacmanAspect == Yon.EAST && right) {
            p.setLocation(p.x + 1, p.y);
        }
        else if (pacmanAspect == Yon.WEST && left) {
            p.setLocation(p.x - 1, p.y);
        }
        else if (pacmanAspect == Yon.NORTH && top) {
            p.setLocation(p.x, p.y - 1);
        }
        else if (pacmanAspect == Yon.SOUTH && bottom) {
            p.setLocation(p.x, p.y + 1);
        }

        // seçtiği yön ile gideceği nokta alakalı değilse
        // örneğin sağa gidecek ama duvar var
        else {
            if (right) {
                p.setLocation(p.x + 1, p.y);
                pacmanAspect = Yon.EAST;
            }
            else if (bottom) {
                p.setLocation(p.x, p.y + 1);
                pacmanAspect = Yon.SOUTH;
            }
            else if (left) {
                p.setLocation(p.x - 1, p.y);
                pacmanAspect = Yon.WEST;
            }
            else if (top) {
                p.setLocation(p.x, p.y - 1);
                pacmanAspect = Yon.NORTH;
            }
        }

        System.out.println("x: " + p.x + ", y: " + p.y);
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

        // hareket edince animasyon yeniden başlar
        this.animationComplate = 1;
    }

    public void keyReleased(KeyEvent e) {

    }
}
