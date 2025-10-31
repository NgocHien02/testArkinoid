//package org.example.gamearkanoid.menu;
//
//import javafx.scene.Cursor;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.effect.ColorAdjust;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.KeyCode;
//import javafx.stage.Stage;
//import org.example.gamearkanoid.MainApp;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Lớp này chịu trách nhiệm cho màn hình CHỌN LEVEL (Level 1-5, Back).
// */
//public class LevelSelectMenu {
//
//    private Group root;
////    private Scene scene;
////    private MainApp mainApp;
//
//    // Kích thước cố định
//    private static final double TITLE_WIDTH = 500;
//    private static final double TITLE_HEIGHT = 200;
//    private static final double BUTTON_WIDTH = 200;
//    private static final double BUTTON_HEIGHT = 60;
//
//    // Đường dẫn ảnh
//    private static final String ICON_IMAGE_PATH = "/images/icon_game.png";
//    private static final String TITLE_IMAGE_PATH = "/images/Select_Button/select_level_title.png";
//
//    private static final String LVL1_BUTTON_PATH = "/images/Level_Button/level_1_button.png";
//
//    private static final String LVL2_BUTTON_PATH = "/images/Level_Button/level_2_button.png";
//    private static final String LVL2_LOCKED_PATH = "/images/Level_Button/level_2_locked.png";
//
//    private static final String LVL3_BUTTON_PATH = "/images/Level_Button/level_3_button.png";
//    private static final String LVL3_LOCKED_PATH = "/images/Level_Button/level_3_locked.png";
//
//    private static final String LVL4_BUTTON_PATH = "/images/Level_Button/level_4_button.png";
//    private static final String LVL4_LOCKED_PATH = "/images/Level_Button/level_4_locked.png";
//
//    private static final String LVL5_BUTTON_PATH = "/images/Level_Button/level_5_button.png";
//    private static final String LVL5_LOCKED_PATH = "/images/Level_Button/level_5_locked.png";
//
//    private static final String BACK_BUTTON_PATH = "/images/Select_Button/back_button.png";
//
//    // Danh sách các nút và hiệu ứng
//    private ImageView titleView;
//    private List<ImageView> buttons = new ArrayList<>();
//    private List<ColorAdjust> effects = new ArrayList<>();
//    private List<Boolean> isLocked = new ArrayList<>();
//
//    // 0=Lvl1, 1=Lvl2, 2=Lvl3, 3=Lvl4, 4=Lvl5, 5=Back
//    private int selectedOption = 0;
//    private final int totalOptions = 6; // 5 level + 1 nút Back
//
//    public LevelSelectMenu(Group root) {
//        this.root = root;
////        this.scene = scene;
////        this.mainApp = mainApp;
//    }
//
//    /**
//     * Hiển thị menu chọn level.
//     */
//    public void show() {
//        try {
//            // 1. Tải ảnh tiêu đề "SELECT LEVEL"
//            Image titleImage = new Image(getClass().getResourceAsStream(TITLE_IMAGE_PATH));
//            titleView = new ImageView(titleImage);
//            titleView.setFitWidth(TITLE_WIDTH);
//            titleView.setFitHeight(TITLE_HEIGHT);
//            titleView.setX((scene.getWidth() - TITLE_WIDTH) / 2);
//            titleView.setY(50);
//
//            root.getChildren().add(titleView);
//
//            // 2. Tải và sắp xếp các nút
//            // Vị trí Y bắt đầu của nút đầu tiên
//            double currentY = 250;
//            // Khoảng cách giữa các nút
//            double spacing = BUTTON_HEIGHT + 20;
//
//            int highestLevel = mainApp.getHighestLevelUnlocked();
//            buttons.clear();
//            effects.clear();
//            isLocked.clear();
//            // Quyết định trạng thái (khóa/mở) cho từng màn
//            boolean lvl2Locked = (2 > highestLevel);
//            boolean lvl3Locked = (3 > highestLevel);
//            boolean lvl4Locked = (4 > highestLevel);
//            boolean lvl5Locked = (5 > highestLevel);
//
//            // Thêm nút với logic
//            // Level 1 (Luôn mở)
//            addButton(LVL1_BUTTON_PATH, currentY, false);
//
//            // Level 2
//            addButton(lvl2Locked ? LVL2_LOCKED_PATH : LVL2_BUTTON_PATH, currentY + spacing, lvl2Locked);
//
//            // Level 3
//            addButton(lvl3Locked ? LVL3_LOCKED_PATH : LVL3_BUTTON_PATH, currentY + spacing * 2, lvl3Locked);
//
//            // Level 4
//            addButton(lvl4Locked ? LVL4_LOCKED_PATH : LVL4_BUTTON_PATH, currentY + spacing * 3, lvl4Locked);
//
//            // Level 5
//            addButton(lvl5Locked ? LVL5_LOCKED_PATH : LVL5_BUTTON_PATH, currentY + spacing * 4, lvl5Locked);
//
//            // Nút Back (Luôn mở)
//            addButton(BACK_BUTTON_PATH, currentY + spacing * 6, false);
//
//            // 3. Gắn điều khiển
//            setupMouseControls();
//            setupKeyboardControls();
//
//            // 4. Cập nhật lựa chọn ban đầu
//            selectedOption = 0;
//            updateSelectionVisuals();
//
//        } catch (Exception e) {
//            System.err.println("Error loading levelSelectMenu image.");
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Hàm trợ giúp để thêm một nút vào menu
//     */
//    private void addButton(String imagePath, double y, boolean locked) {
//        Image img = new Image(getClass().getResourceAsStream(imagePath));
//        ImageView view = new ImageView(img);
//        ColorAdjust effect = new ColorAdjust();
//        view.setFitWidth(BUTTON_WIDTH);
//        view.setFitHeight(BUTTON_HEIGHT);
//
//        view.setEffect(effect);
//        view.setX((scene.getWidth() - BUTTON_WIDTH) / 2);
//        view.setY(y);
//
//        buttons.add(view);
//        effects.add(effect);
//        isLocked.add(locked);
//        root.getChildren().add(view);
//    }
//
//    private void hide() {
//        // Xóa tiêu đề
//        if (titleView != null) {
//            root.getChildren().remove(titleView);
//        }
//        // Xóa tất cả các nút
//        for (ImageView button : buttons) {
//            root.getChildren().remove(button);
//        }
//
//        // Xóa listener
//        scene.setOnKeyPressed(null);
//        scene.setCursor(Cursor.DEFAULT);
//
//        // Dọn dẹp danh sách
//        buttons.clear();
//        effects.clear();
//        isLocked.clear();
//    }
//
//    private void setupKeyboardControls(Scene scene) {
//        scene.setOnKeyPressed(event -> {
//            switch (event.getCode()) {
//                case UP:
//                case W:
//                    // (chỉ số hiện tại - 1 + tổng số) % tổng số
//                    selectedOption = (selectedOption - 1 + totalOptions) % totalOptions;
//                    updateSelectionVisuals();
//                    break;
//                case DOWN:
//                case S:
//                    // (chỉ số hiện tại + 1) % tổng số
//                    selectedOption = (selectedOption + 1) % totalOptions;
//                    updateSelectionVisuals();
//                    break;
//                case ENTER:
//                    executeSelection();
//                    break;
//            }
//        });
//    }
//
//    private void setupMouseControls() {
//        for (int i = 0; i < buttons.size(); i++) {
//            ImageView button = buttons.get(i);
//            final int index = i;
//
//            button.setOnMouseEntered(e -> {
//                selectedOption = index;
//                updateSelectionVisuals();
//            });
//            button.setOnMouseClicked(e -> executeSelection());
//            button.setOnMouseMoved(e -> scene.setCursor(Cursor.HAND));
//        }
//    }
//
//    private void updateSelectionVisuals() {
//        // Đặt tất cả về bình thường
//        for (ColorAdjust effect : effects) {
//            effect.setBrightness(0.0);
//        }
//        // Làm sáng cái được chọn
//        effects.get(selectedOption).setBrightness(0.3);
//    }
//
//    private void executeSelection() {
//        // Nếu chọn phải nút bị khóa, không làm gì cả
//        if (isLocked.get(selectedOption)) {
//            return;
//        }
//
//        hide();
//
//        // Xử lý lựa chọn
//        switch (selectedOption) {
//            case 0: // Level 1
//                mainApp.startGame(1);
//                break;
//            case 1: // Level 2
//                mainApp.startGame(2);
//                break;
//            case 2: // Level 3
//                mainApp.startGame(3);
//                break;
//            case 3: // Level 4
//                mainApp.startGame(4);
//                break;
//            case 4: // Level 5
//                mainApp.startGame(5);
//                break;
//            case 5: // Nút Back
//                mainApp.showMainMenu(); // Quay lại menu chính
//                break;
//        }
//    }
//}
