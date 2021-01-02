package main.java.pacmanoyunu;

import java.awt.*;

public class Dusman {
    private Yon aspect;
    private int speed = 1;
    private Point point;

    Dusman() {

    }

    Dusman(Yon aspect, int speed, Point point) {
        this.aspect = aspect;
        this.speed = speed;
        this.point = point;
    }
}
