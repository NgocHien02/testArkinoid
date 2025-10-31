package org.example.gamearkanoid.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.gamearkanoid.model.Paddle;

public class PaddleView extends BaseView<Paddle> {

    private Image image = new Image(getClass().getResourceAsStream("/images/paddle.png"), 100, 50, true, false);

    public PaddleView(Paddle paddle) {
        super(paddle);
        setImageView(new ImageView(image));
        initial();
    }
    @Override
    public void update() {

    }
}
