package org.example.gamearkanoid.model.powerup;

import javafx.scene.Group;
import javafx.scene.image.Image;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.Paddle;
import java.util.List;

public class PowerBlinkBall extends PowerUp {

    private static final double DURATION_FRAMES = 10 * 60;
    private List<Ball> targetBalls;

    public PowerBlinkBall(double x, double y, Image image) {
        super(x, y, image, DURATION_FRAMES);
    }

    @Override
    public void applyEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        this.targetBalls = balls; // Lưu lại danh sách bóng
    }

    @Override
    public void removeEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        if (targetBalls != null) {
            for (Ball ball : targetBalls) {
                ball.getBallImgView().setOpacity(1.0);
            }
        }
    }

    @Override
    public void update(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        // Gọi update() của lớp cha để xử lý item rơi và đếm ngược
        super.update(paddle, balls, blocks, group);

        // Logic nhấp nháy
        if (isActive() && targetBalls != null) {
            double opacity = (timer % 20 < 10) ? 0.2 : 1.0;
            for (Ball ball : targetBalls) {
                ball.getBallImgView().setOpacity(opacity);
            }
        }
    }
}