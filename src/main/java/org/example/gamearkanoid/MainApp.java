package org.example.gamearkanoid;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.gamearkanoid.controller.GameController;
import org.example.gamearkanoid.model.*;

public class MainApp extends Application {
    private Scene scene;
    private GameMap gameMap = new GameMap("/maps/lv1.txt");
    private BlockBrick blockObject = new BlockBrick();
    private Ball ballObject;
    private Paddle paddleObject = new Paddle(400, 400);
    private GameController controller;


    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();

        scene = new Scene(root, 800, 800);

        blockObject.addBrick(gameMap.getLayout());
        for (Brick brick : blockObject.getBlock()) {
            root.getChildren().add(brick.getBrickImageView());
        }

        controller = new GameController(scene, root);

        ballObject = new Ball(scene.getWidth() / 2 + 50, scene.getHeight() - 150);
        paddleObject = new Paddle(scene.getWidth() / 2 , scene.getHeight() - 100);
        root.getChildren().addAll(ballObject.getBallImgView(), paddleObject.getPaddleImgView());
        controller.dragPaddle(paddleObject);

        controller.ballMovement(ballObject, paddleObject, blockObject);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
