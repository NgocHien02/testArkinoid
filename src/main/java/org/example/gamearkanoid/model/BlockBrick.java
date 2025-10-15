package org.example.gamearkanoid.model;

import java.util.ArrayList;

public class BlockBrick {
    private ArrayList<Brick> block = new ArrayList<>();
    private double dx = 10;
    private double dy = 10;

    public void addBrick(int row, int column) {
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
                Brick newBrick = new Brick(x, y);
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
