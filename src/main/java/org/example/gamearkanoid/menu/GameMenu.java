package org.example.gamearkanoid.menu;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.example.gamearkanoid.MainApp;

/**
 * Chịu trách nhiệm cho Menu CHÍNH (Play / Exit).
 */
public class GameMenu {

    private Group root;
    private Scene scene;
    private Stage stage;
    private MainApp mainApp;

    // các setup nút
    private static final double BUTTON_WIDTH = 200;
    private static final double BUTTON_HEIGHT = 60;

    private static final String ICON_IMAGE_PATH = "/images/icon_game.png";
    private static final String PLAY_BUTTON_IMAGE_PATH = "/images/Select_Button/play_button.png";
    private static final String EXIT_BUTTON_IMAGE_PATH = "/images/Select_Button/exit_button.png";

    private ImageView iconView;
    private ImageView playView;
    private ImageView exitView;
    private ColorAdjust playEffect = new ColorAdjust();
    private ColorAdjust exitEffect = new ColorAdjust();

    private int selectedOption = 0; // 0 = Play, 1 = Exit

    public GameMenu(Group root, Scene scene, Stage stage, MainApp mainApp) {
        this.root = root;
        this.scene = scene;
        this.stage = stage;
        this.mainApp = mainApp;
    }

    /**
     * Hiển thị menu Play/Exit.
     */
    public void show() {
        try {
            // 1. Tải icon
            Image iconImage = new Image(getClass().getResourceAsStream(ICON_IMAGE_PATH));
            iconView = new ImageView(iconImage);
            iconView.setFitWidth(scene.getWidth());
            iconView.setFitHeight(scene.getHeight());

            // 2. Tải nút "Play"
            Image playImage = new Image(getClass().getResourceAsStream(PLAY_BUTTON_IMAGE_PATH));
            playView = new ImageView(playImage);
            playView.setFitHeight(BUTTON_HEIGHT);
            playView.setFitWidth(BUTTON_WIDTH);
            playView.setEffect(playEffect);
            playView.setX((scene.getWidth() - BUTTON_WIDTH) / 2);
            playView.setY((scene.getHeight() / 2) - BUTTON_HEIGHT + 50);

            // 3. Tải nút "Exit"
            Image exitImage = new Image(getClass().getResourceAsStream(EXIT_BUTTON_IMAGE_PATH));
            exitView = new ImageView(exitImage);
            exitView.setFitWidth(BUTTON_WIDTH);
            exitView.setFitHeight(BUTTON_HEIGHT);
            exitView.setEffect(exitEffect);
            exitView.setX((scene.getWidth() - BUTTON_WIDTH) / 2);
            exitView.setY(scene.getHeight() / 2 + 70);

            // 4. Gắn điều khiển
            setupMouseControls();
            setupKeyboardControls();

            // 5. Cập nhật lựa chọn ban đầu
            selectedOption = 0;
            updateSelectionVisuals();

            // 6. Thêm vào root
            root.getChildren().addAll(iconView, playView, exitView);

        } catch (Exception e) {
            System.err.println("Error loading mainMenu ");
            e.printStackTrace();
        }
    }

    /**
     * Ẩn các thành phần của menu chính khỏi root.
     */
    private void hide() {
        if (iconView != null) {
            root.getChildren().remove(iconView);
        }
        if (playView != null) {
            root.getChildren().remove(playView);
        }
        if (exitView != null) {
            root.getChildren().remove(exitView);
        }
        scene.setOnKeyPressed(null); // Xóa listener
        scene.setCursor(Cursor.DEFAULT);
    }

    /**
     * Điều khiển lựa chọn bằng bàn phím.
     */
    private void setupKeyboardControls() {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                case W:
                    selectedOption = (selectedOption == 0) ? 1 : 0;
                    updateSelectionVisuals();
                    break;
                case DOWN:
                case S:
                    selectedOption = (selectedOption == 0) ? 1 : 0;
                    updateSelectionVisuals();
                    break;
                case ENTER:
                    executeSelection();
                    break;
            }
        });
    }

    /**
     * Điều khiển lựa chọn bằng chuột.
     */
    private void setupMouseControls() {
        playView.setOnMouseEntered(e -> {
            selectedOption = 0;
            updateSelectionVisuals();
        });
        playView.setOnMouseClicked(e -> executeSelection());
        playView.setOnMouseMoved(e -> scene.setCursor(Cursor.HAND));

        exitView.setOnMouseEntered(e -> {
            selectedOption = 1;
            updateSelectionVisuals();
        });
        exitView.setOnMouseClicked(e -> executeSelection());
        exitView.setOnMouseMoved(e -> scene.setCursor(Cursor.HAND));
    }

    private void updateSelectionVisuals() {
        playEffect.setBrightness(selectedOption == 0 ? 0.3 : 0.0);
        exitEffect.setBrightness(selectedOption == 1 ? 0.3 : 0.0);
    }

    private void executeSelection() {
        hide();
        if (selectedOption == 0) {
            mainApp.showLevelSelect();
        } else {
            stage.close();
        }
    }
}
