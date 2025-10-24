package org.example.gamearkanoid;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;
import org.example.gamearkanoid.controller.GameController;
import org.example.gamearkanoid.model.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class MainApp extends Application {
    private Scene scene;
    private Group root;
    private Group backgroundGroup;
    private ImageView bgView1;
    private ImageView bgView2;
    private AnimationTimer backgroundTimer;
    private double scrollSpeed = 0.5; // Tốc độ cuộn
    private GameController controller;

    // Khai báo các đối tượng menu
    private GameMenu gameMenu;
    private LevelSelectMenu levelSelectMenu;
    private ScreenManager screenManager;
    private PauseMenu pauseMenu;

    // Các đối tượng game (sẽ được tạo trong startGame)
    private GameMap gameMap;
    private BlockBrick blockObject;
    private Ball ballObject;
    private Paddle paddleObject;

    private static final double SCENE_WIDTH = 800;
    private static final double SCENE_HEIGHT = 800;

    private int currentLevel; // Màn đang chơi
    private int highestLevelUnlocked = 1; // Màn cao nhất đã mở
    private static final int TOTAL_LEVELS = 5; // Tổng số màn

    @Override
    public void start(Stage stage) throws Exception {
        root = new Group(); // root chính
        backgroundGroup = new Group(); // Group cho nền

        // Thêm theo thứ tự: nền TRƯỚC, nội dung SAU
        root.getChildren().addAll(backgroundGroup);
        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        // --- BẮT ĐẦU: THÊM NỀN CHUYỂN ĐỘNG VÀO backgroundGroup ---
        try {
            // Đảm bảo bạn có ảnh tại đường dẫn này
            String bgPath = "/images/game_background.png";
            Image bgImage = new Image(getClass().getResourceAsStream(bgPath));

            bgView1 = new ImageView(bgImage);
            bgView1.setFitWidth(SCENE_WIDTH);
            bgView1.setFitHeight(SCENE_HEIGHT);
            bgView1.setX(0);
            bgView1.setY(0); // Ảnh 1 bắt đầu ở (0, 0)
            bgView1.setTranslateY(0);

            bgView2 = new ImageView(bgImage);
            bgView2.setFitWidth(SCENE_WIDTH);
            bgView2.setFitHeight(SCENE_HEIGHT);
            bgView2.setX(0);
            bgView2.setY(0); // Ảnh 2 bắt đầu ngay phía trên Ảnh 1
            bgView2.setTranslateY(-SCENE_HEIGHT);

            // Thêm nền vào backgroundGroup (KHÔNG PHẢI root)
            backgroundGroup.getChildren().addAll(bgView1, bgView2);

        } catch (Exception e) {
            System.err.println("Không tải được ảnh nền game: " + e.getMessage());
        }
        // --- KẾT THÚC: THÊM NỀN CHUYỂN ĐỘNG ---


        // --- BẮT ĐẦU: VÒNG LẶP NỀN (BACKGROUND TIMER) ---
        // Timer này chạy riêng biệt và liên tục
        backgroundTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (bgView1 != null && bgView2 != null) {
                    double sceneHeight = scene.getHeight();

                    // Di chuyển cả hai ảnh xuống
                    bgView1.setTranslateY(bgView1.getTranslateY() + scrollSpeed);
                    bgView2.setTranslateY(bgView2.getTranslateY() + scrollSpeed);

                    // Nếu bg1 cuộn ra khỏi đáy màn hình
                    if (bgView1.getTranslateY() >= sceneHeight) {
                        // Đặt nó ngay trên bg2
                        bgView1.setTranslateY(bgView2.getTranslateY() - sceneHeight);
                    }

                    // Nếu bg2 cuộn ra khỏi đáy màn hình
                    if (bgView2.getTranslateY() >= sceneHeight) {
                        // Đặt nó ngay trên bg1
                        bgView2.setTranslateY(bgView1.getTranslateY() - sceneHeight);
                    }
                }
            }
        };
        backgroundTimer.start(); // <-- CHẠY NGAY LẬP TỨC
        // --- KẾT THÚC: VÒNG LẶP NỀN ---


        // Khởi tạo các menu
        gameMenu = new GameMenu(root, scene, stage, this);
        levelSelectMenu = new LevelSelectMenu(root, scene, stage, this);
        screenManager = new ScreenManager(this, root, scene);

        // PauseMenu
        pauseMenu = new PauseMenu(root, scene, this);

        // Hiển thị menu chính đầu tiên
        showMainMenu();

        stage.setScene(scene);
        stage.setTitle("Arkanoid Game");
        stage.show();
    }

    public void levelCompleted() {
        screenManager.showLevelComplete(ballObject, paddleObject);
    }

    public void processNextLevel() {
        int nextLevel = currentLevel + 1;

        // Mở khóa màn tiếp theo
        if (nextLevel > highestLevelUnlocked && nextLevel <= TOTAL_LEVELS) {
            highestLevelUnlocked = nextLevel;
        }

        // Chuyển sang màn tiếp theo hoặc màn "Win"
        if (nextLevel <= TOTAL_LEVELS) {
            startGame(nextLevel);
        } else {
            showWinScreen(); // <-- Gọi màn hình "Win"
        }
    }

    /**
     * Được gọi bởi LevelSelectMenu để bắt đầu màn chơi.
     * @param levelNumber Số thứ tự của level (1, 2, 3...)
     */
    public void startGame(int levelNumber) {
        // 1. Dọn dẹp màn hình (xóa menu)
        clearGameScreen();
        this.currentLevel = levelNumber; // <-- Lưu màn hiện tại

        controller = new GameController(scene, root, this, pauseMenu);

        // 2. Tải map dựa trên levelNumber
        String mapPath = "/maps/lv" + levelNumber + ".txt";
        System.out.println(mapPath);
        gameMap = new GameMap(mapPath);

        // 3. Tạo gạch (blockObject)
        blockObject = new BlockBrick();
        blockObject.addBrick(gameMap.getLayout());
        for (Brick brick : blockObject.getBlock()) {
            root.getChildren().add(brick.getBrickImageView());
        }

        // 4. Tạo bóng và paddle
        ballObject = new Ball(scene.getWidth() / 2 + 50, scene.getHeight() - 150);
        paddleObject = new Paddle(scene.getWidth() / 2 , scene.getHeight() - 100);
        root.getChildren().addAll(ballObject.getBallImgView(), paddleObject.getPaddleImgView());

        // 5. Kích hoạt điều khiển và chuyển động
        controller.dragPaddle(paddleObject);
        controller.ballMovement(ballObject, paddleObject, blockObject);
        controller.setupPauseHandler();
    }

    /**
     * Xóa các đối tượng game hiện tại khỏi root.
     */
    public void clearGameScreen() {
        if (blockObject != null) {
            for (Brick brick : blockObject.getBlock()) {
                root.getChildren().remove(brick.getBrickImageView());
            }
            blockObject = null;
        }
        if (ballObject != null) {
            root.getChildren().remove(ballObject.getBallImgView());
            ballObject = null;
        }
        if (paddleObject != null) {
            root.getChildren().remove(paddleObject.getPaddleImgView());
            paddleObject = null;
        }
    }

    /**
     * Được gọi bởi LevelSelectMenu (nút Back) để quay lại menu chính.
     */
    public void showMainMenu() {
        clearGameScreen();
        screenManager.clearWinScreen();
        gameMenu.show();
    }

    /**
     * Được gọi bởi GameMenu (nút Play) để hiển thị menu chọn level.
     */
    public void showLevelSelect() {
        levelSelectMenu.show();
    }

    public void showWinScreen() {
        screenManager.showWinScreen();
    }

    public int getHighestLevelUnlocked() {
        return highestLevelUnlocked;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
