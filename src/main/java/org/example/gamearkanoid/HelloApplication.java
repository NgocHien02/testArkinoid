package org.example.gamearkanoid;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.gamearkanoid.model.Enemy;
import org.example.gamearkanoid.model.Paddle;
import org.example.gamearkanoid.view.EnemyView;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Paddle paddle = new Paddle(350, 750);
        Enemy enemy1 = new Enemy(0, 0,100, 100, paddle);
        EnemyView enemyView = new EnemyView("/images/paddle.png", enemy1);

        Button button = new Button("wander");
        button.setLayoutX(300);
        button.setLayoutY(300);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                button.setText("wandering");
            }
        });


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                enemy1.update();
                enemyView.showWander();
//                System.out.println(paddle.getX());
//                System.out.println(paddle.getY());
            }
        };
        Group root = new Group();
        root.getChildren().addAll(paddle.getPaddleImgView(), enemyView.getImageView(), button);
        Scene scene = new Scene(root, 800, 800);

        timer.start();
        stage.setScene(scene);
        stage.show();
    }
}
