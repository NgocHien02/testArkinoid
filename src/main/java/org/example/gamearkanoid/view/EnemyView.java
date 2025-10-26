package org.example.gamearkanoid.view;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.gamearkanoid.model.Enemy;
import org.example.gamearkanoid.model.Paddle;

public class EnemyView {

    private Enemy enemy;
    private ImageView imageView;
    private int currentFrame = 0;
    private int totalFrames = 4;
    private double animationTimer = 0;
    private double animationInterval = 0.1;
    private double frameWidth = 50, frameHeight = 50;

    // Sửa "getClass()" thành "EnemyView.class" (hoặc tên lớp của bạn)
    private static final Image WANDER_SHEET = new Image(EnemyView.class.getResourceAsStream("/images/enemy_sprite.png"));
    private static final Image CHASE_SHEET = new Image(EnemyView.class.getResourceAsStream("/images/enemy_sprite.png"));
    private static final Image ATTACK_SHEET = new Image(EnemyView.class.getResourceAsStream("/images/enemy_sprite.png"));

    public EnemyView(Enemy enemy) {
       this.enemy = enemy;
       imageView = new ImageView(WANDER_SHEET);
       initial();
    }

    private void initial() {
        imageView.setX(enemy.getX());
        imageView.setY(enemy.getY());
        imageView.setFitWidth(enemy.getWidth());
        imageView.setFitHeight(enemy.getHeight());
        imageView.xProperty().bind(enemy.xProperty());
        imageView.yProperty().bind(enemy.yProperty());
        enemy.currentStateProperty().addListener((obs, oldState, newState) -> {
            switchAnimation(newState);});
    }

    // Cập nhật logic animation (chuyển frame)
    public void update(double delta) {
        if (enemy.isAlive() == false) {
            return;
        }
        animationTimer += delta;

        if (animationTimer >= animationInterval) {
            animationTimer = 0;
            currentFrame = (currentFrame + 1) % totalFrames;

            double viewportX = currentFrame * frameWidth;
            this.imageView.setViewport(new Rectangle2D(viewportX, 0, frameWidth, frameHeight));
        }
    }

    private void switchAnimation(Enemy.EnemyState newState) {
        currentFrame = 0;
        animationTimer = 0;

        switch (newState) {
            case IDLE:
                break;
            case WANDERING:
                this.imageView.setImage(WANDER_SHEET);
                break;
            case CHASING:
                this.imageView.setImage(CHASE_SHEET);
                break;
            case ATTACK:
                this.imageView.setImage(ATTACK_SHEET);
                break;
            default:
                this.imageView.setImage(null);
                this.totalFrames = 1;
        }

    }


    public ImageView getImageView() {
        return imageView;
    }


}
