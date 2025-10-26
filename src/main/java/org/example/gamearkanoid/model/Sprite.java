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
        double dx = Math.abs(targetCenterX - centerX);
        double dy = Math.abs(targetCenterY - centerY);

        boolean checkDx = dx <= (getWidth() + other.getWidth()) / 2;
        boolean checkDy = dy <= (getHeight()+ other.getHeight()) / 2;

        return checkDx && checkDy;
    }

    public Sprite(double x, double y, double width, double height) {
        super(x, y);
        this.width.set(width);
        this.height.set(height);
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
        setPosition(getX() + dirX * speed, getY() + dirY * speed);
    }

    public boolean inScreen() {
        return (getX() + dirX * speed + getWidth() <= screenHeight
                && getY() + dirY * speed + getHeight() <= screenHeight
                && getX() + dirX * speed >= 0
                && getY() + dirY * speed >= 0);
    }

}
