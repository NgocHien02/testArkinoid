package org.example.gamearkanoid.model;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

import java.util.List;

public class Ball extends Sprite {

    private ObjectProperty<BallState> currentState;
    private Paddle paddle;
    private Brick brick;
//    private List<Brick> targetList;
    private BlockBrick blockBrick;
    private double ANGLE_SENSITIVITY = 1.0;
    private double PADDLE_INFLUENCE = 0.4;

    public enum BallState{
        MOVING,
        TOUCH_BORDER,
        TOUCH_BRICK,
        TOUCH_PADDLE
    }
    public Ball(double x, double y) {
        super(x, y, 30, 30);
        dirX = 1;
        dirY = 1;
        speed = 2.5;
        currentState = new SimpleObjectProperty<>(BallState.MOVING);
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public void setBrick(Brick brick) {
        this.brick = brick;
    }

    public void setBlockBrick(BlockBrick blockBrick) {
        this.blockBrick = blockBrick;
    }

    @Override
    public void update() {
        updateState();
        executeState();
    }

    private void updateState() {
        if (!inScreen()) {
            setCurrentState(BallState.TOUCH_BORDER);
        }
        else {
            if (checkCollision(paddle)) {
                setCurrentState(BallState.TOUCH_PADDLE);
            }
            else if (checkCollision(blockBrick.getBlock())) {
                setCurrentState(BallState.TOUCH_BRICK);
            }
            else {
                setCurrentState(BallState.MOVING);
            }
        }
    }

    private void executeState() {
        switch (getCurrentState()) {
            case TOUCH_BRICK:
                boundBrick();
                brick.takeDamage();
                move();
                break;
            case TOUCH_BORDER:
                if (boundBorder() == false) {
                    setAlive(false);
                    setDirection(0, 0);
                }
                move();
                break;
            case TOUCH_PADDLE:
                boundPaddle();
                move();
                break;
            case MOVING:
                move();
                break;
            default:
                move();
                break;
        }
    }

    private boolean boundBorder() {
        // Lấy hướng hiện tại (để chuẩn bị thay đổi nếu cần)
        double newDirX = this.dirX;
        double newDirY = this.dirY;

        // --- 1. KIỂM TRA VA CHẠM NGANG (TRÁI / PHẢI) ---

        // Va chạm cạnh TRÁI
        if (getX() <= 0) {
            newDirX = -this.dirX; // Đảo hướng X
            setX(0); // Xử lý chồng lấn: đẩy bóng ra khỏi cạnh
        }
        // Va chạm cạnh PHẢI
        else if (getX() + getWidth() >= screenWidth) {
            newDirX = -this.dirX; // Đảo hướng X
            setX(screenWidth - getWidth()); // Xử lý chồng lấn
        }

        // --- 2. KIỂM TRA VA CHẠM DỌC (TRÊN / DƯỚI) ---

        // Va chạm cạnh TRÊN
        if (getY() <= 0) {
            newDirY = -this.dirY; // Đảo hướng Y
            setY(0); // Xử lý chồng lấn
        }
        // Va chạm cạnh DƯỚI (Rớt ra ngoài)
        else if (getY() + getHeight() >= screenHeight) {
            // Bóng đã rơi ra khỏi màn hình
            // Bạn có thể đặt lại vị trí bóng hoặc xử lý thua ở đây
            return false;
        }

        // --- 3. CẬP NHẬT HƯỚNG ---
        // Cập nhật hướng một lần duy nhất.
        // Cách này xử lý đúng va chạm ở góc (khi cả X và Y đều bị đảo)
        setDirection(newDirX, newDirY);

        // Nếu không chạm đáy, bóng vẫn "sống"
        return true;
    }


    public void boundBrick() {

        // --- 1. TÍNH TOÁN ĐỘ LÚN (OVERLAP) ---
        double c1x = this.getCenterX();
        double c1y = this.getCenterY();
        double c2x = brick.getCenterX();
        double c2y = brick.getCenterY();

        double diffX = c1x - c2x;
        double diffY = c1y - c2y;

        double totalHalfWidths = (this.getWidth() / 2.0) + (brick.getWidth() / 2.0);
        double totalHalfHeights = (this.getHeight() / 2.0) + (brick.getWidth() / 2.0);

        // Tính độ lún trên mỗi trục
        double overlapX = totalHalfWidths - Math.abs(diffX);
        double overlapY = totalHalfHeights - Math.abs(diffY);

        // --- 2. XÁC ĐỊNH TRỤC VA CHẠM VÀ PHẢN XẠ ---

        // So sánh độ lún để tìm ra trục va chạm "chính"
        // Trục có độ lún ÍT HƠN là trục va chạm (vì nó vừa mới chạm vào)

        if (overlapY < overlapX) {
            // --- VA CHẠM DỌC (TRÊN/DƯỚI) ---

            // 2a. Phản xạ vận tốc Y
            this.dirY = -this.dirY;

            // 2b. Xử lý chồng lấn (Đẩy bóng ra)
            if (diffY > 0) { // Bóng ở DƯỚI gạch
                this.setY(brick.getY() + brick.getHeight());
            } else { // Bóng ở TRÊN gạch
                this.setY(brick.getY() - this.getHeight());
            }

        } else {
            // --- VA CHẠM NGANG (TRÁI/PHẢI) ---

            // 3a. Phản xạ vận tốc X
            this.dirX = -this.dirX;

            // 3b. Xử lý chồng lấn (Đẩy bóng ra)
            if (diffX > 0) { // Bóng ở bên PHẢI gạch
                this.setX(brick.getX() + brick.getWidth());
            } else { // Bóng ở bên TRÁI gạch
                this.setX(brick.getX() - this.getWidth());
            }
        }

        // Lưu ý: Tốc độ (this.speed) không đổi trong va chạm phản xạ đơn giản.
    }

    public boolean checkCollision(List<Brick> list) {
        for (Brick target : list) {
            if (checkCollision(target)) {
                this.brick = target;
                this.brick.takeDamage();
                if (brick.isAlive() == false) {
                    list.remove(target);
                }
                return true;
            }
        // --- SỬA ĐỔI LOGIC BIÊN DƯỚI (Hỗ trợ PowerShield) ---
        if (ball.getY() + ball.getFitHeight() > sceneHeight) {
            // 1. Kiểm tra xem Shield có đang hoạt động không
            if (GameState.shieldActive) {
                setDirectionY(-1); // Bật ngược lên
                ball.setY(sceneHeight - ball.getFitHeight()); // Kẹp lại vị trí
                GameState.shieldActive = false; // Tắt khiên (chỉ dùng 1 lần)

            } else {
                // 2. Logic Game Over như cũ
                setDirectionY(0);
                setDirectionX(0);
                ball.setY(sceneHeight - ball.getFitHeight());
            }
        }
        return false;
    }

    public void boundPaddle() {

        // --- 1. LẤY VẬN TỐC HIỆN TẠI ---
        double ball_vx = this.dirX * this.speed;
        double ball_vy = this.dirY * this.speed;
        double paddle_vx = paddle.dirX * paddle.speed; // Paddle chỉ di chuyển ngang


        // --- 2. TÍNH TOÁN VẬN TỐC MỚI ---

        // A. Vận tốc Dọc Mới (new_vy)
        // Đơn giản là nảy ngược lại
        double new_vy = -ball_vy;

        // B. Vận tốc Ngang Mới (new_vx)

        // B.1. Tính điểm va chạm tương đối (từ -1.0 đến +1.0)
        double relative_impact = this.getCenterX() - paddle.getCenterX();
        double normalized_impact = relative_impact / (paddle.getWidth() / 2.0);

        // Giới hạn giá trị trong khoảng [-1, 1] để đảm bảo an toàn
        normalized_impact = Math.max(-1.0, Math.min(1.0, normalized_impact));

        // B.2. Tính vận tốc ngang cơ bản (dựa trên góc)
        // Chúng ta dùng "this.speed" (tốc độ tổng) để giữ năng lượng
        double base_vx = this.speed * normalized_impact * ANGLE_SENSITIVITY;

        // B.3. Thêm ảnh hưởng từ tốc độ của paddle
        double new_vx = base_vx + (paddle_vx * PADDLE_INFLUENCE);

        for (Brick brick : block.getBlock()) {
            if (ball.getBoundsInParent().intersects(brick.getBrickImageView().getBoundsInParent())) {
                // Kiểm tra xem bóng đã "lên đạn" (armed) chưa
                if (GameState.strongBallArmed) {
                    GameState.strongBallActive = true; // Kích hoạt hiệu ứng NGAY BÂY GIỜ
                    GameState.strongBallArmed = false;  // Và dùng hết "đạn"
                }
                // --- SỬA ĐỔI LOGIC VA CHẠM (Hỗ trợ StrongBall) ---
                // Chỉ đổi hướng nếu bóng KHÔNG mạnh
                if (!GameState.strongBallActive) {
                    double ballCenterX = ball.getX() + ball.getFitWidth() / 2;
                    double ballCenterY = ball.getY() + ball.getFitHeight() / 2;
                    double brickCenterX = brick.getX() + brick.getWidth() / 2;
                    double brickCenterY = brick.getY() + brick.getHeight() / 2;

        // --- 3. CẬP NHẬT LẠI BÓNG ---

        // A. Tính tốc độ mới (speed)
        // Tốc độ mới sẽ thay đổi dựa trên new_vx và new_vy
        double new_speed = Math.sqrt(new_vx * new_vx + new_vy * new_vy);
        this.speed = new_speed;

        // B. Chuẩn hóa và cập nhật hướng mới (dirx, diry)
        if (new_speed > 0) {
            setDirection(new_vx / new_speed, new_vy / new_speed);
        } else {
            // Trường hợp hiếm gặp: bóng dừng lại
            setDirection(0, 0);
        }

        for (Brick b : toRemove) {
            group.getChildren().remove(b.getBrickImageView());
            block.getBlock().remove(b);
        }

        return brokenBrick; // Trả về gạch đã vỡ
    }

    //Lấy tốc độ gốc 100% của bóng.
    public double getOriginalSpeed() {
        return originalSpeed;
    }

    public double getSpeed() {
        return speed;
    }

        // --- 4. (RẤT QUAN TRỌNG) XỬ LÝ CHỒNG LẤN ---
        // Đẩy bóng ra khỏi paddle để tránh bị dính ở khung hình sau.
        // Giả sử bóng va chạm với mặt TRÊN của paddle.
        setY(paddle.getY() - getHeight());
    }

    public BallState getCurrentState() {
        return currentState.get();
    }

    public ObjectProperty<BallState> currentStateProperty() {
        return currentState;
    }

    public void setCurrentState(BallState currentState) {
        this.currentState.set(currentState);
    }
}

