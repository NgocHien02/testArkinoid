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
import org.example.gamearkanoid.controller.GameController;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.Brick;
import org.example.gamearkanoid.model.Enemy;
import org.example.gamearkanoid.model.Paddle;
import org.example.gamearkanoid.view.BallView;
import org.example.gamearkanoid.view.BaseView;
import org.example.gamearkanoid.view.EnemyView;
import org.example.gamearkanoid.view.PaddleView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    private long lastFrameTime; // Thời điểm của khung hình trước

    @Override
    public void start(Stage stage) throws IOException {

        lastFrameTime = System.nanoTime();

        Group root = new Group();

        List<Brick> list = new ArrayList<>();
        Brick brick = new Brick(100, 100, 2);
        list.add(brick);
        Paddle paddle = new Paddle(500, 500);
        PaddleView paddleView = new PaddleView(paddle);
        Ball ball = new Ball(200, 200);
        ball.setPaddle(paddle);
        ball.setTargetList(list);
        BallView ballView = new BallView(ball);

        Enemy enemy = new Enemy(0, 0, 50, 50, paddle);
        EnemyView eView = new EnemyView(enemy);

        root.getChildren().addAll(eView.getImageView(), ballView.getImageView(), paddleView.getImageView());
        Scene scene = new Scene(root, 800, 800);
        GameController controller = new GameController(scene, root);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {

                long currentFrameTime = System.nanoTime(); // Lấy thời điểm hiện tại
                long timeElapsed = currentFrameTime - lastFrameTime; // Thời gian trôi qua (nano giây)

                // Chuyển đổi nano giây sang GIÂY (đây chính là delta)
                double delta = timeElapsed / 1_000_000_000.0;

                lastFrameTime = currentFrameTime;
                enemy.update();
                eView.update(delta);

                ball.update();
                paddle.update();
                paddleView.update();
                controller.dragPaddle(paddle);
            }
        };

        stage.setScene(scene);
        timer.start();

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
