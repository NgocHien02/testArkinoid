package org.example.gamearkanoid.model.powerup;

import javafx.scene.Group;
import javafx.scene.image.Image;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.Paddle;
import java.util.List;

public class PowerSmallPaddle extends PowerUp {

    private static final double DURATION_FRAMES = 8 * 60; // 8 giây
    private static final double SCALE_FACTOR = 0.7;
    private Paddle targetPaddle;

    public PowerSmallPaddle(double x, double y, Image image) {
        super(x, y, image, DURATION_FRAMES);
    }

    @Override
    public void applyEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        this.targetPaddle = paddle;
        // Lấy kích thước gốc 100% THỰC SỰ từ đối tượng paddle
        double trueOriginalWidth = paddle.getOriginalWidth();
        // Luôn đặt kích thước dựa trên kích thước gốc
        paddle.getPaddleImgView().setFitWidth(trueOriginalWidth * SCALE_FACTOR);
    }

    @Override
    public void removeEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        if (targetPaddle != null) {
            // Trả về kích thước gốc 100% THỰC SỰ
            targetPaddle.getPaddleImgView().setFitWidth(targetPaddle.getOriginalWidth());
        }
    }

    @Override
    public PowerUpType getType() {
        return PowerUpType.SMALL_PADDLE;
    }
}