package org.example.gamearkanoid.menu;

import javafx.animation.PauseTransition;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import org.example.gamearkanoid.MainApp;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.Paddle;

/**
 * Lớp này quản lý các màn hình chuyển tiếp như "Level Complete" và "You Win".
 */
public class ScreenManager {

    private MainApp mainApp;
    private Group root;
    private Scene scene;

    // Kích thước (lấy từ scene)
    private double sceneWidth;
    private double sceneHeight;

    // Đường dẫn ảnh
    private static final String ICON_IMAGE_PATH = "/images/icon_game.png";
    private static final String LEVEL_COMPLETE_IMAGE_PATH = "/images/level_complete.png";
    private static final String YOU_WIN_IMAGE_PATH = "/images/you_win_title.png";
    private static final String BACK_BUTTON_PATH = "/images/Select_Button/back_button.png";

    private static final double IMAGE_WIDTH = 600;
    private static final double IMAGE_HEIGHT = 300;

    private ImageView winTitleView;
    private ImageView backView;
    private ImageView iconView;

    public ScreenManager(MainApp mainApp, Group root, Scene scene) {
        this.mainApp = mainApp;
        this.root = root;
        this.scene = scene;
        this.sceneWidth = scene.getWidth();
        this.sceneHeight = scene.getHeight();
    }

    /**
     * Hiển thị "Level Complete".
     * @param ballObject Bóng để xóa khỏi màn hình
     * @param paddleObject Paddle để xóa khỏi màn hình
     */
    public void showLevelComplete(Ball ballObject, Paddle paddleObject) {
        // 1. Dọn dẹp bóng và paddle khỏi màn hình
        if (ballObject != null) root.getChildren().remove(ballObject.getBallImgView());
        if (paddleObject != null) root.getChildren().remove(paddleObject.getPaddleImgView());

        // 2. Hiển thị ảnh "Level Complete"
        ImageView levelCompleteView = null;
        try {
            Image img = new Image(getClass().getResourceAsStream(LEVEL_COMPLETE_IMAGE_PATH));
            levelCompleteView = new ImageView(img);
            levelCompleteView.setFitHeight(IMAGE_HEIGHT);
            levelCompleteView.setFitWidth(IMAGE_WIDTH);
            levelCompleteView.setX((sceneWidth - IMAGE_WIDTH) / 2);
            levelCompleteView.setY((sceneHeight - IMAGE_HEIGHT) / 2 - 100);
            root.getChildren().add(levelCompleteView);
        } catch (Exception e) {
            System.err.println("Error loading image Level Complete: " + LEVEL_COMPLETE_IMAGE_PATH);
        }

        // 3. Tạo độ trễ
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        final ImageView finalLevelCompleteView = levelCompleteView;

        pause.setOnFinished(event -> {
            if (finalLevelCompleteView != null) {
                root.getChildren().remove(finalLevelCompleteView);
            }
            mainApp.processNextLevel();
        });
        pause.play();
    }

    /**
     * Hiển thị màn hình chiến thắng cuối cùng khi hoàn thành tất cả các màn.
     */
    public void showWinScreen() {
        mainApp.clearGameScreen();

        // Tải ảnh icon
        try {
            Image iconImage = new Image(getClass().getResourceAsStream(ICON_IMAGE_PATH));
            iconView = new ImageView(iconImage);
            iconView.setFitWidth(sceneWidth);
            iconView.setFitHeight(sceneHeight);
            root.getChildren().add(iconView);
        } catch (Exception e) {
            System.err.println("Error loading image 'Win'");
        }

        // Tải ảnh win
        try {
            Image titleImg = new Image(getClass().getResourceAsStream(YOU_WIN_IMAGE_PATH));
            winTitleView = new ImageView(titleImg);
            winTitleView.setFitHeight(IMAGE_HEIGHT);
            winTitleView.setFitWidth(IMAGE_WIDTH);
            winTitleView.setX((sceneWidth - IMAGE_WIDTH) / 2);
            winTitleView.setY(200);
            root.getChildren().add(winTitleView);
        } catch (Exception e) {
            System.err.println("Error loading image 'You Win': " + YOU_WIN_IMAGE_PATH);
        }

        // Thêm nút back
        try {
            Image backImg = new Image(getClass().getResourceAsStream(BACK_BUTTON_PATH));
            backView = new ImageView(backImg);
            backView.setFitWidth(200);
            backView.setFitHeight(60);
            // Căn giữa
            backView.setX((sceneWidth - 200) / 2);
            backView.setY(600);

            ColorAdjust effect = new ColorAdjust();
            backView.setEffect(effect);

            // Thêm hiệu ứng và hành động cho nút
            backView.setOnMouseEntered(e -> {
                effect.setBrightness(0.3);
                scene.setCursor(Cursor.HAND);
            });
            backView.setOnMouseExited(e -> {
                effect.setBrightness(0.0);
                scene.setCursor(Cursor.DEFAULT);
            });
            backView.setOnMouseClicked(e -> {
                scene.setCursor(Cursor.DEFAULT);
                mainApp.showMainMenu(); // Quay về menu chính
            });
            backView.setOnKeyReleased(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    mainApp.showMainMenu(); // Quay về menu chính
                }
            });

            root.getChildren().add(backView);
        } catch (Exception e) {
            System.err.println("Error loading image 'Back Button': " + BACK_BUTTON_PATH);
        }
    }

    public void clearWinScreen() {
        if (iconView != null) {
            root.getChildren().remove(iconView);
            iconView = null;
        }
        if (winTitleView != null) {
            root.getChildren().remove(winTitleView);
            winTitleView = null;
        }
        if (backView != null) {
            root.getChildren().remove(backView);
            backView = null;
        }
    }
}
