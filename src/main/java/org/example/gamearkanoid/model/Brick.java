package org.example.gamearkanoid.model;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Brick {
    private Rectangle brick = new Rectangle();

    public static final double DEFAULT_HEIGHT_BRICK = 50;
    public static final double DEFAULT_WIDTH_BRICK = 100;
    public static final Paint DEFAULT_COLOR_BRICK = Color.BLACK;


    public Brick(double positionX, double positionY) {
        brick.setHeight(DEFAULT_HEIGHT_BRICK);
        brick.setWidth(DEFAULT_WIDTH_BRICK);
        brick.setFill(DEFAULT_COLOR_BRICK);
        brick.setX(positionX);
        brick.setY(positionY);
    }

    public Rectangle getBrick() {
        return brick;
    }

    public void setBrick(Rectangle brick) {
        this.brick = brick;
    }

    public double getX() {
        return brick.getX();
    }

    public double getY() {
        return brick.getY();
    }

    public double getHeight() {
        return brick.getHeight();
    }

    public double getWidth() {
        return brick.getWidth();
    }
}
