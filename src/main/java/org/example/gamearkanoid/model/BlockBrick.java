package org.example.gamearkanoid.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlockBrick {
    private ArrayList<Brick> block = new ArrayList<>();
    private double dx = 10;
    private double dy = 10;

    private Map<Integer, Image> brickImages = new HashMap<>();

    public BlockBrick() {
        loadBrickImages();
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

    /**
     * Lấy hình ảnh theo key (loại gạch)
     */
    public Image getImageForType(int type) {
        return brickImages.get(type);
    }

    public void addBrick(int[][] layout) {
        double y = 0;
        for (int i = 0; i < layout.length; i++) {
            double x = 0;
            for (int j = 0; j < layout[i].length; j++) {
                int brickType = layout[i][j];

                if (brickType > 0) {
                    Image brickImage = brickImages.get(brickType);
                    if (brickImage != null) {
                        // Xác định máu dựa trên loại gạch
                        int health;
                        if (brickType == 9) {
                            health = -1; // -1 là bất tử
                        } else if (brickType >= 1 && brickType <= 3) {
                            health = brickType; // Gạch loại 1 có 1 máu, loại 2 có 2 máu,...
                        } else {
                            health = 1; // Mặc định là 1 máu nếu loại gạch không xác định
                        }

                        Brick newBrick = new Brick(x, y, brickImage, health);
                        newBrick.setBlockBrickManager(this);
                        block.add(newBrick);
                    } else {
                        System.err.println("No image found for brick type");
                    }
                }
                x = x + Brick.DEFAULT_WIDTH_BRICK + dx;
            }
            y = y + Brick.DEFAULT_HEIGHT_BRICK + dy;
        }
    }

    public ArrayList<Brick> getBlock() {
        return block;
    }

    public void setBlock(ArrayList<Brick> block) {
        this.block = block;
    }

    public boolean isEmpty() {
        return block.isEmpty();
    }


}
