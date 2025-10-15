package org.example.gamearkanoid;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gamearkanoid.controller.GameController;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.Paddle;

public class MainApp extends Application {
    private Scene scene;
    private BlockBrick blockObject = new BlockBrick();
    private Ball ballObject;
    private Paddle paddleObject = new Paddle(400, 400);
    private GameController controller;


    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();

        blockObject.addBrick(4, 6);
        for (int i = 0; i < blockObject.getBlock().size(); i++) {
            root.getChildren().add(blockObject.getBlock().get(i).getBrick());
        }

        scene = new Scene(root, 800, 800);
        controller = new GameController(scene, root);

        ballObject = new Ball(scene.getWidth() / 2 + 50, scene.getHeight() - 150 , 10);
        paddleObject = new Paddle(scene.getWidth() / 2 , scene.getHeight() - 100);
        root.getChildren().addAll(ballObject.getBall(), paddleObject.getPaddle());
        controller.dragPaddle(paddleObject);

        controller.ballMovement(ballObject, paddleObject, blockObject);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
