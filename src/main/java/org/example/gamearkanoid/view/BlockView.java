package org.example.gamearkanoid.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.Brick;

import java.util.*;

public class BlockView {
    private List<Brick> block;
    private BlockBrick blockBrick;
    private static Map<Integer, Image> brickImages = new HashMap<>();;
    private List<ImageView> viewList;


    public BlockView(BlockBrick blockBrick) {
        this.blockBrick = blockBrick;
        block = this.blockBrick.getBlock();
        viewList = new ArrayList<>();
        // cải tiến để chỉ phải gọi một lần trong cả game
        loadBrickImages();
        loadViewList();
    }

    private void loadViewList() {
        for (Brick brick: block) {
            BrickView brickView = new BrickView(brick, getImageForType(brick.getType()));
            viewList.add(brickView.getImageView());
        }
    }

    public static Image getImageForType(int type) {
        return brickImages.get(type);
    }

    public void loadBrickImages() {
        try {
            // --- GẠCH THƯỜNG (THEO MAP) ---
            // Gạch 1 máu
            Image brick1 = loadImage("/images/Brick/brick_1_health.png", Brick.WIDTH, Brick.HEIGHT);
            brickImages.put(1, brick1);

            // Gạch 2 máu
            Image brick2 = loadImage("/images/Brick/brick_2_health.png", Brick.WIDTH, Brick.HEIGHT);
            brickImages.put(2, brick2);

//            // Gạch 3 máu
//            Image brick3 = loadImage("/images/brick_3.png"), Brick.WIDTH, Brick.HEIGHT);
//            brickImages.put(3, brick3);

            // Gạch không thể phá hủy
            Image brick9 = loadImage("/images/Brick/brick_indestructible.png", Brick.WIDTH, Brick.HEIGHT);
            brickImages.put(9, brick9);

            // --- CÁC TRẠNG THÁI NỨT --- (số máu ban đầu - 0 - số máu còn lại)
            // Gạch 2 máu bị nứt (còn 1 máu)
            Image brick201 = loadImage("/images/Brick/brick_2_health_cracked.png", Brick.WIDTH, Brick.HEIGHT);
            brickImages.put(201, brick201);

//            // Gạch 3 máu bị nứt 1 (còn 2 máu)
//            Image brick302 = loadImage("/images/brick_3_cracked_1.png"), Brick.WIDTH, Brick.HEIGHT);
//            brickImages.put(302, brick302);
//
//            // Gạch 3 máu bị nứt 2 (còn 1 máu)
//            Image brick301 = loadImage("/images/brick_3_cracked_2.png"), Brick.WIDTH, Brick.HEIGHT);
//            brickImages.put(301, brick301);

        } catch (Exception e) {
            System.err.println("Error loading brick images:");
            e.printStackTrace();
        }
    }

    protected Image loadImage(String path, double width, double height) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)), width, height,true, false);
    }

    public List<ImageView> getViewList() {
        return viewList;
    }
}
