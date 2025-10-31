package org.example.gamearkanoid.controller;

import javafx.animation.AnimationTimer;
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
import org.example.gamearkanoid.model.*;
import org.example.gamearkanoid.view.BallView;
import org.example.gamearkanoid.view.BlockView;
import org.example.gamearkanoid.view.EnemyView;
import org.example.gamearkanoid.view.PaddleView;

import java.util.HashSet;
import java.util.Set;
import org.example.gamearkanoid.model.GameConfig.*;

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
//    private Scene scene;
    private GameMap gameMap;
//    private Pane pane;

    private Set<KeyCode> activeKeys;
    private double paddleX;
    private double newPositionX, mousePosX;


    @FXML
    public void initialize() {
        initGame();

    }


    public void initGame() {
        // load map
        String mapPath = "/maps/lv" + "1" + ".txt";
        System.out.println(mapPath);
        gameMap = new GameMap(mapPath);

        // create ball, paddle, block of brick and add them to scene
        block = new BlockBrick();
        block.addBrick(gameMap.getLayout());
        blockView = new BlockView(block);
        ball = new Ball( GameConfig.SCREEN_WIDTH/ 2 + 50, GameConfig.SCREEN_WIDTH - 150);
        ballView = new BallView(ball);
        paddle = new Paddle(GameConfig.SCREEN_WIDTH/ 2 , GameConfig.SCREEN_HEIGHT - 100);
        paddleView = new PaddleView(paddle);
        ball.setPaddle(paddle);
        ball.setBlockBrick(block);
        gamePane.getChildren().addAll(ballView.getImageView(), paddleView.getImageView());
        gamePane.getChildren().addAll(blockView.getViewList());


        activeKeys = new HashSet<>();
//        setupInputHandlers();
    }

    public void runGame() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // calculate delta
                double delta = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;
                // update game
                updateGame();
            }
        };
        animationTimer.start();
    }

    public void updateGame() {
        handleInput();
        ball.update();
        ballView.update();
        paddle.update();
        paddleView.update();
    }

    private void handleInput() {
        if (activeKeys.contains(KeyCode.LEFT)) {
            paddle.goLeft();
        }else if (activeKeys.contains(KeyCode.RIGHT)) {
            paddle.goRight();
        }
        else {
            paddle.idle();
        }
        if (activeKeys.contains(KeyCode.SPACE)) {
            pauseGame();
        }
    }

    /**
     * Dừng game timer và hiển thị menu pause.
     */
    public void pauseGame () {
        animationTimer.stop(); // Dừng vòng lặp game
    }

    /**
     * Ẩn menu pause và tiếp tục game timer.
     * Hàm này được gọi từ PauseMenu.
     */
    public void resumeGame () {
        animationTimer.start();
    }


    public void setupInputHandlers(Scene scene) {
        scene.setOnKeyPressed(keyEvent -> {activeKeys.add(keyEvent.getCode());});
        scene.setOnKeyReleased(keyEvent -> {activeKeys.remove(keyEvent.getCode());});
        EventHandler<MouseEvent> paddlePress = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mousePosX = mouseEvent.getSceneX();
                paddleX = ((Node) mouseEvent.getSource()).getTranslateX();
            }
        };

        EventHandler<MouseEvent> paddleDrag = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double amountMove = mouseEvent.getSceneX() - mousePosX;
                newPositionX = paddleX + amountMove;
                ((Node) mouseEvent.getSource()).setTranslateX(newPositionX);
            }
        };

        paddleView.getImageView().setOnMousePressed(paddlePress);
        paddleView.getImageView().setOnMouseDragged(paddleDrag);
    }

}
