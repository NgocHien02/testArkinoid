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

}