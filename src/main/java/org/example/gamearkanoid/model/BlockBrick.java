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
            Image brick1 = new Image(getClass().getResourceAsStream("/images/123.png"), Brick.DEFAULT_HEIGHT_BRICK, Brick.DEFAULT_WIDTH_BRICK, true, false);
            brickImages.put(1, brick1);

            Image brick2 = new Image(getClass().getResourceAsStream("/images/1234.png"), Brick.DEFAULT_HEIGHT_BRICK, Brick.DEFAULT_WIDTH_BRICK, true, false);
            brickImages.put(2, brick2);

        } catch (Exception e) {
            System.err.println("Error loading brick images:");
            e.printStackTrace();
        }
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
                        Brick newBrick = new Brick(x, y, brickImage);
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


}
