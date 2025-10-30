package org.example.gamearkanoid.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Random;

import static org.example.gamearkanoid.model.GameConfig.*;

public class Enemy extends Sprite{
    protected double health;
    protected double damage;
    protected int scoreValue;
    protected Paddle target;
    protected ObjectProperty<EnemyState> currentState;

    protected Random random;
    protected double wanderSteerStrength;

    public enum EnemyState{
        IDLE,
        WANDERING,
        CHASING,
        ATTACK
    }

    protected double detectionRange;


    public Enemy(double x, double y, double width, double height, Paddle target) {
        super(x, y, width, height);
        this.target = target;
        health = 1;
        damage = 1;
        speed = 2;
        scoreValue = 10;
        random = new Random();

        detectionRange = 400;
        setDirection(1,1);
        this.currentState = new SimpleObjectProperty<>(EnemyState.WANDERING);
        wanderSteerStrength = 0.5;

    }



    @Override
    public void update() {
        if (!isAlive()) {
            return;
        }

        updateState();
        executeState();
    }

    public void executeState() {
        switch (getCurrentState()) {
            case WANDERING:
                wander();
                break;
            case ATTACK:
                attackTarget();
                break;
            case CHASING:
                moveToTarget();
                break;
            case IDLE:
                notMove();
                break;
            default:
                break;
        }
    }

    public void notMove() {
        setDirection(0, 0);
    }

    public void moveToTarget() {
        double targetX = target.getCenterX();
        double targetY = target.getCenterY();

        double rawX = targetX - getCenterX();
        double rawY = targetY - getCenterY();

        setDirection(rawX, rawY);
        move();
    }


    public void attackTarget() {
        if (health > 1) {
            health -= 1;
        }
        else {
            setAlive(false);
        }
        setDirection(0, 0);
    }


    public void wander() {
        if (!inScreen()) {

            setDirection(random.nextDouble() * 2 - 1, random.nextDouble() * 2 - 1);
        }
        else {

            double steerX = (random.nextDouble() - 0.5) * wanderSteerStrength;
            double steerY = (random.nextDouble() - 0.5) * wanderSteerStrength;

            // Đặt hướng mới = hướng cũ + một chút "lắc"
            setDirection(this.dirX + steerX, this.dirY + steerY);
        }

        move();
    }


    public void updateState() {

        if (target == null || !target.isAlive()) {
            setCurrentState(EnemyState.IDLE);
        }
        else {
            double distance = distanceToTarget(target);
            System.out.println(target.getX());
            System.out.println(target.getY());

            if (checkCollision(target)) {
                setCurrentState(EnemyState.ATTACK);
                System.out.println("attacking");
            } else if (distance <= detectionRange) {
                setCurrentState(EnemyState.CHASING);
                System.out.println("chasing");
            } else {
                setCurrentState(EnemyState.WANDERING);
                System.out.println("wandering");
            }
        }

    }

    public double distanceToTarget(Paddle target) {
        double dx = getCenterX() - target.getCenterX();
        double dy = getCenterY() - target.getCenterY();

        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    public void setUpParameter(double detectionRange) {
        this.detectionRange = detectionRange;

    }

    public EnemyState getCurrentState() {
        return currentState.get();
    }

    public void setCurrentState(EnemyState currentState) {
        this.currentState.set(currentState);
    }

    public ObjectProperty<EnemyState> currentStateProperty() {
        return currentState;
    }

}
