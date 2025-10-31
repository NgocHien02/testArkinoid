package org.example.gamearkanoid.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends  Sprite{

    enum PaddleState{
        GO_LEFT,
        GO_RIGHT,
        MOUSE_DRAG,
        IDLE
    }
    PaddleState currentState;
    public Paddle(double x, double y) {
        super(x, y, 100, 50);
        speed = 5;
        currentState = PaddleState.IDLE;
    }

    @Override
    public void update() {
        executeState();
    }

    public void executeState() {

        switch (currentState) {
            case GO_LEFT:
                setDirection(-1, 0);
                move();
                break;
            case GO_RIGHT:
                setDirection(1, 0);
                move();
                break;
            case IDLE:
                setDirection(0, 0);
            default:
                setDirection(0, 0);
        }
        if (!inScreen()) {
            System.out.println(getX() + " " + getY());

            setDirection(0, 0);
            if (getX() <= 0) {
                setX(0);
            }
            else {
                setX(screenWidth - getWidth());
            }
            System.out.println(getX() + " " + getY());
        }
    }

    public void setCurrentState(PaddleState currentState) {
        this.currentState = currentState;
    }

    public void goLeft() {
        setCurrentState(PaddleState.GO_LEFT);
    }

    public void goRight() {
        setCurrentState(PaddleState.GO_RIGHT);
    }

    public void idle() {
        setCurrentState(PaddleState.IDLE);
    }

//    public boolean checkLeft() {
//
//    }
}
