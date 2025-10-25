package org.example.gamearkanoid.controller;

import com.sun.tools.javac.Main;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import org.example.gamearkanoid.MainApp;
//import org.example.gamearkanoid.PauseMenu;
//import org.example.gamearkanoid.ScreenManager;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.GameMap;
import org.example.gamearkanoid.model.Paddle;
import javafx.scene.image.ImageView;
import org.example.gamearkanoid.menu.*;

public class GameController {
    private double originPositionX;
    private double newPositionX, mousePosX;
    private Scene scene;
    private Group group;

    private MainApp mainApp;
    private PauseMenu pauseMenu;
    private AnimationTimer gameTimer;
    private boolean isPaused = false;

    public GameController(Scene scene, Group group, MainApp mainApp, PauseMenu pauseMenu) {
        this.scene = scene;
        this.group = group;
        this.mainApp = mainApp;
        this.pauseMenu = pauseMenu;
        this.pauseMenu.setGameController(this);

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

    public void ballMovement(Ball ballObj, Paddle paddle, BlockBrick blocks) {

            gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                // update position of ball
                ballObj.updatePosition();
                ballObj.checkPaddle(paddle);
                ballObj.checkBorder(scene);

                // 1. checkBlock trả về true nếu hết gạch
                boolean levelCleared = ballObj.checkBlock(blocks, group);
                // 2. Nếu thắng
                if (levelCleared) {
                    stop();
                    scene.setOnKeyReleased(null);
                    mainApp.levelCompleted(); // Báo cho MainApp
                }
            }
        };

        gameTimer.start();
    }

    /**
     * Kích hoạt trình xử lý phím (listener) cho nút SPACE.
     */
    public void setupPauseHandler() {
        // Dùng setOnKeyReleased để tránh bị giữ phím
        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                    pauseGame();
                }
        });
    }

    /**
     * Dừng game timer và hiển thị menu pause.
     */
    public void pauseGame() {
        if (isPaused) return; // Tránh gọi nhiều lần
        isPaused = true;
        gameTimer.stop(); // Dừng vòng lặp game
        pauseMenu.show(); // Hiển thị menu pause
    }

    /**
     * Ẩn menu pause và tiếp tục game timer.
     * Hàm này được gọi từ PauseMenu.
     */
    public void resumeGame() {
        if (!isPaused) return;
        isPaused = false;
        pauseMenu.hide();
        gameTimer.start();
        setupPauseHandler(); //Kích hoạt lại listener phím SPACE
    }
}
