package org.example.gamearkanoid.model.powerup;

import javafx.scene.Group;
import javafx.scene.image.Image;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.Paddle;
import java.util.List;
import java.util.Random;

public class PowerMultiBall extends PowerUp {

    private Random random = new Random();

    // duration = 0: Vĩnh viễn
    public PowerMultiBall(double x, double y, Image image) {
        super(x, y, image, 0);
    }

    @Override
    public void applyEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        if (balls.isEmpty()) return; // Không có bóng gốc để nhân bản

        // Lấy bóng đầu tiên làm mẫu
        Ball originalBall = balls.get(0);

        // Tạo thêm 1-3 quả bóng mới
        int newBallsCount = random.nextInt(3) + 1;

        for (int i = 0; i < newBallsCount; i++) {
            // Tạo bóng mới tại vị trí bóng gốc
            Ball newBall = new Ball(originalBall.getBallImgView().getX(), originalBall.getBallImgView().getY());

            newBall.setSpeed(originalBall.getSpeed());
            newBall.setDirectionX(random.nextBoolean() ? 1 : -1); // Hướng X ngẫu nhiên
            newBall.setDirectionY(-1); // Luôn bay lên

            // THÊM VÀO 2 NƠI:
            group.getChildren().add(newBall.getBallImgView()); // 1. Thêm ảnh vào Scene
            balls.add(newBall);                                // 2. Thêm vào danh sách quản lý
        }
    }

    @Override
    public void removeEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        // Không làm gì cả
    }
}