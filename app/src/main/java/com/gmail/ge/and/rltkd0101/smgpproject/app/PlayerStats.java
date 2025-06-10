package com.gmail.ge.and.rltkd0101.smgpproject.app;

public class PlayerStats {
    // 기본값
    public static float maxHp = 100f;
    public static float moveSpeed = 250f;
    public static float attack = 1.0f;
    public static float defense = 0.0f;

    // 강화 관련 옵션
    public static float healPerSec = 0.0f;   // 자연 회복량
    public static float healOnKill = 0.0f;   // 몬스터 처치 시 회복량

    // 유틸
    public static void reset() {
        maxHp = 100f;
        moveSpeed = 250f;
        attack = 1.0f;
        defense = 0.0f;
        healPerSec = 0.0f;
        healOnKill = 0.0f;
    }

    // 예시: 강화 적용 메서드
    public static void applyStatUpgrade(StatType statType) {
        switch (statType) {
            case MAX_HP: maxHp += 3f; break;
            case ATTACK: attack += 0.2f; break;
            case DEFENSE: defense += 0.5f; break;
            case MOVE_SPEED: moveSpeed += 10f; break;
            case HEAL_PER_SEC: healPerSec += 0.2f; break;
            case HEAL_ON_KILL: healOnKill += 2.0f; break;
        }
    }

    public enum StatType {
        MAX_HP, ATTACK, DEFENSE, MOVE_SPEED, HEAL_PER_SEC, HEAL_ON_KILL
    }
}
