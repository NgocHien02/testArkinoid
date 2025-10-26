package org.example.gamearkanoid.view;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
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
    String imgPath;

    private ImageView imageView;

    public EnemyView(String path, Enemy enemy) {
       this.imgPath = path;
       this.enemy = enemy;
       Image image = new Image(getClass().getResourceAsStream(imgPath));
       imageView = new ImageView(image);
       initial();
    }

    private void initial() {
        imageView.setX(enemy.getX());
        imageView.setY(enemy.getY());
        imageView.setFitWidth(enemy.getWidth());
        imageView.setFitHeight(enemy.getHeight());
    }

    public ImageView getImageView() {
        return imageView;
    }


    public void showWander() {
        imageView.setX(enemy.getX());
        imageView.setY(enemy.getY());
    }


}
