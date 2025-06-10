package com.gmail.ge.and.rltkd0101.smgpproject.app;

import static java.lang.Math.round;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class UpgradeRegistry {
    private static final List<UpgradeOption> allOptions = new ArrayList<>();
    private static final Random random = new Random();

    public static List<UpgradeOption> getRandomOptions(int count) {
        List<UpgradeOption> randomOptions = new ArrayList<>();

        // 매번 새로운 UpgradeOption을 생성
        for (int i = 0; i < 6; i++) {
            randomOptions.add(createRandomOption(i));
        }

        Collections.shuffle(randomOptions);
        return randomOptions.subList(0, Math.min(count, randomOptions.size()));
    }

    @SuppressLint("DefaultLocale")
    private static UpgradeOption createRandomOption(int index) {
        switch (index) {
            case 0: {
                float value = getRandom(0.3f, 1.0f);
                return new UpgradeOption(
                        "공격력 +" + String.format("%.1f", value),
                        "기본 공격력이 " + value + " 만큼 증가합니다.",
                        0,
                        () -> PlayerStats.attack += value
                );
            }
            case 1: {
                float value = getRandom(5f, 20f);
                return new UpgradeOption(
                        "이동속도 +" + (int)value,
                        "이동 속도가 " + (int)value + " 증가합니다.",
                        0,
                        () -> PlayerStats.moveSpeed += value
                );
            }
            case 2: {
                float value = getRandom(5f, 15f);
                return new UpgradeOption(
                        "최대 체력 +" + (int)value,
                        "최대 체력이 " + (int)value + " 증가합니다.",
                        0,
                        () -> PlayerStats.maxHp += value
                );
            }
            case 3: {
                float value = getRandom(0.5f, 2.0f);
                return new UpgradeOption(
                        "방어력 +" + String.format("%.1f", value),
                        "방어력이 " + value + " 만큼 증가합니다.",
                        0,
                        () -> PlayerStats.defense += value
                );
            }
            case 4: {
                float value = getRandom(0.1f, 0.5f);
                return new UpgradeOption(
                        "자연 회복 +" + String.format("%.1f", value),
                        "초당 " + value + " 만큼 체력이 회복됩니다.",
                        0,
                        () -> PlayerStats.healPerSec += value
                );
            }
            case 5: {
                float value = getRandom(1f, 4f);
                return new UpgradeOption(
                        "처치 시 회복 +" + (int)value,
                        "몬스터 처치 시 체력이 " + (int)value + " 회복됩니다.",
                        0,
                        () -> PlayerStats.healOnKill += value
                );
            }
            default:
                return null;
        }
    }

    private static float getRandom(float min, float max) {
        return min + random.nextFloat() * (max - min);
    }
}
