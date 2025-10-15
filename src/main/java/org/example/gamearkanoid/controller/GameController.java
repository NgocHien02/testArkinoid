package org.example.gamearkanoid.controller;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.Paddle;

public class GameController {
    private double originPositionX;
    private double newPositionX, mousePosX;
    private Scene scene;
    private Group group;

    public GameController(Scene scene, Group group) {
        this.scene = scene;
        this.group = group;

    }

    public void dragPaddle(Paddle paddle) {
        EventHandler<MouseEvent> paddlePress = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mousePosX = mouseEvent.getSceneX();
                originPositionX = ( (Node) mouseEvent.getSource() ).getTranslateX();
            }
        };

        EventHandler<MouseEvent> paddleDrag = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double amountMove = mouseEvent.getSceneX() - mousePosX;
                newPositionX =originPositionX + amountMove;
                ( (Node) mouseEvent.getSource() ).setTranslateX(newPositionX);
            }
        };

        paddle.getPaddle().setOnMousePressed(paddlePress);
        paddle.getPaddle().setOnMouseDragged(paddleDrag);
    }

    public void ballMovement(Ball ballObj, Paddle paddle, BlockBrick blocks) {

        AnimationTimer at = new AnimationTimer() {
            @Override
            public void handle(long l) {
                // update position of ball
                ballObj.updatePosition();
                ballObj.checkPaddle(paddle);
                ballObj.checkBorder(scene);
                ballObj.checkBlock(blocks, group);
            }
        };

        at.start();

    }
}
