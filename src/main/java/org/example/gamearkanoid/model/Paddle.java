package org.example.gamearkanoid.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends  Sprite{
    private Image image = new Image(getClass().getResourceAsStream("/images/paddle.png"), 100, 50, true, false);
    private ImageView paddleImgView = new ImageView(image);
    private boolean goLeft = false;
    private boolean goRight = false;

    public Paddle(double x, double y) {
        super(x, y, 100, 50);
        paddleImgView.setX(x);
        paddleImgView.setY(y);
        paddleImgView.setFitWidth(100);
        paddleImgView.setFitHeight(50);
    }

    @Override
    public void update() {
        if (!inScreen()) {

        }
    }


    public ImageView getPaddleImgView() {
        return paddleImgView;
    }

    public void setPaddleImgView(ImageView paddleImgView) {
        this.paddleImgView = paddleImgView;
    }


    public boolean isGoRight() {
        return goRight;
    }

    public void setGoRight(boolean goRight) {
        this.goRight = goRight;
    }

    public boolean isGoLeft() {
        return goLeft;
    }

    public void setGoLeft(boolean goLeft) {
        this.goLeft = goLeft;
    }
}
