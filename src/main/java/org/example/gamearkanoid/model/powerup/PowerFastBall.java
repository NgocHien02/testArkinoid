package org.example.gamearkanoid.model.powerup;

import javafx.scene.Group;
import javafx.scene.image.Image;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.GameState;
import org.example.gamearkanoid.model.Paddle;
import java.util.List;

public class PowerFastBall extends PowerUp {

    private static final double DURATION_FRAMES = 7 * 60; //7 giây
    private static final double FAST_FACTOR = 2.5;

    // Biến trạng thái để biết bóng CÓ THỰC SỰ đang nhanh không
    private boolean isCurrentlyFast = false;
    public PowerFastBall(double x, double y, Image image) {
        super(x, y, image, DURATION_FRAMES);
    }

    public void applyEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        // Phương thức này CẦN PHẢI TỒN TẠI để thỏa mãn lớp cha PowerUp.
        // Logic thực tế đã được chuyển vào update().
    }

    @Override
    public void removeEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        if (balls.isEmpty()) return;
        double trueOriginalSpeed = balls.get(0).getOriginalSpeed();

        // Nếu bóng đang nhanh, khôi phục lại tốc độ
        if (isCurrentlyFast) {
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

        // Lấy tốc độ gốc 100%
        double trueOriginalSpeed = balls.get(0).getOriginalSpeed();

        // 1. Nếu BlinkBall đang bật...
        if (GameState.blinkBallActive) {
            // ...và nếu bóng đang nhanh...
            if (isCurrentlyFast) {
                // ...hãy "tạm dừng" nó (trả về tốc độ gốc)
                for (Ball ball : balls) {
                    ball.setSpeed(trueOriginalSpeed);
                }
                isCurrentlyFast = false; // Đánh dấu là đã tạm dừng
            }
        }
        // 2. Nếu BlinkBall đang TẮT...
        else {
            // ...và nếu bóng đang KHÔNG nhanh...
            if (!isCurrentlyFast) {
                // ...hãy "kích hoạt" hiệu ứng (tăng tốc)
                for (Ball ball : balls) {
                    ball.setSpeed(trueOriginalSpeed * FAST_FACTOR);
                }
                isCurrentlyFast = true; // Đánh dấu là đang nhanh
            }
        }
    }

    @Override
    public PowerUpType getType() {
        return PowerUpType.FAST_BALL;
    }
}