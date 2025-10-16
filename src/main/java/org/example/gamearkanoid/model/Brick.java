package org.example.gamearkanoid.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Brick {
    //    private Image image;
    private ImageView brickImageView;
    private int health;
    private boolean destroyed = false;

    public static final double DEFAULT_HEIGHT_BRICK = 50;
    public static final double DEFAULT_WIDTH_BRICK = 100;
    public static final Paint DEFAULT_COLOR_BRICK = Color.BLACK;


    public Brick(double positionX, double positionY, Image image) {
        this.brickImageView = new ImageView(image);
        this.brickImageView.setX(positionX);
        this.brickImageView.setY(positionY);
    }

    public double getX() {
        return brickImageView.getX();
    }

    public double getY() {
        return brickImageView.getY();
    }

    public double getHeight() {
        return brickImageView.getFitHeight();
    }

    public double getWidth() {
        return brickImageView.getFitWidth();
    }

    public ImageView getBrickImageView() {
        return brickImageView;
    }

    public void setBrickImageView(ImageView brickImageView) {
        this.brickImageView = brickImageView;
    }
}
