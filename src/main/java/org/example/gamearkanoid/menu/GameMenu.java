package org.example.gamearkanoid.menu;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.gamearkanoid.MainApp;

/**
 * Chịu trách nhiệm cho Menu CHÍNH (Play / Exit).
 */
public class GameMenu {

    private Pane pane;

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

    private Runnable onPlayRequest;
    private Runnable onExitRequest;

    public GameMenu(double screenWidth, double screenHeight) {
        pane = new Pane();
        pane.setPrefSize(screenWidth, screenHeight);
        load(screenWidth, screenHeight);
    }

    /**
     * Hiển thị menu Play/Exit.
     */
    public void load(double screenWidth, double screenHeight) {
        try {
            // 1. Tải icon
            Image iconImage = new Image(getClass().getResourceAsStream(ICON_IMAGE_PATH));
            iconView = new ImageView(iconImage);
            iconView.setFitWidth(screenWidth);
            iconView.setFitHeight(screenHeight);

            // 2. Tải nút "Play"
            Image playImage = new Image(getClass().getResourceAsStream(PLAY_BUTTON_IMAGE_PATH));
            playView = new ImageView(playImage);
            playView.setFitHeight(BUTTON_HEIGHT);
            playView.setFitWidth(BUTTON_WIDTH);
            playView.setEffect(playEffect);
            playView.setX((screenWidth - BUTTON_WIDTH) / 2);
            playView.setY((screenHeight / 2) - BUTTON_HEIGHT + 50);

            // 3. Tải nút "Exit"
            Image exitImage = new Image(getClass().getResourceAsStream(EXIT_BUTTON_IMAGE_PATH));
            exitView = new ImageView(exitImage);
            exitView.setFitWidth(BUTTON_WIDTH);
            exitView.setFitHeight(BUTTON_HEIGHT);
            exitView.setEffect(exitEffect);
            exitView.setX((screenWidth - BUTTON_WIDTH) / 2);
            exitView.setY(screenHeight / 2 + 70);

            // 4. Gắn điều khiển
            setupMouseControls();
//            setupKeyboardControls();
            // 5. Cập nhật lựa chọn ban đầu
            selectedOption = 0;
            updateSelectionVisuals();

            // 6. Thêm vào pane
            pane.getChildren().addAll(iconView, playView, exitView);

        } catch (Exception e) {
            System.err.println("Error loading mainMenu ");
            e.printStackTrace();
        }
    }

    public Pane getPane() {
        return pane;
    }



    // --- Các hàm Setter cho Callback ---
    public void setOnPlayRequest(Runnable onPlayRequest) {
        this.onPlayRequest = onPlayRequest;
    }

    public void setOnExitRequest(Runnable onExitRequest) {
        this.onExitRequest = onExitRequest;
    }

    /**
     * Điều khiển lựa chọn bằng bàn phím.
     */
    public void setupKeyboardControls(Scene scene) {
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
     * MainApp sẽ gọi hàm này để gỡ điều khiển bàn phím.
     */
    public void detachControls(Scene scene) {
        scene.setOnKeyPressed(null);
        scene.setCursor(Cursor.DEFAULT);
    }

    /**
     * Điều khiển lựa chọn bằng chuột.
     */
    public void setupMouseControls() {
        playView.setOnMouseEntered(e -> {
            selectedOption = 0;
            updateSelectionVisuals();
        });
        playView.setOnMouseClicked(e -> executeSelection());
        playView.setOnMouseMoved(e -> playView.getScene().setCursor(Cursor.HAND));

        exitView.setOnMouseEntered(e -> {
            selectedOption = 1;
            updateSelectionVisuals();
        });
        exitView.setOnMouseClicked(e -> executeSelection());
        exitView.setOnMouseMoved(e -> playView.getScene().setCursor(Cursor.HAND));
    }

    private void updateSelectionVisuals() {
        playEffect.setBrightness(selectedOption == 0 ? 0.3 : 0.0);
        exitEffect.setBrightness(selectedOption == 1 ? 0.3 : 0.0);
    }

    private void executeSelection() {
        if (selectedOption == 0) {
            if (onPlayRequest != null) {
                onPlayRequest.run();
            }
        } else {
            if (onExitRequest != null) {
                onExitRequest.run();
            }
        }
    }


    public void showMenu(Scene scene) {
        this.getPane().setVisible(true);
        this.getPane().toFront();
        this.setupKeyboardControls(scene);
    }

    public void hideMenu(Scene scene) {
        this.getPane().setVisible(false);
        this.detachControls(scene);
    }
}
