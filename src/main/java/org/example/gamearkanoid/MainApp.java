package org.example.gamearkanoid;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import org.example.gamearkanoid.controller.MainController;
import org.example.gamearkanoid.menu.GameMenu;

import java.io.IOException;

public class MainApp extends Application {
    private Scene scene;
    private StackPane rootPane;
    private Stage mainStage;
    AnchorPane anchorPane;


    // Khai báo các đối tượng menu
    private GameMenu gameMenu;
//    private LevelSelectMenu levelSelectMenu;
//    private ScreenManager screenManager;
//    private PauseMenu pauseMenu;

    private MainController mainController;

    private enum GameState{
        PLAYING,
        MENU,
        PAUSE
    }
    private GameState currentState;

    public static final double SCENE_WIDTH = 800;
    public static final double SCENE_HEIGHT = 800;

    private int currentLevel; // Màn đang chơi
    private int highestLevelUnlocked = 1; // Màn cao nhất đã mở
    private static final int TOTAL_LEVELS = 5; // Tổng số màn

    @Override
    public void start(Stage stage) throws Exception {
//        root = new Group(); // root chính
//        // Khởi tạo các menu
//        gameMenu = new GameMenu(root, scene, stage, this);
//        levelSelectMenu = new LevelSelectMenu(root, scene, stage, this);
//        screenManager = new ScreenManager(this, root, scene);
//
//        // PauseMenu
//        pauseMenu = new PauseMenu(root, scene, this);

        // Hiển thị menu chính đầu tiên
//        showMainMenu();

        this.mainStage = stage;
        rootPane = new StackPane();
        scene = new Scene(rootPane, SCENE_WIDTH, SCENE_HEIGHT);
        initialMenu();
        stage.setScene(scene);
        stage.setTitle("Arkanoid Game");
        stage.show();
    }


    public void initialMenu() {
        gameMenu = new GameMenu(SCENE_WIDTH, SCENE_HEIGHT);
        gameMenu.setOnExitRequest(() ->{
            mainStage.close();
            });

        gameMenu.setOnPlayRequest(() ->{
            gameMenu.getPane().setVisible(false);
            gameMenu.detachControls(scene);
            try {
                initGame();
                mainController.setupInputHandlers(scene);
                currentState = GameState.PLAYING;
                mainController.runGame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        gameMenu.getPane().setVisible(false);
        rootPane.getChildren().add(gameMenu.getPane());
        gameMenu.showMenu(scene);
    }

    public void initGame() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/gamearkanoid/game-view.fxml"));

       anchorPane = loader.load();
        mainController = loader.getController();
        anchorPane.setVisible(true);
        anchorPane.toFront();
        rootPane.getChildren().add(anchorPane);
    }


//    public void levelCompleted() {
//        screenManager.showLevelComplete(ballObject, paddleObject);
//    }
//
//    public void processNextLevel() {
//        int nextLevel = currentLevel + 1;
//
//        // Mở khóa màn tiếp theo
//        if (nextLevel > highestLevelUnlocked && nextLevel <= TOTAL_LEVELS) {
//            highestLevelUnlocked = nextLevel;
//        }
//
//        // Chuyển sang màn tiếp theo hoặc màn "Win"
//        if (nextLevel <= TOTAL_LEVELS) {
//            startGame(nextLevel);
//        } else {
//            showWinScreen(); // <-- Gọi màn hình "Win"
//        }
//    }






//    /**
//     * Được gọi bởi LevelSelectMenu (nút Back) để quay lại menu chính.
//     */
//    public void showMainMenu() {
//        clearGameScreen();
//        screenManager.clearWinScreen();
//        gameMenu.show();
//    }
//
//    /**
//     * Được gọi bởi GameMenu (nút Play) để hiển thị menu chọn level.
//     */
//    public void showLevelSelect() {
//        levelSelectMenu.show();
//    }
//
//    public void showWinScreen() {
//        screenManager.showWinScreen();
//    }
//
//    public int getHighestLevelUnlocked() {
//        return highestLevelUnlocked;
//    }
//
//    public int getCurrentLevel() {
//        return currentLevel;
//    }

    public static void main(String[] args) {
        launch(args);
    }
}
