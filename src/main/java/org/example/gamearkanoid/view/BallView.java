package org.example.gamearkanoid.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.gamearkanoid.model.Ball;

public class BallView extends BaseView<Ball>{

    Image image = new Image(getClass().getResourceAsStream("/images/ball.png"),30,30,true,false);

    public BallView(Ball ball) {
        super();
        this.model = ball;
        setImageView(new ImageView(image));
        initial();
        model.currentStateProperty().addListener((obs, oldState, newState) -> {
            update();
        });

    }

    @Override
    public void update() {

    }
}
