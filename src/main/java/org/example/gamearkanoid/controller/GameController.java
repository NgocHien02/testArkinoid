package org.example.gamearkanoid.controller;

import com.sun.tools.javac.Main;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import kotlin.internal.PlatformDependent;
import org.example.gamearkanoid.MainApp;
import org.example.gamearkanoid.menu.PauseMenu;
import org.example.gamearkanoid.menu.ScreenManager;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.GameMap;
import org.example.gamearkanoid.model.Paddle;
import javafx.scene.image.ImageView;

public class GameController {

    private AnimationTimer gameLoop;
    private enum GameState{
        PLAYING,
        PAUSE
    }
    GameState currentSate;

    private Scene scene;
    private Group group;

    private MainApp mainApp;
    private PauseMenu pauseMenu;
    private AnimationTimer gameTimer;
    private boolean isPaused = false;



    public GameController(Scene scene, Group group) {
        this.scene = scene;
        this.group = group;

    }






    public void ballMovement() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
//                 update position of ball
                ball.update();
                // 1. checkBlock trả về true nếu hết gạch
                boolean levelCleared = block.isEmpty();
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

}
