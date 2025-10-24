package org.example.gamearkanoid.model;

/**
 * Lớp này chứa các cờ (flag) trạng thái tĩnh
 * để giao tiếp giữa các lớp mà không cần tham chiếu trực tiếp.
 */
public class GameState {

    // Cờ cho PowerStrongBall
    public static boolean strongBallActive = false;

    // Cờ cho PowerShield
    public static boolean shieldActive = false;

    // Cờ cho PowerBlinkBall
    public static boolean blinkBallActive = false;
    // Cờ báo hiệu StrongBall đã được nhặt, CHỜ va chạm gạch để kích hoạt
    public static boolean strongBallArmed = false;
}