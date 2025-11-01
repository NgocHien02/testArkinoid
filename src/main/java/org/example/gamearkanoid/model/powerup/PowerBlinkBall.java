package org.example.gamearkanoid.model.powerup;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.GameState;
import org.example.gamearkanoid.view.BallView;
import org.example.gamearkanoid.view.PaddleView;

import java.util.List;

public class PowerBlinkBall extends PowerUp {

    private static final double DURATION_FRAMES = 8 * 60; //8 giây
    private List<BallView> targetBalls;

    public PowerBlinkBall(double x, double y, Image image) {
        super(x, y, image, DURATION_FRAMES);
    }

    @Override
    public void applyEffect(PaddleView paddle, List<BallView> balls, List<Ball> ballList, BlockBrick blocks, Pane group) {
        this.targetBalls = balls; // Lưu lại danh sách bóng
        GameState.blinkBallActive = true;
    }

    @Override
    public void removeEffect(PaddleView paddle, List<BallView> balls, List<Ball> ballList, BlockBrick blocks, Pane group) {
        GameState.blinkBallActive = false;
        if (targetBalls != null) {
            for (BallView ball : targetBalls) {
                ball.getImageView().setOpacity(1.0);
            }
        }
    }

    @Override
    public void update(PaddleView paddle, List<BallView> balls, List<Ball> ballList, BlockBrick blocks, Pane group) {
        // Gọi update() của lớp cha để xử lý item rơi và đếm ngược
        super.update(paddle, balls, ballList, blocks, group);

        // Logic nhấp nháy
        if (isActive() && targetBalls != null) {
            double opacity = (timer % 60 < 30) ? 0.0 : 1.0;

            for (BallView ball : targetBalls) {
                ball.getImageView().setOpacity(opacity);
            }
        }
    }

    @Override
    public PowerUpType getType() {
        return PowerUpType.BLINK_BALL;
    }
}