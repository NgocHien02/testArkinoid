package org.example.gamearkanoid.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Random;

import static org.example.gamearkanoid.model.GameConfig.*;

public class Enemy extends Sprite{
    protected double health;
    protected double maxHealth;
    protected double damage;
    protected int scoreValue;
    protected Paddle target;
    protected int attackCoolDown;
    protected EnemyState currentState;

    protected Random random;

    protected enum EnemyState{
        IDLE,
        WANDERING,
        CHASING,
        ATTACK,
        DEFENSE
    }

    protected double detectionRange;
    protected  int defenseAbility;
    protected int maxDefense = 11;


    public Enemy(double x, double y, double width, double height, Paddle target) {
        super(x, y, width, height);
        this.target = target;
        maxHealth = 10;
        health = 10;
        damage = 1;
        speed = 2;
        scoreValue = 10;
        attackCoolDown = 1000;
        random = new Random();
        defenseAbility = 5;
        random = new Random();
        screenHeight = 800;
        screenWidth = 800;

        detectionRange = 400;

        setDirection(1,1);

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
        switch (currentState) {
            case WANDERING:
                wander();
                break;
            case ATTACK:
                attackTarget();
                break;
            case CHASING:
                moveToTarget();
                break;
            case DEFENSE:
//                performDefense();
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
        System.out.println("attacking");
    }

    public void wander() {

        if (inScreen()) {
            move();
        }
        else {
            setDirection(random.nextDouble() * 2 - 1, random.nextDouble() * 2 - 1);
        }

    }


    public void updateState() {

        if (target == null || !target.isAlive()) {
            currentState = EnemyState.IDLE;
        }

//        if (beingAttacked() && rand.nextInt() % (maxDefense - defenseAbility) == 0) {
//            currentState = EnemyState.DEFENSE;
//        }
        else {
            double distance = distanceToTarget(target);
            System.out.println(target.getX());
            System.out.println(target.getY());

            if (checkCollision(target)) {
                currentState = EnemyState.ATTACK;
                System.out.println("attack");
            } else if (distance <= detectionRange) {
                currentState = EnemyState.CHASING;
                System.out.println("chasing");
            } else {
                currentState = EnemyState.WANDERING;
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


}
