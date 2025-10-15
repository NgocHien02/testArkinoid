package org.example.gamearkanoid.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle {
    private Rectangle paddle = new Rectangle(100, 20, Color.BLACK);

    public Paddle(double x, double y) {
        paddle.setX(x);
        paddle.setY(y);
    }

    public Rectangle getPaddle() {
        return paddle;
    }

    public void setPaddle(Rectangle paddle) {
        this.paddle = paddle;
    }
}
