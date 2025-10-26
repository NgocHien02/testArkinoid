package org.example.gamearkanoid.controller;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.example.gamearkanoid.HelloApplication;
import org.example.gamearkanoid.model.Enemy;
import org.example.gamearkanoid.model.Paddle;
import org.example.gamearkanoid.view.EnemyView;

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

}
