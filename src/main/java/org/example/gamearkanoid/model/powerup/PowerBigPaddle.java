package org.example.gamearkanoid.model.powerup;

//import javafx.scene.Pane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.view.BallView;
import org.example.gamearkanoid.view.PaddleView;

import java.util.List;

public class PowerBigPaddle extends PowerUp {

    private static final double DURATION_FRAMES = 8 * 60; // 8 giây
    private static final double SCALE_FACTOR = 1.5;
    private PaddleView targetPaddle;

    public PowerBigPaddle(double x, double y, Image image) {
        super(x, y, image, DURATION_FRAMES);
    }

    @Override
    public void applyEffect(PaddleView paddle, List<BallView> balls, List<Ball> ballList, BlockBrick blocks, Pane pane) {
        this.targetPaddle = paddle;
        // Lấy kích thước gốc 100% THỰC SỰ từ đối tượng paddle
        double trueOriginalWidth = paddle.getModel().getOriginalWidth();

        // Luôn đặt kích thước dựa trên kích thước gốc
        paddle.getImageView().setFitWidth(trueOriginalWidth * SCALE_FACTOR);
        paddle.getModel().setWidth(trueOriginalWidth * SCALE_FACTOR);
    }

    @Override
    public void removeEffect(PaddleView paddle, List<BallView> balls, List<Ball> ballList, BlockBrick blocks, Pane pane) {
        // Trả về kích thước gốc 100% THỰC SỰ
        targetPaddle.getImageView().setFitWidth(targetPaddle.getModel().getOriginalWidth());
        targetPaddle.getModel().setWidth(targetPaddle.getModel().getOriginalWidth());
    }

    @Override
    public PowerUpType getType() {
        return PowerUpType.BIG_PADDLE;
    }
}