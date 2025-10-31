package org.example.gamearkanoid.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockBrick  {
    private List<Brick> block;
    private double dx = 10;
    private double dy = 10;



    public BlockBrick() {
        block = new ArrayList<>();
    }

    public void addBrick(int[][] layout) {
        double y = 0;
        for (int i = 0; i < layout.length; i++) {
            double x = 0;
            for (int j = 0; j < layout[i].length; j++) {
                int brickType = layout[i][j];

                if (brickType > 0) {
                        // Xác định máu dựa trên loại gạch
                        int health;
                        if (brickType == 9) {
                            health = -1; // -1 là bất tử
                        } else if (brickType >= 1 && brickType <= 3) {
                            health = brickType; // Gạch loại 1 có 1 máu, loại 2 có 2 máu,...
                        } else {
                            health = 1; // Mặc định là 1 máu nếu loại gạch không xác định
                        }

                        Brick newBrick = new Brick(x, y, health);
                        newBrick.setType(brickType);
                        block.add(newBrick);
                }
                x = x + Brick.WIDTH+ dx;
            }
            y = y + Brick.HEIGHT+ dy;
        }
    }

    public List<Brick> getBlock() {
        return block;
    }

    public void setBlock(ArrayList<Brick> block) {
        this.block = block;
    }

    public boolean isEmpty() {
        return block.isEmpty();
    }

}
