package org.example.gamearkanoid.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.gamearkanoid.model.Brick;
import org.example.gamearkanoid.model.Sprite;

import java.util.Objects;

public abstract class BaseView<T extends Sprite> {

    protected ImageView imageView;
    protected boolean isvisible;
    protected T model;
    public BaseView(T model) {
        this.model = model;
        isvisible = true;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public abstract void update();

    protected void initial() {
        imageView.setX(model.getX());
        imageView.setY(model.getY());
        imageView.setFitWidth(model.getWidth());
        imageView.setFitHeight(model.getHeight());
        imageView.xProperty().bind(model.xProperty());
        imageView.yProperty().bind(model.yProperty());

    }

    protected Image loadImage(String path, double width, double height) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)), width, height,true, false);
    }

}
