package org.example.gamearkanoid.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Brick extends BaseObject {
    private ImageView brickImageView;
    private int health;
    private int initialHealth;
    private BlockBrick blockBrickManager;

    public static final double DEFAULT_HEIGHT_BRICK = 50;
    public static final double DEFAULT_WIDTH_BRICK = 100;


    public Brick(double x, double y, Image image, int health) {
        super(x, y );
        this.brickImageView = new ImageView(image);
        this.brickImageView.setX(x);
        this.brickImageView.setY(y);
        this.health = health;
        this.initialHealth = health;
        setWidth(DEFAULT_WIDTH_BRICK);
        setHeight(DEFAULT_HEIGHT_BRICK);
    }

    public void setBlockBrickManager(BlockBrick blockBrickManager) {
        this.blockBrickManager = blockBrickManager;
    }

    public void takeDamage() {
        if (isIndestructible()) {
            return;
        }
        health--;
        if (health > 0) {
            updateImage();
        }
    }

    private void updateImage() {
        Image newImage = null;

        if (initialHealth == 2 && health == 1) {
            newImage = blockBrickManager.getImageForType(201);
        }
//        // Nếu là gạch 3 máu ban đầu
//        if (initialHealth == 3) {
//            if (health == 2) {
//                newImage = blockBrickManager.getImageForType(302); // Lấy ảnh "3_cracked_1"
//            } else if (health == 1) {
//                newImage = blockBrickManager.getImageForType(301); // Lấy ảnh "3_cracked_2"
//            }
//        }
        if (newImage != null) {
            brickImageView.setImage(newImage);
        }
    }

    /**
     * Kiểm tra xem gạch có bị phá hủy (hết máu)
     */
    public boolean isDestroyed() {
        return health == 0;
    }

    /**
     * Kiểm tra xem gạch có phải là bất tử
     */
    public boolean isIndestructible() {
        return health < 0; // Quy ước máu < 0 là bất tử
    }

    public double getX() {
        return brickImageView.getX();
    }

    public double getY() {
        return brickImageView.getY();
    }

    public double getHeight() {
        return brickImageView.getFitHeight();
    }

    public double getWidth() {
        return brickImageView.getFitWidth();
    }

    public ImageView getBrickImageView() {
        return brickImageView;
    }

    public void setBrickImageView(ImageView brickImageView) {
        this.brickImageView = brickImageView;
    }
}
