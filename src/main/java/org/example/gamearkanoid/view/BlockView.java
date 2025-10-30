package org.example.gamearkanoid.view;

import javafx.scene.image.Image;
import org.example.gamearkanoid.model.Brick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockView {
    private List<Brick> block = new ArrayList<>();
    private static Map<Integer, Image> brickImages = new HashMap<>();

    public static Image getImageForType(int type) {
        return brickImages.get(type);
    }

    private void loadBrickImages() {
        try {
            // --- GẠCH THƯỜNG (THEO MAP) ---
            // Gạch 1 máu
            Image brick1 = new Image(getClass().getResourceAsStream("/images/Brick/brick_1_health.png"), Brick.DEFAULT_WIDTH_BRICK, Brick.DEFAULT_HEIGHT_BRICK, true, false);
            brickImages.put(1, brick1);

            // Gạch 2 máu
            Image brick2 = new Image(getClass().getResourceAsStream("/images/Brick/brick_2_health.png"), Brick.DEFAULT_WIDTH_BRICK, Brick.DEFAULT_HEIGHT_BRICK, true, false);
            brickImages.put(2, brick2);

//            // Gạch 3 máu
//            Image brick3 = new Image(getClass().getResourceAsStream("/images/brick_3.png"), Brick.DEFAULT_WIDTH_BRICK, Brick.DEFAULT_HEIGHT_BRICK, true, false);
//            brickImages.put(3, brick3);

            // Gạch không thể phá hủy
            Image brick9 = new Image(getClass().getResourceAsStream("/images/Brick/brick_indestructible.png"), Brick.DEFAULT_WIDTH_BRICK, Brick.DEFAULT_HEIGHT_BRICK, true, false);
            brickImages.put(9, brick9);

            // --- CÁC TRẠNG THÁI NỨT --- (số máu ban đầu - 0 - số máu còn lại)
            // Gạch 2 máu bị nứt (còn 1 máu)
            Image brick201 = new Image(getClass().getResourceAsStream("/images/Brick/brick_2_health_cracked.png"), Brick.DEFAULT_WIDTH_BRICK, Brick.DEFAULT_HEIGHT_BRICK, true, false);
            brickImages.put(201, brick201);

//            // Gạch 3 máu bị nứt 1 (còn 2 máu)
//            Image brick302 = new Image(getClass().getResourceAsStream("/images/brick_3_cracked_1.png"), Brick.DEFAULT_WIDTH_BRICK, Brick.DEFAULT_HEIGHT_BRICK, true, false);
//            brickImages.put(302, brick302);
//
//            // Gạch 3 máu bị nứt 2 (còn 1 máu)
//            Image brick301 = new Image(getClass().getResourceAsStream("/images/brick_3_cracked_2.png"), Brick.DEFAULT_WIDTH_BRICK, Brick.DEFAULT_HEIGHT_BRICK, true, false);
//            brickImages.put(301, brick301);

        } catch (Exception e) {
            System.err.println("Error loading brick images:");
            e.printStackTrace();
        }
    }

}
