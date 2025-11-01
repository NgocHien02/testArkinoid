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

// Import lớp quản lý Power-Up
import org.example.gamearkanoid.controller.PowerUpManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Lớp điều khiển chính của game.
 * Nhiệm vụ:
 * 1. Khởi động và quản lý vòng lặp game (AnimationTimer).
 * 2. Xử lý input của người chơi (kéo thả Paddle).
 * 3. Quản lý danh sách các quả bóng (List<Ball>).
 * 4. Ủy thác toàn bộ logic Power-Up cho PowerUpManager.
 */

public class GameController {
    private double originPositionX;
    private double newPositionX, mousePosX;
    private Scene scene;
    private Group group;

    // Danh sách quản lý TẤT CẢ các quả bóng đang có trên màn hình
    private List<Ball> balls = new ArrayList<>();

    // Đối tượng quản lý chuyên biệt cho Power-Up
    private PowerUpManager powerUpManager;

    public GameController(Scene scene, Group group) {
        this.scene = scene;
        this.group = group;

        // Khởi tạo PowerUpManager và trao cho nó quyền truy cập vào 'group' để nó có thể tự thêm/xóa hình ảnh Power-Up
        this.powerUpManager = new PowerUpManager(group);
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
                // Dùng Iterator để duyệt và xóa bóng an toàn (tránh lỗi)
                Iterator<Ball> ballIterator = balls.iterator();
                while (ballIterator.hasNext()) {
                    Ball ballObj = ballIterator.next();

                    // 1a. Cập nhật vị trí và va chạm
                    ballObj.updatePosition();
                    ballObj.checkPaddle(paddle);
                    ballObj.checkBorder(scene);

                    // 1b. Va chạm gạch
                    Brick brokenBrick = ballObj.checkBlock(blocks, group);

                    // 1c. Ủy quyền cho PowerUpManager:
                    // "Thông báo" cho Manager biết có gạch vỡ, Manager sẽ tự xử lý việc thả Power-Up
                    powerUpManager.spawnPowerUp(brokenBrick);
                    if (ballObj.getDirectionX() == 0 && ballObj.getDirectionY() == 0) {
                        ballIterator.remove();
                        group.getChildren().remove(ballObj.getBallImgView());
                    }
                }

                // === 2. VÒNG LẶP QUẢN LÝ POWER-UP (Đã được ủy thác) ===
                // Chỉ cần gọi 1 dòng. PowerUpManager sẽ tự lo mọi thứ:
                // (Cho item rơi, đếm ngược timer, kiểm tra va chạm paddle, kích hoạt hiệu ứng, ...)
                powerUpManager.update(paddle, balls, blocks);

                // === 3. XỬ LÝ GAME OVER ===
                if (balls.isEmpty()) {
                    System.out.println("GAME OVER");
                    this.stop(); // Dừng game
                }
            }
        };
        at.start();
    }
}