package org.example.gamearkanoid.menu;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static org.example.gamearkanoid.MainApp.SCENE_HEIGHT;
import static org.example.gamearkanoid.MainApp.SCENE_WIDTH;

public class Background {
    private Group backgroundGroup;
    private ImageView bgView1;
    private ImageView bgView2;
    private AnimationTimer backgroundTimer;
    private double scrollSpeed = 0.5; // Tốc độ cuộn


    public void show() {
        backgroundGroup = new Group(); // Group cho nền

        // Thêm theo thứ tự: nền TRƯỚC, nội dung SAU
//        root.getChildren().addAll(backgroundGroup);
//        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

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
                    double sceneHeight = SCENE_HEIGHT;

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

    }
}
