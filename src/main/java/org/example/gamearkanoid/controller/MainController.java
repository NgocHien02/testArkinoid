package org.example.gamearkanoid.controller;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.example.gamearkanoid.HelloApplication;
import org.example.gamearkanoid.model.*;
import org.example.gamearkanoid.view.BallView;
import org.example.gamearkanoid.view.BlockView;
import org.example.gamearkanoid.view.EnemyView;
import org.example.gamearkanoid.view.PaddleView;

import java.util.HashSet;
import java.util.Set;

public class MainController {

    @FXML
    private AnchorPane gameAnchor;

    @FXML
    private Pane gamePane;

    @FXML
    private ImageView heartImg;

    @FXML
    private Label scoreText;

    private long lastUpdate = 0;
    private Paddle paddle;
    private PaddleView paddleView;
    private Ball ball;
    private BallView ballView;
    private BlockBrick block;
    private BlockView blockView;
    private AnimationTimer animationTimer;
    private Scene scene;
    private GameMap gameMap;
    private Group root;

    private Set<KeyCode> activeKeys;
    private double originPositionX;
    private double newPositionX, mousePosX;

    public MainController(Scene scene, Group root) {
        this.scene = scene;
        this.root = root;
    }


    @FXML
    public void initialize() {
        Paddle paddle = new Paddle(350, 700);
        Enemy enemy1 = new Enemy(0, 0, 50, 50, paddle);
        EnemyView enemyView = new EnemyView(enemy1);
        gamePane.getChildren().addAll(enemyView.getImageView(), paddle.getPaddleImgView());
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double delta = (now - lastUpdate) / 1_000_000_000.0;
                enemy1.update();
                enemyView.update(delta);
                paddle.update();

                lastUpdate = now;
            }
        };
        timer.start();

    }


    public void initGame() {
        clearGameScreen();
        // load map
        String mapPath = "/maps/lv" + levelNumber + ".txt";
        System.out.println(mapPath);
        gameMap = new GameMap(mapPath);

        // create ball, paddle, block of brick and add them to scene
        block = new BlockBrick();
        block.addBrick(gameMap.getLayout());
        blockView = new BlockView(block);
        ball = new Ball(scene.getWidth() / 2 + 50, scene.getHeight() - 150);
        ballView = new BallView(ball);
        paddle = new Paddle(scene.getWidth() / 2 , scene.getHeight() - 100);
        paddleView = new PaddleView(paddle);

        root.getChildren().addAll(blockView.getViewList());
        root.getChildren().addAll(ballView.getImageView(), paddleView.getImageView());

        activeKeys = new HashSet<>();
        setupInputHandlers();
    }

    public void runGame() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                // calculate delta

                // update game
                updateGame();
            }
        }
    }

    public void updateGame() {
        ball.update();
        ballView.update();
        paddle.update();
        paddleView.update();

    }


    public void clearGameScreen() {
        if (block != null) {
            for (Brick brick : block.getBlock()) {
                // @ Nhan fix
                root.getChildren().remove(brick.getBrickImageView());
            }
            block = null;
        }
        if (ball != null) {
            // @ Nhan fix
            root.getChildren().remove(ball.getBallImgView());

            ball = null;
        }
        if (paddle!= null) {
            root.getChildren().remove(paddle.getPaddleImgView());
            paddle= null;
        }
    }

    public void dragPaddle() {
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

    /**
     * Kích hoạt trình xử lý phím (listener) cho nút SPACE.
     */
    public void setupPauseHandler () {
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
    public void pauseGame () {
        if (isPaused) return; // Tránh gọi nhiều lần
        isPaused = true;
        gameTimer.stop(); // Dừng vòng lặp game
        pauseMenu.show(); // Hiển thị menu pause
    }


    /**
     * Ẩn menu pause và tiếp tục game timer.
     * Hàm này được gọi từ PauseMenu.
     */
    public void resumeGame () {
        if (!isPaused) return;
        isPaused = false;
        pauseMenu.hide();
        gameTimer.start();
        setupPauseHandler(); //Kích hoạt lại listener phím SPACE
    }


    public void setupInputHandlers() {
        scene.setOnKeyPressed(keyEvent -> {activeKeys.add(keyEvent.getCode());});
        scene.setOnKeyReleased(keyEvent -> {activeKeys.remove(keyEvent.getCode());});
    }


}
