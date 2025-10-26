package org.example.gamearkanoid.model;

import javafx.scene.Node;

public abstract class BaseObject {

    protected boolean isAlive;
    protected double x;
    protected double y;
    protected double height;
    protected double width;

    /**
     * constructor mặc định của lớp thủy tổ.
     * @param x vị trí x của node
     * @param y vị trí y của node
     */
    public BaseObject(double x, double y) {
        this.x = x;
        this.y = y;
        this.isAlive = true;
    }

    /**
     * logic update (cài theo từng class)
     */
    public abstract void update();


    public double getX() {return x;}

    public double getY() {return y;}

    public void  setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getCenterX() {return x + width / 2;}

    public double getCenterY() {return  y + height / 2;}

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {this.isAlive = isAlive;}

}
