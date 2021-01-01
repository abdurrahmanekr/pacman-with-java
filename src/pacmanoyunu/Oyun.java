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
     */
    private byte[][] map = new byte[][] {
            {3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3},
            {3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3},
            {3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3},
            {3, 2, 3, 2, 3, 1, 1, 1, 1, 3, 2, 3, 2, 2, 3},
            {3, 2, 3, 2, 3, 0, 0, 0, 0, 3, 2, 3, 2, 2, 3},
            {3, 2, 3, 2, 3, 0, 0, 0, 0, 3, 2, 3, 2, 2, 3},
            {3, 2, 3, 2, 3, 0, 0, 0, 0, 3, 2, 3, 2, 2, 3},
            {3, 2, 3, 2, 3, 0, 0, 0, 0, 3, 2, 3, 2, 2, 3},
            {3, 2, 3, 2, 3, 0, 0, 0, 0, 3, 2, 3, 2, 2, 3},
            {3, 2, 3, 2, 3, 1, 1, 1, 1, 3, 2, 3, 2, 2, 3},
            {3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3},
            {3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3},
            {3, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3},
            {3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3},
            {3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3},
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
