package org.example.gamearkanoid.model;

import javafx.scene.image.Image;


import java.util.ArrayList;

public class Brick extends Sprite {
    private int health;
    private int initialHealth;
    private BlockBrick blockBrickManager;
    private int type;

    public static final double DEFAULT_HEIGHT_BRICK = 50;
    public static final double DEFAULT_WIDTH_BRICK = 100;


    public Brick(double x, double y, int health) {
        super(x, y,DEFAULT_WIDTH_BRICK, DEFAULT_HEIGHT_BRICK );
        this.health = health;
        this.initialHealth = health;
    }

    public void takeDamage() {
        if (isIndestructible()) {
            return;
        }
        health--;
        if (health <= 0) {
            setAlive(false);
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

    @Override
    public void update() {

    }

    public int getHealth() {
        return health;
    }

    public int getInitialHealth() {
        return initialHealth;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
