package org.example.gamearkanoid.model;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class BlockBrick {
    private ArrayList<Brick> block = new ArrayList<>();
    private double dx = 10;
    private double dy = 10;

    private Image image1;
    private Image image2;

    private void loadBrickImages() {
        try {
            image1 = new Image(getClass().getResourceAsStream("/images/normal_brick.png"), 100, 50, true,false);
//            image2 = new Image("path to image 2");
        } catch (Exception e) {
            System.err.println("Error load image of a brick");
        }
    }

    public void addBrick(int row, int column) {
        loadBrickImages();
        double x = 0;
        double y = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (j == 0) {
                    x = 0;
                }
                else {
                    x = x + Brick.DEFAULT_WIDTH_BRICK + dx;
                }
                Brick newBrick = new Brick(x, y, image1);
                block.add(newBrick);
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
