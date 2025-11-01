package org.example.gamearkanoid.model.powerup;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.GameState;
import org.example.gamearkanoid.view.BallView;
import org.example.gamearkanoid.view.PaddleView;

import java.util.List;

public class PowerShield extends PowerUp {

    // duration = 0: Vĩnh viễn (cho đến khi sử dụng)
    public PowerShield(double x, double y, Image image) {
        super(x, y, image, 0);
    }

    @Override
    public void applyEffect(PaddleView paddle, List<BallView> balls, List<Ball> ballList, BlockBrick blocks, Pane pane) {
        GameState.shieldActive = true;
    }

    @Override
    public void removeEffect(PaddleView paddle, List<BallView> balls, List<Ball> ballList, BlockBrick blocks, Pane pane) {
        // Sẽ bị tắt bởi Ball.java khi va chạm
        GameState.shieldActive = false;
    }

    @Override
    public PowerUpType getType() {
        return PowerUpType.SHIELD;
    }
}