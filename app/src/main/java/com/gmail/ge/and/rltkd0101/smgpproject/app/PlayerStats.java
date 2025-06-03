package com.gmail.ge.and.rltkd0101.smgpproject.app;

public class PlayerStats {
    public static int attack = 1;
    public static float moveSpeed = 100f;
    public static float maxHp = 10f;

    public static void upgradeAttack() {
        attack++;
    }

    public static void upgradeSpeed() {
        moveSpeed *= 1.1f;
    }

    public static void upgradeHp() {
        maxHp += 2f;
    }
}
