package org.example.gamearkanoid.model;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Ball {
    private Circle ball = new Circle();
    private double speed = 1;
    private int directionX = 1;
    private int directionY = 1;

    public Ball(double x, double y, double r) {
        ball.setCenterX(x);
        ball.setCenterY(y);
        ball.setRadius(r);
        ball.setFill(Color.BLACK);
    }

    public void updatePosition() {
        ball.setCenterX(ball.getCenterX() + directionX * speed);
        ball.setCenterY(ball.getCenterY() + directionY * speed);
    }

    public void checkPaddle(Paddle paddle) {
        Circle b = this.ball;
        Rectangle p = paddle.getPaddle();

//         Kiểm tra va chạm
        if (b.getBoundsInParent().intersects(p.getBoundsInParent())) {

//             Đặt lại bóng ngay trên paddle để tránh bị kẹt
            b.setCenterY(p.getY() - b.getRadius() - 1);

//             Đảo hướng Y (bóng bật ngược lên)
            directionY = -directionY;

//             Xác định vùng va chạm để điều chỉnh hướng X
            double paddleCenter = p.getX() + p.getWidth() / 2;
            double ballCenter = b.getCenterX();

//             Nếu bóng chạm bên trái hoặc phải paddle → đổi hướng X
            if (ballCenter < paddleCenter - p.getWidth() / 4) {
                directionX = -1; // đi về bên trái
            } else if (ballCenter > paddleCenter + p.getWidth() / 4) {
                directionX = 1; // đi về bên phải
            }

//             Tùy chọn: tăng tốc nhẹ sau mỗi lần chạm
            speed += 0.05;
        }
    }

    public void checkBorder(Scene scene) {
        boolean atRightBorder = ball.getCenterX() >= (scene.getWidth() - ball.getRadius());
        boolean atLeftBorder = ball.getCenterX()<= 0;
        boolean atTopBorder = ball.getCenterY() <= (ball.getRadius());
        boolean atBottomBorder = ball.getCenterY() >= (scene.getHeight() - ball.getRadius());

        //Here we change the Direction of the Ball
        if (atRightBorder) {
            setDirectionX(-1);
        }

        if (atLeftBorder) {
           setDirectionX(1);
        }

        if (atTopBorder) {
            setDirectionY(1);
        }
        if (atBottomBorder) {
           setDirectionY(0);
           setDirectionX(0);
        }

    }

    public void checkBlock(BlockBrick block, Group group) {
        for (Brick b : block.getBlock()) {
            boolean ball_down_block = getX() <= b.getX() +  b.getWidth() && getX() >= b.getX() -  getWidth() &&  getY() <= b.getY() +  b.getHeight() + 3 &&  getY() >= b.getY() +  b.getHeight() - 3;
            boolean ball_above_block = getX() <= b.getX() +  b.getWidth() && getX() >= b.getX() -  getWidth() &&  getY() <= b.getY() -  getHeight() + 3 &&  getY() >= b.getY() -  getHeight() - 3;
            boolean ball_left_block = getY() <= b.getY() +  b.getHeight() && getY() >= b.getY() -  getHeight() &&  getX() <= b.getX() -  getWidth() + 3 &&  getX() >= b.getX() -  getWidth() - 3;
            boolean blockRight = getY() <= b.getY() +  b.getHeight() && getY() >= b.getY() -  getHeight() &&  getX() <= b.getX() +  b.getWidth() + 3 && getX() >= b.getX() +  b.getWidth() - 3;

            if (ball_down_block) {
                group.getChildren().remove(b.getBrick());
                setDirectionY(1);
            } else if (ball_above_block) {
                group.getChildren().remove(b.getBrick());
                setDirectionY(-1);
            } else if (ball_left_block) {
                group.getChildren().remove(b.getBrick());
                setDirectionX(-1);
            } else if (blockRight) {
                group.getChildren().remove(b.getBrick());
                setDirectionX(1);
            }
        }
    }


    public void setBall(Circle ball) {
        this.ball = ball;
    }

    public Circle getBall() {
        return ball;
    }


    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDirectionX() {
        return directionX;
    }

    public void setDirectionX(int directionX) {
        this.directionX = directionX;
    }

    public int getDirectionY() {
        return directionY;
    }

    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }

    public double getX() {
        return ball.getCenterX();
    }
    
    public double getY() {
        return ball.getCenterY();
    }
    
    public double getWidth() {
        return ball.getRadius();
    }
    
    public double getHeight() {
        return ball.getRadius();
    }
    
}
