package org.example.gamearkanoid.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;

public abstract class BaseObject {

    protected BooleanProperty isAlive;
    protected DoubleProperty x;
    protected DoubleProperty y;
    protected DoubleProperty height;
    protected DoubleProperty width;

    /**
     * constructor mặc định của lớp thủy tổ.
     * @param x vị trí x của node
     * @param y vị trí y của node
     */
    public BaseObject(double x, double y) {
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.height = new SimpleDoubleProperty();
        this.width = new SimpleDoubleProperty();
        this.isAlive = new SimpleBooleanProperty(true);
    }

    /**
     * logic update (cài theo từng class)
     */



    public double getX() {return x.get();}
    public double getY() {return y.get();}
    public double getWidth() {return width.get();}
    public double getHeight() {return height.get();}
    public double getCenterX() {return getX() + getWidth() / 2;}
    public double getCenterY() {return  getY() + getHeight() / 2;}
    public boolean isAlive() {
        return isAlive.get();
    }

    public void setX(double x) {this.x.set(x);}
    public void setY(double y) {this.y.set(y);}
    public void setHeight(double height) {this.height.set(height);}
    public void setWidth(double width) {this.width.set(width);}
    public void  setPosition(double x, double y) {
        this.x.set(x);
        this.y.set(y);
    }
    public void setAlive(boolean isAlive) {this.isAlive.set(isAlive);}


    public BooleanProperty isAliveProperty() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive.set(isAlive);
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    public DoubleProperty widthProperty() {
        return width;
    }
}
