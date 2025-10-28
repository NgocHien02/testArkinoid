package org.example.gamearkanoid.model;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Ball extends Sprite {
    Image image = new Image(getClass().getResourceAsStream("/images/ball.png"),30,30,true,false);
    ImageView ballImgView = new ImageView(image);


    public Ball(double x, double y) {
        super(x, y, 30, 30);
        ballImgView.setX(x);
        ballImgView.setY(y);
        ballImgView.setFitHeight(30);
        ballImgView.setFitWidth(30);
        dirX = 1;
        dirY = 1;
        speed = 2.5;
    }

    @Override
    public void update() {

    }

    public void updatePosition() {
        ballImgView.setX(ballImgView.getX() + dirX * speed);
        ballImgView.setY(ballImgView.getY() + dirY * speed);
    }

    public void checkPaddle(Paddle paddle) {
        ImageView ball_obj = ballImgView;
        ImageView paddle_obj = paddle.getPaddleImgView();


        var ballBounds = ball_obj.getBoundsInParent();
        var paddleBounds = paddle_obj.getBoundsInParent();

        if (ballBounds.intersects(paddleBounds)) {

            double ballCenterX = ball_obj.getX() + ball_obj.getFitWidth() / 2;
            double paddleCenterX = paddle_obj.getX() + paddle_obj.getFitWidth() / 2;
            double collisionPoint = ballCenterX - paddleCenterX;

            double paddleThird = paddle_obj.getFitWidth() / 3;

            if (collisionPoint < -paddleThird / 2) { // Va chạm ở phần bên trái của thanh trượt
                setDirection(1, -dirY); // Bật sang trái
            } else if (collisionPoint > paddleThird / 2) { // Va chạm ở phần bên phải
                setDirection(-1, -dirY);// Bật sang phải
            } else {
                setDirection(dirX, -dirY);

            }
        }
    }

    public void checkBorder(Scene scene) {
        ImageView ball = ballImgView;
        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();

        if (ball.getX() + ball.getFitWidth() > sceneWidth || ball.getX() < 0) {
            setDirection(-dirX, dirY);
        }


        // Xử lý va chạm biên trên
        if (ball.getY() < 0) {
            setDirection(dirX, -dirY);// 1. Đổi hướng
            ball.setY(0);
        }

        // Xử lý va chạm biên dưới (Game Over)
        if (ball.getY() + ball.getFitHeight() > sceneHeight) {
            // Dừng bóng lại khi chạm đáy
            setDirection(0, 0);
            // Kẹp lại vị trí để bóng dừng ngay tại mép đáy, không bị lún xuống
            ball.setY(sceneHeight - ball.getFitHeight());

        }
    }

    public boolean checkBlock(BlockBrick block, Group group) {
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

                if (Math.abs(dx) > Math.abs(dy)) {
                    // Va chạm theo chiều ngang
                    setDirection(-dirX, dirY);
                } else {
                    // Va chạm theo chiều dọc
                    setDirection(dirX, -dirY);
                }
                if (brick.isIndestructible()) {
                    // Gạch không thể phá -> bỏ qua
                } else {
                    brick.takeDamage();

                    if (brick.isDestroyed()) {
                        toRemove.add(brick);
                    }
                }
                break; // chỉ xử lý 1 viên mỗi lần
            }
        }

        for (Brick b : toRemove) {
            group.getChildren().remove(b.getBrickImageView());
            block.getBlock().remove(b);
        }
        return allDestructibleBricksDestroyed(block);
    }

    private boolean allDestructibleBricksDestroyed(BlockBrick block) {
        for (Brick brick : block.getBlock()) {
            if (!brick.isIndestructible()) {
                return false;
            }
        }
        return true;
    }


    public ImageView getBallImgView() {
        return ballImgView;
    }
}
