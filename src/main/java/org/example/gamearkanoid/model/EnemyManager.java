package org.example.gamearkanoid.model;

import javafx.scene.layout.Pane;
import org.example.gamearkanoid.view.EnemyView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyManager {
    private List<Enemy> enemyList;
    private List<EnemyView> viewList;

    private final double HEIGHT = 50;
    private final double WIDTH = 50;

    private Random random;
    private int rate;
    public EnemyManager() {
        enemyList = new ArrayList<>();
        viewList = new ArrayList<>();
        random = new Random();
        rate = 2;
    }

    public void addEnemy(double x, double y, Paddle target, Pane pane) {
        Enemy enemy = new Enemy(x, y, WIDTH, HEIGHT, target);
        EnemyView enemyView = new EnemyView(enemy);
        enemyList.add(enemy);
        viewList.add(enemyView);
        pane.getChildren().add(enemyView.getImageView());
    }

    public void update(List<Brick> brickList, Paddle target, Pane pane, double delta) {
        addEnemy(brickList, target, pane);
        List<Enemy> deadEnemy = new ArrayList<>();
        List<EnemyView> deadView = new ArrayList<>();
        for (EnemyView enemyView : viewList) {
            if (!enemyView.getModel().isAlive()) {
                deadEnemy.add(enemyView.getModel());
                deadView.add(enemyView);
                pane.getChildren().remove(enemyView.getImageView());
            }
            else {
//                enemyView.update();
                enemyView.getModel().update();
                enemyView.updateFrame(delta);
            }
        }
        enemyList.removeAll(deadEnemy);
        viewList.removeAll(deadView);
    }

    public void addEnemy(List<Brick> brickList, Paddle target, Pane pane) {
        for (Brick brick : brickList) {
            if (!brick.isAlive() && (random.nextInt() % rate == 0) ) {
                addEnemy(brick.getX(), brick.getY(), target, pane);
            }
        }
    }

}
