package org.example.gamearkanoid.model.powerup;

import javafx.scene.Group;
import javafx.scene.image.Image;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.GameState;
import org.example.gamearkanoid.model.Paddle;
import java.util.List;

public class PowerShield extends PowerUp {

    // duration = 0: Vĩnh viễn (cho đến khi sử dụng)
    public PowerShield(double x, double y, Image image) {
        super(x, y, image, 0);
    }

    @Override
    public void applyEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        GameState.shieldActive = true;
    }

    @Override
    public void removeEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        // Sẽ bị tắt bởi Ball.java khi va chạm
        GameState.shieldActive = false;
    }

    @Override
    public PowerUpType getType() {
        return PowerUpType.SHIELD;
    }
}