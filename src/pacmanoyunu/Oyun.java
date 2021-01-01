package pacmanoyunu;

import java.util.HashMap;

public class Oyun {
    public static int MAP_SIZE = 15;

    private boolean isEnd;
    private int pacmanSpeed = 1;

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
}
