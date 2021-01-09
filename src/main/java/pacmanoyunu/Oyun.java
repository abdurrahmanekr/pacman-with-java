package main.java.pacmanoyunu;

import com.sun.istack.internal.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Oyun {
    public static int MAP_SIZE = 15;
    public static final int DELAY = 10;
    public static final int PROCESS_DELAY = 300;

    private int animationComplate = 1;

    private ActionListener listener;
    private Timer timer;

    private boolean isStarted;
    private boolean isEnded;

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
    private Dusman[] enemies;

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
        enemies = new Dusman[] {
            new Dusman(Yon.NORTH, new Point(13, 12), new Point(this.pacmanPosition)),
//            new Dusman(Yon.WEST, new Point(12, 13), new Point(this.pacmanPosition)),
//            new Dusman(Yon.NORTH, new Point(13, 13), new Point(this.pacmanPosition)),
//            new Dusman(Yon.WEST, new Point(13, 13), new Point(this.pacmanPosition)),
        };
    }

    Oyun(@NotNull ActionListener listener) {
        this();

        this.listener = listener;
    }

    public void start() {
        isStarted = true;
        isEnded = false;

        this.fastTest();

        // eğer öncede oluşturulan bir timer varsa onu kapat
        if (timer != null)
            timer.stop();

        timer = new Timer(DELAY, new GameProcess(this));
        timer.start();
    }

    public void stop() {
        isEnded = true;

        timer.stop();
    }

    // oyunun zamanla ilerlemesi sağlar
    // animasyonları yavaş yavaş canlandırmak için
    class GameProcess implements ActionListener {
        private Oyun oyun;

        GameProcess(Oyun oyun) {
            this.oyun = oyun;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int anim = oyun.getAnimationComplate() + (Oyun.PROCESS_DELAY / Oyun.DELAY / 4); // 4 karede bitirsin

            // animasyon bitti ve artık iş yapma zamanı
            if (anim >= Oyun.PROCESS_DELAY) {
                oyun.setAnimationComplate(1);

                // ilk animasyon bitti artık oynayabilir
                oyun.move();

                // oyundaki düşmanları oyucunun en son görüldüğü noktaya ilet
                Random random = new Random();
                for (Dusman enemy : oyun.getEnemies()) {
                    Point eP = enemy.getPoint();
                    // düşman pacman'in geldiği noktaya kadar gelmiş
                    // o yüzden pacman'in şu anki noktası verelim
                    if (eP.distance(enemy.getPacmanLastPoint()) == 0) {
                        enemy.setPacmanLastPoint(new Point(pacmanPosition));
                    }

                    // en yakın yöne doğru ayarla
                    enemy.setAspect(getNearestWay(enemy));

                    if (enemy.getAspect() == Yon.SOUTH) {
                        enemy.setPoint(new Point(eP.x, eP.y + 1));
                    }
                    else if (enemy.getAspect() == Yon.NORTH) {
                        enemy.setPoint(new Point(eP.x, eP.y - 1));
                    }
                    else if (enemy.getAspect() == Yon.EAST) {
                        enemy.setPoint(new Point(eP.x + 1, eP.y));
                    }
                    else {
                        enemy.setPoint(new Point(eP.x - 1, eP.y));
                    }
                }
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

    public boolean isStarted() {
        return isStarted;
    }
    public boolean isEnded() {
        return isEnded;
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

        // eğer ulaştığı koktada yem varsa ye
        if (map[p.y][p.x] == 2) {
            eat(p.x, p.y);
        }

        // kazanmışsa oyunu sonlandır
        if (isWin()) {
            stop();
        }
    }

    public void eat(int x, int y) {
        map[y][x] = 0;
    }

    public boolean isWin() {
        boolean win = true;
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (map[i][j] == 2) {
                    win = false;
                    break;
                }
            }

            if (!win)
                break;
        }

        return win;
    }

    public void keyPressed(KeyEvent e) {
        // başlamamış oyunda sadece boşluğu dikkate al
        if (!isStarted && e.getKeyCode() != KeyEvent.VK_SPACE)
            return;

        Yon beforeAspect = this.pacmanAspect;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE: // oyunu başlat
                // oyun başladıktan sonra boşluk tuşu işlevi olmamalı
                if (isStarted && !isEnded)
                    return;

                this.start();
                break;
            case KeyEvent.VK_RIGHT:
                this.pacmanAspect = Yon.EAST;
                break;
            case KeyEvent.VK_LEFT:
                this.pacmanAspect = Yon.WEST;
                break;
            case KeyEvent.VK_DOWN:
                this.pacmanAspect = Yon.SOUTH;
                break;
            case KeyEvent.VK_UP:
                this.pacmanAspect = Yon.NORTH;
                break;
        }

        // oynadığı nokta çizgiye denk geliyorsa oynamasın
        if (isBorder()) {
            this.pacmanAspect = beforeAspect;
        }
        else {
            // hareket edince animasyon yeniden başlar
            if (beforeAspect != pacmanAspect) {
                this.animationComplate = 1;
            }
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    // öklid algoritmasına göre pacman'e gitmek için en yakın yön veriliyor
    public Yon getNearestWay(Dusman enemy) {
        Point pP = enemy.getPacmanLastPoint();
        Point eP = enemy.getPoint();

        double distance = 0;
        Yon neastYon = Yon.WEST;

        // güney
        if (isWay(eP.x, eP.y + 1)) {
            distance = new Point(eP.x, eP.y + 1).distance(pP);
            neastYon = Yon.SOUTH;
        }

        // kuzey
        if (isWay(eP.x, eP.y - 1)) {
            double tmpDis = new Point(eP.x, eP.y - 1).distance(pP);

            if (distance == 0 || tmpDis < distance) {
                distance = tmpDis;
                neastYon = Yon.NORTH;
            }
        }

        // doğu
        if (isWay(eP.x + 1, eP.y)) {
            double tmpDis = new Point(eP.x + 1, eP.y).distance(pP);

            if (distance == 0 || tmpDis < distance) {
                distance = tmpDis;
                neastYon = Yon.EAST;
            }
        }

        // batı
        if (isWay(eP.x - 1, eP.y)) {
            double tmpDis = new Point(eP.x - 1, eP.y).distance(pP);

            if (distance == 0 || tmpDis < distance) {
                neastYon = Yon.WEST;
            }
        }

        return neastYon;
    }

    // harita  üzerinde gidilebilir bir yer olup olmadığını döndürür
    public boolean isWay(int x, int y) {
        return map[y][x] == 2 || map[y][x] == 0;
    }

    // pacman'in yönüne bakarak yönü sınıra doğruysa bildirir
    public boolean isBorder() {
        Point pP = pacmanPosition;

        if (pacmanAspect == Yon.NORTH)
            return !isWay(pP.x, pP.y - 1);
        if (pacmanAspect == Yon.SOUTH)
            return !isWay(pP.x, pP.y + 1);
        if (pacmanAspect == Yon.EAST)
            return !isWay(pP.x + 1, pP.y);

        return !isWay(pP.x - 1, pP.y);
    }

    // düşmanın'in yönüne bakarak yönü sınıra doğruysa bildirir
    public boolean isBorder(Dusman enemy) {
        Point eP = enemy.getPoint();
        Yon eAs = enemy.getAspect();

        if (eAs == Yon.NORTH)
            return !isWay(eP.x, eP.y - 1);
        if (eAs == Yon.SOUTH)
            return !isWay(eP.x, eP.y + 1);
        if (eAs == Yon.EAST)
            return !isWay(eP.x + 1, eP.y);

        return !isWay(eP.x - 1, eP.y);
    }

    // oyunda sadece bir yem bırakmak için
    public void fastTest() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (map[i][j] == 2) {
                    map[i][j] = 0;
                }
            }
        }

        map[1][1] = 2;
    }
}
