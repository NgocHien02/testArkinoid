package org.example.gamearkanoid.model.powerup;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.GameState;
import org.example.gamearkanoid.view.BallView;
import org.example.gamearkanoid.view.PaddleView;

import java.util.List;

public class PowerSlowBall extends PowerUp {

    private static final double DURATION_FRAMES = 5 * 60; //5 giây
    private static final double SLOW_FACTOR = 0.7;
    private boolean isCurrentlySlow = false;

    public PowerSlowBall(double x, double y, Image image) {
        super(x, y, image, DURATION_FRAMES);
    }

    @Override
    public void applyEffect(PaddleView paddle, List<BallView> balls, List<Ball> ballList, BlockBrick blocks, Pane pane) {

    }

    @Override
    public void removeEffect(PaddleView paddle, List<BallView> balls, List<Ball> ballList, BlockBrick blocks, Pane pane) {
        if (balls.isEmpty()) return;
        double trueOriginalSpeed = balls.get(0).getModel().getOriginalSpeed();

        // Nếu bóng đang chậm, khôi phục tốc độ
        if (isCurrentlySlow) {
            for (BallView ball : balls) {
                ball.getModel().setSpeed(trueOriginalSpeed);
            }
        }
    }
    @Override
    public void update(PaddleView paddle, List<BallView> balls, List<Ball> ballList, BlockBrick blocks, Pane pane) {
        // Gọi update của cha để đếm ngược thời gian
        super.update(paddle, balls, ballList, blocks, pane);

        if (!isActive() || balls.isEmpty()) return;

        double trueOriginalSpeed = balls.get(0).getModel().getOriginalSpeed();

        // 1. Nếu BlinkBall đang bật...
        if (GameState.blinkBallActive) {
            // ...và nếu bóng đang chậm...
            if (isCurrentlySlow) {
                // ...hãy "tạm dừng" nó (trả về tốc độ gốc)
                for (BallView ball : balls) {
                    ball.getModel().setSpeed(trueOriginalSpeed);
                }
                isCurrentlySlow = false; // Đánh dấu là đã tạm dừng
            }
        }
        // 2. Nếu BlinkBall đang TẮT...
        else {
            // ...và nếu bóng đang KHÔNG chậm...
            if (!isCurrentlySlow) {
                // ...hãy "kích hoạt" hiệu ứng (làm chậm)
                for (BallView ball : balls) {
                    ball.getModel().setSpeed(trueOriginalSpeed * SLOW_FACTOR);
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