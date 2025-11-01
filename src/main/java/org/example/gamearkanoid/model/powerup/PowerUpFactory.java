package org.example.gamearkanoid.model.powerup;

import javafx.scene.image.Image;
import java.util.Random;

public class PowerUpFactory {
    private static Image GENERIC_POWERUP_IMAGE;

    private static Random random = new Random();
    private static final double DROP_CHANCE = 0.2; // Tỉ lệ rớt đồ

    private static final PowerUpType[] ALL_POWERUPS = PowerUpType.values();

    static {
        loadImages(); // Gọi hàm tải ảnh
    }
    private static void loadImages() {
        try {
            GENERIC_POWERUP_IMAGE = new Image(
                    PowerUpFactory.class.getResourceAsStream("/images/powerup.png"), // Tên file ảnh chung
                    PowerUp.POWERUP_WIDTH,
                    PowerUp.POWERUP_HEIGHT,
                    true,
                    false
            );
        } catch (Exception e) {
            System.err.println("--- LỖI NGHIÊM TRỌNG ---");
            System.err.println("Không thể tải ảnh power-up chung: /images/powerup.png");
            System.err.println("Hãy đảm bảo bạn có file này trong thư mục 'resources/images'");
            e.printStackTrace();
        }
    }

    /**
     * Tạo ngẫu nhiên một Power-Up.
     * Tất cả đều dùng chung 1 ảnh.
     */
    public static PowerUp randomPower(double x, double y) {
        if (random.nextDouble() > DROP_CHANCE) {
            return null; // Không rớt
        }

        if (GENERIC_POWERUP_IMAGE == null) {
            return null;
        }

        // Chọn ngẫu nhiên một loại từ danh sách enum
        PowerUpType randomType = ALL_POWERUPS[random.nextInt(ALL_POWERUPS.length)];

        // Dùng 'randomType' trong khối switch
        switch (randomType) {
            case BIG_PADDLE: return new PowerBigPaddle(x, y, GENERIC_POWERUP_IMAGE);
            case SMALL_PADDLE: return new PowerSmallPaddle(x, y, GENERIC_POWERUP_IMAGE);
            case SLOW_BALL: return new PowerSlowBall(x, y, GENERIC_POWERUP_IMAGE);
            case FAST_BALL: return new PowerFastBall(x, y, GENERIC_POWERUP_IMAGE);
            case BLINK_BALL: return new PowerBlinkBall(x, y, GENERIC_POWERUP_IMAGE);
            case MULTIBALL: return new PowerMultiBall(x, y, GENERIC_POWERUP_IMAGE);
            case SHIELD: return new PowerShield(x, y, GENERIC_POWERUP_IMAGE);
            case STRONG_BALL: return new PowerStrongBall(x, y, GENERIC_POWERUP_IMAGE);
            default: return null;
        }
    }
}