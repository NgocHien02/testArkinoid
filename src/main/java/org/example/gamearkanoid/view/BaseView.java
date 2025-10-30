package org.example.gamearkanoid.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import org.example.gamearkanoid.model.Sprite;

public abstract class BaseView<T extends Sprite> {

    protected ImageView imageView;
    protected boolean isvisible;
    protected T model;
    public BaseView() {
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


}
