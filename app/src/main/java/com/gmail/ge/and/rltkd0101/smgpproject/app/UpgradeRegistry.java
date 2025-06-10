package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class UpgradeRegistry {
    private static final Random random = new Random();

    public static List<UpgradeOption> getRandomOptions(int count) {
        List<UpgradeOption> result = new ArrayList<>();
        List<PlayerStats.StatType> allTypes = new ArrayList<>();

        Collections.addAll(allTypes, PlayerStats.StatType.values());
        Collections.shuffle(allTypes);

        for (int i = 0; i < count && i < allTypes.size(); i++) {
            PlayerStats.StatType statType = allTypes.get(i);
            result.add(generateOption(statType));
        }
        return result;
    }

    private static UpgradeOption generateOption(PlayerStats.StatType statType) {
        float value;
        String name;
        String description;

        switch (statType) {
            case ATTACK:
                value = randomRange(0.1f, 1.0f);
                name = "공격력 +" + format(value);
                description = "기본 공격력이 " + format(value) + " 증가합니다.";
                return new UpgradeOption(name, description, 0, () -> PlayerStats.attack += value);

            case MOVE_SPEED:
                value = randomRange(1f, 15f);
                name = "이동속도 +" + format(value);
                description = "이동속도가 " + format(value) + " 증가합니다.";
                return new UpgradeOption(name, description, 0, () -> PlayerStats.moveSpeed += value);

            case MAX_HP:
                value = randomRange(1f, 10f);
                name = "최대 체력 +" + format(value);
                description = "최대 체력이 " + format(value) + " 증가합니다.";
                return new UpgradeOption(name, description, 0, () -> PlayerStats.maxHp += value);

            case DEFENSE:
                value = randomRange(0.1f, 1.0f);
                name = "방어력 +" + format(value);
                description = "받는 피해가 줄어듭니다.";
                return new UpgradeOption(name, description, 0, () -> PlayerStats.defense += value);

            case HEAL_PER_SEC:
                value = randomRange(0.1f, 0.8f);
                name = "자연회복 +" + format(value);
                description = "초당 체력이 " + format(value) + " 회복됩니다.";
                return new UpgradeOption(name, description, 0, () -> PlayerStats.healPerSec += value);

            case HEAL_ON_KILL:
                value = randomRange(0.2f, 1.0f);
                name = "처치 회복 +" + format(value);
                description = "적 처치 시 체력 " + format(value) + " 회복.";
                return new UpgradeOption(name, description, 0, () -> PlayerStats.healOnKill += value);

            case ATTACK_SPEED:
                value = randomRange(0.1f, 0.3f);
                name = "공격속도 +" + format(value);
                description = "공격속도가 증가합니다.";
                return new UpgradeOption(name, description, 0, () -> PlayerStats.attackSpeed += value);

            default:
                return new UpgradeOption("미정", "설정되지 않은 옵션입니다.", 0, () -> {});
        }
    }

    private static float randomRange(float min, float max) {
        return min + random.nextFloat() * (max - min);
    }

    @SuppressLint("DefaultLocale")
    private static String format(float value) {
        return String.format("%.1f", value);
    }
}
