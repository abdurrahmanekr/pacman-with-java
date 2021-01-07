package main.java.pacmanoyunu;

import java.awt.*;

public class Dusman {
    private Yon aspect;
    private Point point;

    Dusman() {
    }

    Dusman(Yon aspect, Point point) {
        this.aspect = aspect;
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public Yon getAspect() {
        return aspect;
    }
}
