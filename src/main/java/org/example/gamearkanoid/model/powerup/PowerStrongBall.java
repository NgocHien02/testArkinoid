package org.example.gamearkanoid.model.powerup;

import javafx.scene.Group;
import javafx.scene.image.Image;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.GameState;
import org.example.gamearkanoid.model.Paddle;
import java.util.List;

public class PowerStrongBall extends PowerUp {

    private static final double DURATION_FRAMES = 2 * 60; // 2 giây

    public PowerStrongBall(double x, double y, Image image) {
        super(x, y, image, DURATION_FRAMES);
    }

    @Override
    public void applyEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        GameState.strongBallArmed = true;
    }

    @Override
    public void removeEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        // Khi hết giờ, tắt cả 2 cờ
        GameState.strongBallActive = false;
        GameState.strongBallArmed = false;
    }
    @Override
    public void update(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {

        // 1. Kiểm tra xem chúng ta có đang ở trạng thái "lên đạn" (armed) không
        if (GameState.strongBallArmed) {

            // 2. Kiểm tra xem Ball.java đã kích hoạt hiệu ứng (active) chưa
            if (GameState.strongBallActive) {
                // Ball đã chạm gạch và kích hoạt chúng ta!
                // Giờ chúng ta tắt cờ "armed"
                GameState.strongBallArmed = false;

                // (Bây giờ chúng ta để logic update của lớp cha chạy,
                // nó sẽ bắt đầu đếm ngược 10 giây)

            } else {
                // Nếu chúng ta VẪN đang "armed" (chưa chạm gạch)
                // -> Timer bị "đóng băng"
                // -> KHÔNG GỌI super.update()
                // -> Chỉ gọi logic rơi (nếu chưa nhặt)
                if (!isCollected()) {
                    super.update(paddle, balls, blocks, group);
                }
                return; // Bỏ qua việc đếm ngược timer
            }
        }

        // 3. Nếu chúng ta không "armed" (hoặc vừa được kích hoạt),
        // thì chạy logic update bình thường của lớp cha (để đếm ngược timer)
        super.update(paddle, balls, blocks, group);
    }

    @Override
    public PowerUpType getType() {
        return PowerUpType.STRONG_BALL;
    }
}