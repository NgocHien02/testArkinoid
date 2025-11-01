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

public class EnemyView extends BaseView<Enemy> {

//    private Enemy enemy;
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
        super(enemy);
        setImageView(new ImageView(WANDER_SHEET));
       initial();
        model.currentStateProperty().addListener((obs, oldState, newState) -> {
            update();});
    }



    // Cập nhật logic animation (chuyển frame)
    public void updateFrame(double delta) {
        if (model.isAlive() == false) {
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
    @Override
    public void update() {

        currentFrame = 0;
        animationTimer = 0;

        switch (model.getCurrentState()) {
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



}
