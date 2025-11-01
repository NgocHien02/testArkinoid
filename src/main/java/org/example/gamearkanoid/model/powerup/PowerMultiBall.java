package org.example.gamearkanoid.model.powerup;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.view.BallView;
import org.example.gamearkanoid.view.PaddleView;

import java.util.List;
import java.util.Random;

public class PowerMultiBall extends PowerUp {

    private Random random = new Random();
    //Thời gian hiệu lực
    private static final double DURATION_FRAMES = 6 * 60; //6 giây
    // Thời gian bắt đầu nhấp nháy báo hiệu
    private static final double WARNING_FRAMES = 1.5 * 60;
    public PowerMultiBall(double x, double y, Image image) {
        super(x, y, image, DURATION_FRAMES);
    }

    @Override
    public void applyEffect(PaddleView paddle, List<BallView> balls, List<Ball> ballList, BlockBrick blocks, Pane pane) {
        if (balls.isEmpty()) return;

        // Lấy bóng đầu tiên làm mẫu
        Ball originalBall = balls.get(0).getModel();

        // Tạo thêm 2 quả bóng mới (tổng cộng sẽ có 3)
        int newBallsCount = 2;

        for (int i = 0; i < newBallsCount; i++) {
            Ball newBall = new Ball(originalBall.getX(), originalBall.getY());

            // Lấy tốc độ gốc 100% từ bóng mẫu
            newBall.setSpeed(originalBall.getOriginalSpeed());

            newBall.setDirection(random.nextBoolean() ? 1 : -1, -1);
            // Không cần logic 'isOriginal'
            newBall.setPaddle(paddle.getModel());
            newBall.setBlockBrick(blocks);

            BallView ballView = new BallView(newBall);
            // THÊM VÀO 2 NƠI:
            balls.add(ballView);                                // 1. Danh sách quản lý chính
            ballList.add(newBall);
            pane.getChildren().add(ballView.getImageView()); // 2. Thêm ảnh vào Scene
        }
    }

    @Override
    public void update(PaddleView paddle, List<BallView> balls, List<Ball> ballList,  BlockBrick blocks, Pane pane) {
        // Gọi update của cha để đếm ngược thời gian
        super.update(paddle, balls, ballList, blocks, pane);

        // Nếu power-up không hoạt động, không làm gì cả
        if (!isActive()) return;

        // Logic nhấp nháy khi gần hết giờ
        if (this.timer <= WARNING_FRAMES) {
            double opacity = (timer % 20 < 10) ? 0.0 : 1.0;
            // Áp dụng cho TẤT CẢ các bóng trong danh sách
            for (BallView ball : balls) {
                ball.getImageView().setOpacity(opacity);
            }
        } else {
            // Khi chưa đến 3 giây cuối, đảm bảo tất cả bóng đều hiện rõ
            for (BallView ball : balls) {
                ball.getImageView().setOpacity(1.0);
            }
        }
    }
    @Override
    public void removeEffect(PaddleView paddle, List<BallView> balls, List<Ball> ballList, BlockBrick blocks, Pane pane) {

        // Vòng lặp: Xóa ngẫu nhiên các bóng miễn là còn nhiều hơn 1 quả
        while (balls.size() > 1) {
            // Chọn 1 quả bóng ngẫu nhiên từ danh sách
            BallView ballToRemove = balls.get(random.nextInt(balls.size()));

            // Xóa nó khỏi 2 nơi
            pane.getChildren().remove(ballToRemove.getImageView());
            balls.remove(ballToRemove);
        }

        // Đảm bảo quả bóng CUỐI CÙNG còn lại phải hiện rõ
        if (!balls.isEmpty()) {
            balls.get(0).getImageView().setOpacity(1.0);
        }
    }

    @Override
    public PowerUpType getType() {
        return PowerUpType.MULTIBALL;
    }
}