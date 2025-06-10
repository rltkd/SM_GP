package com.gmail.ge.and.rltkd0101.smgpproject.app;

public class PlayerStats {
    // 기본값
    public static float maxHp = 100f;
    public static float moveSpeed = 250f;
    public static float attack = 1.0f;
    public static float defense = 0.0f;

    // 회복 관련
    public static float healPerSec = 0.0f;   // 초당 회복
    public static float healOnKill = 0.0f;   // 처치 시 회복

    // 원래 초기화 값 저장 (리셋 시 참고)
    private static final float DEFAULT_MAX_HP = 100f;
    private static final float DEFAULT_MOVE_SPEED = 250f;
    private static final float DEFAULT_ATTACK = 1.0f;
    private static final float DEFAULT_DEFENSE = 0.0f;
    private static final float DEFAULT_HEAL_PER_SEC = 0.0f;
    private static final float DEFAULT_HEAL_ON_KILL = 0.0f;

    // 스탯 리셋 (게임 재시작 등)
    public static void reset() {
        maxHp = DEFAULT_MAX_HP;
        moveSpeed = DEFAULT_MOVE_SPEED;
        attack = DEFAULT_ATTACK;
        defense = DEFAULT_DEFENSE;
        healPerSec = DEFAULT_HEAL_PER_SEC;
        healOnKill = DEFAULT_HEAL_ON_KILL;
    }
}
