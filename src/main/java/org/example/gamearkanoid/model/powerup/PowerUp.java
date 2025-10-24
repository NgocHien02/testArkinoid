package org.example.gamearkanoid.model.powerup;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.Paddle;

import java.util.List;

public abstract class PowerUp {

    protected ImageView imageView;
    protected double durationInFrames; // 0 = vĩnh viễn
    protected double timer;
    protected boolean active = false;
    protected boolean collected = false;

    private static final double FALL_SPEED = 2.0;
    public static final double POWERUP_WIDTH = 40;
    public static final double POWERUP_HEIGHT = 40;


    public PowerUp(double x, double y, Image image, double durationInFrames) {
        this.imageView = new ImageView(image);
        this.imageView.setX(x);
        this.imageView.setY(y);
        this.durationInFrames = durationInFrames;
        this.timer = durationInFrames;
    }

    /**
     * Cập nhật trạng thái của Power-Up mỗi frame.
     */
    public void update(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        if (collected) {
            if (active && durationInFrames > 0) {
                timer--;
                if (timer <= 0) {
                    this.removeEffect(paddle, balls, blocks, group);
                    this.active = false;
                }
            }
        } else {
            // Cho item rơi xuống
            imageView.setY(imageView.getY() + FALL_SPEED);
        }
    }

    /**
     * Kích hoạt hiệu ứng khi được nhặt.
     */
    public void activate(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group) {
        this.collected = true;
        this.active = true;
        this.applyEffect(paddle, balls, blocks, group);
    }

    public boolean checkPaddleCollision(Paddle paddle) {
        if (!collected && imageView.getBoundsInParent().intersects(paddle.getPaddleImgView().getBoundsInParent())) {
            collected = true;
            return true;
        }
        return false;
    }

    //Đặt lại bộ đếm thời gian của Power-Up về giá trị tối đa.
    public void resetTimer() {
        if (this.durationInFrames > 0) { // Chỉ reset timer có thời hạn
            this.timer = this.durationInFrames;
            System.out.println(this.getClass().getSimpleName() + " timer reset!"); // Dòng test
        }
    }

    // --- Các phương thức trừu tượng
    public abstract void applyEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group);
    public abstract void removeEffect(Paddle paddle, List<Ball> balls, BlockBrick blocks, Group group);

    /**
     * Phương thức trừu tượng, buộc các lớp con phải định danh xem chúng thuộc loại Power-Up nào.
     */
    public abstract PowerUpType getType();

    // --- Getters ---
    public ImageView getImageView() { return imageView; }
    public boolean isActive() { return active; }
    public boolean isCollected() { return collected; }
    public double getY() { return imageView.getY(); }
}