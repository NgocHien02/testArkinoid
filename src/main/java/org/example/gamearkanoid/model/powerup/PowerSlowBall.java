package org.example.gamearkanoid.model.powerup;

import javafx.scene.Group;
import javafx.scene.image.Image;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.GameState;
import org.example.gamearkanoid.model.Paddle;

import java.util.List;

public class PowerSlowBall extends PowerUp {

    private static final double DURATION_FRAMES = 5 * 60; //5 giây
    private static final double SLOW_FACTOR = 0.7;
    private boolean isCurrentlySlow = false;

    public PowerSlowBall(double x, double y, Image image) {
        super(x, y, image, DURATION_FRAMES);
    }

    @Override
    public void applyEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {

    }

    @Override
    public void removeEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        if (balls.isEmpty()) return;
        double trueOriginalSpeed = balls.get(0).getOriginalSpeed();

        // Nếu bóng đang chậm, khôi phục tốc độ
        if (isCurrentlySlow) {
            for (Ball ball : balls) {
                ball.setSpeed(trueOriginalSpeed);
            }
        }
    }
    @Override
    public void update(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        // Gọi update của cha để đếm ngược thời gian
        super.update(paddle, balls, blocks, group);

        if (!isActive() || balls.isEmpty()) return;

        double trueOriginalSpeed = balls.get(0).getOriginalSpeed();

        // 1. Nếu BlinkBall đang bật...
        if (GameState.blinkBallActive) {
            // ...và nếu bóng đang chậm...
            if (isCurrentlySlow) {
                // ...hãy "tạm dừng" nó (trả về tốc độ gốc)
                for (Ball ball : balls) {
                    ball.setSpeed(trueOriginalSpeed);
                }
                isCurrentlySlow = false; // Đánh dấu là đã tạm dừng
            }
        }
        // 2. Nếu BlinkBall đang TẮT...
        else {
            // ...và nếu bóng đang KHÔNG chậm...
            if (!isCurrentlySlow) {
                // ...hãy "kích hoạt" hiệu ứng (làm chậm)
                for (Ball ball : balls) {
                    ball.setSpeed(trueOriginalSpeed * SLOW_FACTOR);
                }
                isCurrentlySlow = true; // Đánh dấu là đang chậm
            }
        }
    }

    @Override
    public PowerUpType getType() {
        return PowerUpType.SLOW_BALL;
    }
}