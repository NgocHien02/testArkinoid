package org.example.gamearkanoid.model.powerup;

import javafx.scene.Group;
import javafx.scene.image.Image;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.Paddle;
import java.util.List;

public class PowerBigPaddle extends PowerUp {

    private static final double DURATION_FRAMES = 10 * 60; // 10 giây
    private static final double SCALE_FACTOR = 1.5;
    private double originalWidth;
    private Paddle targetPaddle;

    public PowerBigPaddle(double x, double y, Image image) {
        super(x, y, image, DURATION_FRAMES);
    }

    @Override
    public void applyEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        this.targetPaddle = paddle;
        this.originalWidth = paddle.getPaddleImgView().getFitWidth();
        paddle.getPaddleImgView().setFitWidth(originalWidth * SCALE_FACTOR);
    }

    @Override
    public void removeEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        if (targetPaddle != null) {
            targetPaddle.getPaddleImgView().setFitWidth(originalWidth);
        }
    }
}