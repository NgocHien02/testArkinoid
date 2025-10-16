package org.example.gamearkanoid.model;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Ball {
    Image image = new Image(getClass().getResourceAsStream("/images/ball.png"),30,30,true,false);
    ImageView ballImgView = new ImageView(image);

    private double speed = 0.5;
    private int directionX = 1;
    private int directionY = 1;

    public Ball(double x, double y) {
        ballImgView.setX(x);
        ballImgView.setY(y);
        ballImgView.setFitHeight(30);
        ballImgView.setFitWidth(30);
    }

    public void updatePosition() {
        ballImgView.setX(ballImgView.getX() + directionX * speed);
        ballImgView.setY(ballImgView.getY() + directionY * speed);
    }

    public void checkPaddle(Paddle paddle) {
        ImageView ball_obj = ballImgView;
        ImageView paddle_obj = paddle.getPaddleImgView();

        // Lấy hình chữ nhật bao quanh của bóng và thanh trượt.
        // Đây là cách chuẩn và chính xác nhất trong JavaFX.
        var ballBounds = ball_obj.getBoundsInParent();
        var paddleBounds = paddle_obj.getBoundsInParent();

        // 1. Kiểm tra xem bóng và thanh trượt có va chạm không
        if (ballBounds.intersects(paddleBounds)) {

            // 2. Nếu có va chạm, luôn luôn đổi hướng Y của bóng (bật lên)
            setDirectionY(-1);

            // 3. Xác định hướng X mới dựa trên vị trí va chạm
            double ballCenterX = ball_obj.getX() + ball_obj.getFitWidth() / 2;
            double paddleCenterX = paddle_obj.getX() + paddle_obj.getFitWidth() / 2;

            // Tính toán điểm va chạm tương đối so với tâm của thanh trượt
            double collisionPoint = ballCenterX - paddleCenterX;

            // Chia thanh trượt làm 3 phần để quyết định hướng bật ra
            double paddleThird = paddle_obj.getFitWidth() / 3;

            if (collisionPoint < -paddleThird / 2) { // Va chạm ở phần bên trái của thanh trượt
                setDirectionX(-1); // Bật sang trái
            } else if (collisionPoint > paddleThird / 2) { // Va chạm ở phần bên phải
                setDirectionX(1); // Bật sang phải
            } else {
                // Va chạm ở giữa, có thể không đổi hướng X hoặc cho nó một hướng ngẫu nhiên nhẹ
                // Ở đây ta giữ nguyên hướng X hiện tại hoặc đặt nó bằng 0 nếu muốn nó bật thẳng lên
                // setDirectionX(getDirectionX()); // Không thay đổi
            }
        }
    }

    public void checkBorder(Scene scene) {
        ImageView ball = ballImgView;
        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();

        // Xử lý va chạm biên phải
        if (ball.getX() + ball.getFitWidth() > sceneWidth) { // Dùng > thay vì >= để chắc chắn
            setDirectionX(-1); // 1. Đổi hướng
            ball.setX(sceneWidth -  ball.getFitWidth()); // 2. KẸP LẠI VỊ TRÍ: Đặt bóng nằm sát mép phải
//            System.out.println(ball.getFitWidth());
        }

        // Xử lý va chạm biên trái
        if (ball.getX() < 0) {
            setDirectionX(1); // 1. Đổi hướng
            ball.setX(0);     // 2. KẸP LẠI VỊ TRÍ: Đặt bóng nằm sát mép trái
        }

        // Xử lý va chạm biên trên
        if (ball.getY() < 0) {
            setDirectionY(1); // 1. Đổi hướng
            ball.setY(0);     // 2. KẸP LẠI VỊ TRÍ: Đặt bóng nằm sát mép trên
        }

        // Xử lý va chạm biên dưới (Game Over)
        if (ball.getY() + ball.getFitHeight() > sceneHeight) {
            // Dừng bóng lại khi chạm đáy
            setDirectionY(0);
            setDirectionX(0);
            // Kẹp lại vị trí để bóng dừng ngay tại mép đáy, không bị lún xuống
            ball.setY(sceneHeight - ball.getFitHeight());

            // Tại đây bạn có thể gọi hàm xử lý thua cuộc
            // handleGameOver();
        }
    }

    public void checkBlock(BlockBrick block, Group group) {
        ImageView ball = ballImgView;
        List<Brick> toRemove = new ArrayList<>();

        for (Brick brick : block.getBlock()) {
            if (ball.getBoundsInParent().intersects(brick.getBrickImageView().getBoundsInParent())) {

                // Xác định hướng va chạm
                double ballCenterX = ball.getX() + ball.getFitWidth() / 2;
                double ballCenterY = ball.getY() + ball.getFitHeight() / 2;
                double brickCenterX = brick.getX() + brick.getWidth() / 2;
                double brickCenterY = brick.getY() + brick.getHeight() / 2;

                double dx = ballCenterX - brickCenterX;
                double dy = ballCenterY - brickCenterY;

                // So sánh độ chênh để biết hướng va chạm
                if (Math.abs(dx) > Math.abs(dy)) {
                    // Va chạm theo chiều ngang
                    setDirectionX(dx > 0 ? 1 : -1);
                } else {
                    // Va chạm theo chiều dọc
                    setDirectionY(dy > 0 ? 1 : -1);
                }

                // Thêm vào danh sách xóa
                toRemove.add(brick);
                break; // chỉ xử lý 1 viên mỗi lần
            }
        }

        // Xóa sau khi kiểm tra xong để tránh lỗi lặp
        for (Brick b : toRemove) {
            group.getChildren().remove(b.getBrickImageView());
            block.getBlock().remove(b);
        }
    }


    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDirectionX() {
        return directionX;
    }

    public void setDirectionX(int directionX) {
        this.directionX = directionX;
    }

    public int getDirectionY() {
        return directionY;
    }

    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }

    public ImageView getBallImgView() {
        return ballImgView;
    }
}
