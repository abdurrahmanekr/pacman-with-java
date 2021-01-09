package main.java.pacmanoyunu;

import java.awt.*;
import java.util.Random;

public class Dusman {
    private Yon aspect;
    private Point point;
    private Point beforePoint;
    private Point pacmanLastPoint;
    private Color color;

    Dusman() {
    }

    Dusman(Yon aspect, Point point, Point pacmanLastPoint) {
        this.aspect = aspect;
        this.point = point;
        this.pacmanLastPoint = pacmanLastPoint;

        Random random = new Random();
        this.color = new Color(Math.abs(random.nextInt() % 255), Math.abs(random.nextInt() % 255), Math.abs(random.nextInt() % 255));
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.beforePoint = this.point;
        this.point = point;
    }

    public Point getBeforePoint() {
        return this.beforePoint;
    }

    public Point getPacmanLastPoint() {
        return pacmanLastPoint;
    }

    public void setPacmanLastPoint(Point pacmanLastPoint) {
        this.pacmanLastPoint = pacmanLastPoint;
    }

    public Yon getAspect() {
        return aspect;
    }

    public void setAspect(Yon aspect) {
        this.aspect = aspect;
    }

    public Color getColor() {
        return color;
    }
}
