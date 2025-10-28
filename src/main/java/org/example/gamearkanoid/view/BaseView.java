package org.example.gamearkanoid.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

public class BaseView {

    protected ImageView imageView;
    protected int currentFrame;
    protected int totalFrames;
    protected double animationTimer;
    protected double animationInterval;
    protected double frameWidth, frameHeight;

    public BaseView() {

    }
    // Cập nhật logic animation (chuyển frame)
    public void update(double delta) {

        animationTimer += delta;

        if (animationTimer >= animationInterval) {
            animationTimer = 0;
            currentFrame = (currentFrame + 1) % totalFrames;

            double viewportX = currentFrame * frameWidth;
            this.imageView.setViewport(new Rectangle2D(viewportX, 0, frameWidth, frameHeight));
        }
    }



    public ImageView getImageView() {
        return imageView;
    }



}
