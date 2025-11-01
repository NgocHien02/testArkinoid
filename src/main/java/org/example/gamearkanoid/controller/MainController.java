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

import java.util.*;

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
    private List<Ball> ballList;
    private List<BallView> ballViewList;
    private AnimationTimer animationTimer;
//    private Scene scene;
    private GameMap gameMap;
//    private Pane pane;

    private Set<KeyCode> activeKeys;
    private double paddleX;
    private double newPositionX, mousePosX;

    // Đối tượng quản lý chuyên biệt cho Power-Up
    private PowerUpManager powerUpManager;
    private double originPositionX;


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
        ballList = new ArrayList<>();
        ballViewList = new ArrayList<>();
        ballViewList.add(ballView);
        ballList.add(ball);
        this.powerUpManager = new PowerUpManager(gamePane);



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
        ballUpdate();
        paddle.update();
        paddleView.update();
        block.update();
        blockView.update(gamePane);
    }

    public void ballUpdate() {
        List<Ball> deadBall = new ArrayList<>();
        List<BallView> deadview = new ArrayList<>();

        Iterator<BallView> ballIterator = ballViewList.iterator();
        while (ballIterator.hasNext()) {
            BallView ballObj = ballIterator.next();
            if (!ballObj.getModel().isAlive()) {
                deadBall.add(ballObj.getModel());
                deadview.add(ballObj);
                continue;
            }
            // 1a. Cập nhật vị trí và va chạm
            ballObj.getModel().update();
            ballObj.update();
            // 1b. Va chạm gạch
            Brick brokenBrick = ballObj.getModel().getBrick();
            // 1c. Ủy quyền cho PowerUpManager:
            // "Thông báo" cho Manager biết có gạch vỡ, Manager sẽ tự xử lý việc thả Power-Up
            powerUpManager.spawnPowerUp(brokenBrick);
            if (ballObj.getModel().getDirX() == 0 && ballObj.getModel().getDirY() == 0) {
                ballObj.getModel().setAlive(false);
            }
        }

        ballList.removeAll(deadBall);
        ballViewList.remove(deadview);

        // === 2. VÒNG LẶP QUẢN LÝ POWER-UP (Đã được ủy thác) ===
        // Chỉ cần gọi 1 dòng. PowerUpManager sẽ tự lo mọi thứ:
        // (Cho item rơi, đếm ngược timer, kiểm tra va chạm paddle, kích hoạt hiệu ứng, ...)
        powerUpManager.update(paddleView, ballViewList, ballList, block);

        // === 3. XỬ LÝ GAME OVER ===
        if (ballList.isEmpty()) {
            System.out.println("GAME OVER");
            // game over
        }

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

        // bỏ điều khiển bằng chuột

//        EventHandler<MouseEvent> paddlePress = new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                mousePosX = mouseEvent.getSceneX();
//                originPositionX = ((Node) mouseEvent.getSource()).getLayoutX(); // Lấy vị trí layout thật
//                System.out.println(originPositionX);
//            }
//        };
//
//        EventHandler<MouseEvent> paddleDrag = new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                double amountMove = mouseEvent.getSceneX() - mousePosX;
//                newPositionX = originPositionX + amountMove;
//
//                Node paddleV = (Node) mouseEvent.getSource();
//                paddleV.setLayoutX(newPositionX); // Gắn vị trí layout thật thay vì translate
////                System.out.println(paddleV.getLayoutX());
//            }
//        };
//
//
//        paddleView.getImageView().setOnMousePressed(paddlePress);
//        paddleView.getImageView().setOnMouseDragged(paddleDrag);
    }

    public Pane getGamePane() {
        return gamePane;
    }
}
