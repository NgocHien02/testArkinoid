package org.example.gamearkanoid.controller; // Hoặc package bạn muốn

import javafx.scene.layout.Pane;
import org.example.gamearkanoid.model.Ball;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.Brick;
import org.example.gamearkanoid.model.Paddle;
import org.example.gamearkanoid.model.powerup.*;
import javafx.scene.Group;
import org.example.gamearkanoid.view.BallView;
import org.example.gamearkanoid.view.PaddleView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Lớp này chịu trách nhiệm quản lý TOÀN BỘ logic cho Power-Up.
 * GameController sẽ "ủy thác" (delegate) công việc cho lớp này.
 * Nhiệm vụ:
 * 1. Tạo (spawn) Power-Up khi gạch vỡ.
 * 2. Cập nhật trạng thái Power-Up (cho rơi, đếm ngược timer).
 * 3. Xử lý va chạm với Paddle.
 * 4. Kích hoạt hiệu ứng hoặc "làm mới" (refresh) thời gian.
 * 5. Dọn dẹp (xóa) Power-Up khi hết hạn hoặc rơi ra ngoài.
 */
public class PowerUpManager {

    // Danh sách Power-Up được chuyển từ GameController sang đây
    private List<PowerUp> activePowerUps = new ArrayList<>();

    // Cần Group để thêm/xóa hình ảnh Power-Up
    private Pane group;

    public PowerUpManager(Pane group) {
        this.group = group;
    }

    /**
     * Được gọi bởi GameController mỗi khi có 1 viên gạch bị phá vỡ.
     * Quyết định xem có tạo (spawn) Power-Up hay không.
     *
     * @param brokenBrick Viên gạch vừa bị phá (hoặc null nếu không có).
     */
    public void spawnPowerUp(Brick brokenBrick) {
        if (brokenBrick == null) {
            return; // Không có gạch vỡ
        }

        // Tạo ngẫu nhiên 1 powerup
        PowerUp newPowerUp = PowerUpFactory.randomPower(
                brokenBrick.getX() + brokenBrick.getWidth() / 2,
                brokenBrick.getY()
        );

        if (newPowerUp != null) {
            activePowerUps.add(newPowerUp);
            group.getChildren().add(newPowerUp.getImageView());
        }
    }

    /**
     * Được gọi mỗi frame từ GameController (trong AnimationTimer).
     * Đây là "trái tim" của logic Power-Up.
     */
    public void update(PaddleView paddle, List<BallView> balls, List<Ball> ballList, BlockBrick blocks) {
        Iterator<PowerUp> powerUpIterator = activePowerUps.iterator();

        while (powerUpIterator.hasNext()) {
            PowerUp p = powerUpIterator.next();

            // Cập nhật (cho rơi hoặc đếm ngược)
            p.update(paddle, balls,ballList, blocks, group);

            // Kiểm tra va chạm với paddle
            if (!p.isCollected() && p.checkPaddleCollision(paddle)) {
                boolean effectAlreadyActive = false;

                // Kiểm tra xem item này có phải loại "có thời hạn" không
                if (p instanceof PowerBigPaddle || p instanceof PowerSmallPaddle ||
                        p instanceof PowerSlowBall || p instanceof PowerFastBall ||
                        p instanceof PowerBlinkBall || p instanceof PowerStrongBall ||
                        p instanceof PowerMultiBall)
                {
                    // Duyệt qua danh sách, tìm xem có hiệu ứng CÙNG LOẠI đang chạy không
                    for (PowerUp existingP : activePowerUps) {
                        if (existingP.isActive() && existingP.getType() == p.getType()) {
                            existingP.resetTimer();
                            effectAlreadyActive = true;
                            break;
                        }
                    }
                }

                if (!effectAlreadyActive) {
                    p.activate(paddle, balls, ballList, blocks, group);
                }
                group.getChildren().remove(p.getImageView());

                if (effectAlreadyActive) {
                    powerUpIterator.remove();
                }
            }
            // Xóa các Power-Up đã hết hạn (timer=0) hoặc rơi ra khỏi màn hình
            else if ((p.isCollected() && !p.isActive()) || p.getY() > group.getScene().getHeight()) {
                powerUpIterator.remove();
            }
        }
    }
}