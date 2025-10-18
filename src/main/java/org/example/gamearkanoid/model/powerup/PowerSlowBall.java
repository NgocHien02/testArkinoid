package org.example.gamearkanoid.model.powerup;

import javafx.scene.Group;
import javafx.scene.image.Image;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.Paddle;
import java.util.List;

public class PowerSlowBall extends PowerUp {

    private static final double DURATION_FRAMES = 10 * 60;
    private static final double SLOW_FACTOR = 0.5;
    private double originalSpeed;

    public PowerSlowBall(double x, double y, Image image) {
        super(x, y, image, DURATION_FRAMES);
    }

    @Override
    public void applyEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        if (balls.isEmpty()) return;

        // Lưu tốc độ gốc (chỉ 1 lần)
        if (this.originalSpeed == 0) {
            this.originalSpeed = balls.get(0).getSpeed();
        }

        // Làm chậm tất cả bóng
        for (Ball ball : balls) {
            ball.setSpeed(originalSpeed * SLOW_FACTOR);
        }
    }

    @Override
    public void removeEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        // Khôi phục tốc độ cho tất cả bóng
        for (Ball ball : balls) {
            ball.setSpeed(originalSpeed);
        }
    }
}