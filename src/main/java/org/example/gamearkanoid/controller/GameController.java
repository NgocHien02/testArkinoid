package org.example.gamearkanoid.controller;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.GameMap;
import org.example.gamearkanoid.model.Paddle;
import org.example.gamearkanoid.model.Brick;
import org.example.gamearkanoid.model.powerup.PowerUp;
import org.example.gamearkanoid.model.powerup.PowerUpFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameController {
    private double originPositionX;
    private double newPositionX, mousePosX;
    private Scene scene;
    private Group group;

    // --- SỬA ĐỔI: Thêm 2 danh sách quản lý ---
    private List<Ball> balls = new ArrayList<>();
    private List<PowerUp> activePowerUps = new ArrayList<>();

    public GameController(Scene scene, Group group) {
        this.scene = scene;
        this.group = group;

    }

    public void dragPaddle(Paddle paddle) {
        EventHandler<MouseEvent> paddlePress = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mousePosX = mouseEvent.getSceneX();
                originPositionX = ((Node) mouseEvent.getSource()).getTranslateX();
            }
        };

        EventHandler<MouseEvent> paddleDrag = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double amountMove = mouseEvent.getSceneX() - mousePosX;
                newPositionX = originPositionX + amountMove;
                ((Node) mouseEvent.getSource()).setTranslateX(newPositionX);
            }
        };

        paddle.getPaddleImgView().setOnMousePressed(paddlePress);
        paddle.getPaddleImgView().setOnMouseDragged(paddleDrag);
    }

    public void ballMovement(Ball initialBall, Paddle paddle, BlockBrick blocks) {

        // Thêm quả bóng đầu tiên vào danh sách quản lý
        this.balls.add(initialBall);

        AnimationTimer at = new AnimationTimer() {
            @Override
            public void handle(long l) {

                // === 1. VÒNG LẶP QUẢN LÝ BÓNG ===
                // Dùng Iterator để có thể xóa bóng khi nó rơi ra ngoài
                Iterator<Ball> ballIterator = balls.iterator();
                while (ballIterator.hasNext()) {
                    Ball ballObj = ballIterator.next();

                    // 1a. Cập nhật vị trí và va chạm
                    ballObj.updatePosition();
                    ballObj.checkPaddle(paddle);
                    ballObj.checkBorder(scene); // Đã tích hợp Shield

                    // 1b. Va chạm gạch và spawn Power-Up
                    Brick brokenBrick = ballObj.checkBlock(blocks, group); // Đã tích hợp StrongBall

                    if (brokenBrick != null) {
                        PowerUp newPowerUp = PowerUpFactory.randomPower(
                                brokenBrick.getX() + brokenBrick.getWidth() / 2,
                                brokenBrick.getY()
                        );

                        if (newPowerUp != null) {
                            activePowerUps.add(newPowerUp);
                            group.getChildren().add(newPowerUp.getImageView());
                        }
                    }

                    // 1c. Nếu bóng chạm đáy (và không có khiên), nó sẽ bị dừng
                    if (ballObj.getDirectionX() == 0 && ballObj.getDirectionY() == 0) {
                        ballIterator.remove(); // Xóa bóng khỏi danh sách
                        group.getChildren().remove(ballObj.getBallImgView()); // Xóa ảnh
                    }
                } // Kết thúc vòng lặp bóng

                // === 2. VÒNG LẶP QUẢN LÝ POWER-UP ===
                Iterator<PowerUp> powerUpIterator = activePowerUps.iterator();
                while (powerUpIterator.hasNext()) {
                    PowerUp p = powerUpIterator.next();

                    // Cập nhật (cho rơi hoặc đếm ngược)
                    // Truyền toàn bộ danh sách bóng
                    p.update(paddle, balls, blocks, group);

                    // Kiểm tra va chạm với paddle
                    if (!p.isCollected() && p.checkPaddleCollision(paddle)) {
                        p.activate(paddle, balls, blocks, group); // Kích hoạt
                        group.getChildren().remove(p.getImageView()); // Xóa item
                    }

                    // Xóa Power-Up nếu hết hạn hoặc rơi ra ngoài
                    if ((p.isCollected() && !p.isActive()) || p.getY() > scene.getHeight()) {
                        powerUpIterator.remove();
                    }
                } // Kết thúc vòng lặp power-up

                // === 3. XỬ LÝ GAME OVER ===
                if (balls.isEmpty()) {
                    System.out.println("GAME OVER");
                    this.stop(); // Dừng game
                    // (Thêm logic hiển thị màn hình thua)
                }
            }
        };
        at.start();
    }
}
