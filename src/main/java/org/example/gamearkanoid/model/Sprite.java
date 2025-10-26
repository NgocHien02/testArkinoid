package org.example.gamearkanoid.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Sprite extends BaseObject {
    protected double dirX;
    protected double dirY;
    protected double speed;
    protected double screenWidth;
    protected double screenHeight;

    public boolean checkCollision(Sprite other) {
        double centerX = getCenterX();
        double centerY = getCenterY();
        double targetCenterX = other.getCenterX();
        double targetCenterY = other.getCenterY();

        double targetX = other.getX();
        double targetY = other.getY();

        double dx = Math.abs(targetCenterX - centerX);
        double dy = Math.abs(targetCenterY - centerY);

        boolean checkDx = dx <= (width + other.width) / 2;
        boolean checkDy = dy <= (height + other.height) / 2;

        return checkDx && checkDy;
    }

    public Sprite(double x, double y, double width, double height) {
        super(x, y);
        this.height = height;
        this.width = width;
        dirY = 0;
        dirX = 0;
        speed = 0;

    }

    public void setDirection(double dirX, double dirY) {
        double tmp = Math.sqrt(dirX * dirX + dirY * dirY);
        if (tmp > 0) {
            this.dirX = dirX / tmp;
            this.dirY = dirY / tmp;
        }
        else {
            this.dirX = 0;
            this.dirY = 0;
        }
    }

    public void move() {
        x += dirX * speed;
        y += dirY * speed;
    }

    public boolean inScreen() {
        return (x + dirX * speed + width <= screenHeight
                && y + dirY * speed + height <= screenHeight
                && x + dirX * speed >= 0
                && y + dirY * speed >= 0);
    }
    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }


}
