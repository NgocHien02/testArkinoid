package org.example.gamearkanoid;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.example.gamearkanoid.controller.GameController;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp này chịu trách nhiệm hiển thị Menu Tạm Dừng (Pause Menu).
 */
public class PauseMenu {

    private Group root;
    private Scene scene;
    private MainApp mainApp;
    private GameController gameController; // Để gọi hàm resumeGame()

    private Rectangle overlay;
    private Text pauseTitle;

    private List<ImageView> buttons = new ArrayList<>();
    private List<ColorAdjust> effects = new ArrayList<>();

    // Đường dẫn ảnh
    private static final String RESUME_BUTTON_PATH = "/images/Select_Button/resume_button.png";
    private static final String REPLAY_BUTTON_PATH = "/images/Select_Button/replay_button.png";
    private static final String BACK_TO_MENU_BUTTON_PATH = "/images/Select_Button/back_to_menu_button.png";

    private int selectedOption = 0; // 0=Resume, 1=Replay, 2=Back
    private final int totalOptions = 3;

    // Thêm vào 1 group riêng để dễ dàng ẩn/hiện
    private Group pauseGroup = new Group();

    public PauseMenu(Group root, Scene scene, MainApp mainApp) {
        this.root = root;
        this.scene = scene;
        this.mainApp = mainApp;

        // 1. Tạo lớp phủ (overlay) mờ
        overlay = new Rectangle(scene.getWidth(), scene.getHeight(), Color.BLACK);
        overlay.setOpacity(0.7); // Độ mờ 70%

        // 2. Tạo chữ "PAUSED"
        pauseTitle = new Text("GAME PAUSED");
        pauseTitle.setFont(Font.font("Impact", 80));
        pauseTitle.setFill(Color.WHITE);
        pauseTitle.setX((scene.getWidth() - pauseTitle.getLayoutBounds().getWidth()) / 2);
        pauseTitle.setY(200);

        // 3. Tạo các nút
        double startY = 350;
        double spacing = 90;
        addButton(RESUME_BUTTON_PATH, startY);
        addButton(REPLAY_BUTTON_PATH, startY + spacing);
        addButton(BACK_TO_MENU_BUTTON_PATH, startY + spacing * 2);

        pauseGroup.getChildren().addAll(overlay, pauseTitle);
        pauseGroup.getChildren().addAll(buttons);
    }

    // Hàm tạo nút
    private void addButton(String imagePath, double y) {
        try {
            Image img = new Image(getClass().getResourceAsStream(imagePath));
            ImageView view = new ImageView(img);
            ColorAdjust effect = new ColorAdjust();
            view.setEffect(effect);

            view.setFitWidth(200); // Kích thước nút
            view.setFitHeight(60);
            view.setX((scene.getWidth() - 200) / 2);
            view.setY(y);

            buttons.add(view);
            effects.add(effect);
        } catch (Exception e) {
            System.err.println("Error loading pasue image: " + imagePath);
        }
    }

    // MainApp gọi để liên kết PauseMenu với Controller
    public void setGameController(GameController controller) {
        this.gameController = controller;
    }

    /**
     * Hiển thị menu pause và kích hoạt điều khiển menu.
     */
    public void show() {
        if (!root.getChildren().contains(pauseGroup)) {
            root.getChildren().add(pauseGroup);
        }
        selectedOption = 0;
        updateSelectionVisuals();
        setupMenuControls();
        scene.setCursor(Cursor.DEFAULT);
    }

    /**
     * Ẩn menu pause và hủy điều khiển menu.
     */
    public void hide() {
        root.getChildren().remove(pauseGroup);
        scene.setOnKeyPressed(null); //  Trao trả quyền điều khiển phím
    }

    // Cài đặt điều khiển cho menu (UP, DOWN, ENTER)
    private void setupMenuControls() {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                case W:
                    selectedOption = (selectedOption - 1 + totalOptions) % totalOptions;
                    updateSelectionVisuals();
                    break;
                case DOWN:
                case S:
                    selectedOption = (selectedOption + 1) % totalOptions;
                    updateSelectionVisuals();
                    break;
                case ENTER:
                    executeSelection();
                    break;
            }
        });

        // Thêm điều khiển chuột
        for (int i = 0; i < buttons.size(); i++) {
            ImageView button = buttons.get(i);
            final int index = i;
            button.setOnMouseEntered(e -> {
                selectedOption = index;
                updateSelectionVisuals();
                scene.setCursor(Cursor.HAND);
            });
            button.setOnMouseExited(e -> scene.setCursor(Cursor.DEFAULT));
            button.setOnMouseClicked(e -> executeSelection());
        }
    }

    private void updateSelectionVisuals() {
        for (ColorAdjust effect : effects) {
            effect.setBrightness(0.0);
        }
        if (selectedOption >= 0 && selectedOption < effects.size()) {
            effects.get(selectedOption).setBrightness(0.3);
        }
    }

    private void executeSelection() {
        if (gameController == null) return;

        switch (selectedOption) {
            case 0: // Resume
                gameController.resumeGame();
                break;
            case 1: // Replay
                hide();
                mainApp.startGame(mainApp.getCurrentLevel()); // Bắt đầu lại màn
                break;
            case 2: // Back to Menu
                hide();
                mainApp.showMainMenu(); // Về menu chính
                break;
        }
    }
}
