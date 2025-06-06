package com.gmail.ge.and.rltkd0101.smgpproject.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UpgradeRegistry {
    private static final List<UpgradeOption> allOptions = new ArrayList<>();

    static {
        allOptions.add(new UpgradeOption(
                "공격력 +1", "기본 공격력이 증가합니다.", 0,
                () -> PlayerStats.attack++)
        );

        allOptions.add(new UpgradeOption(
                "이동속도 +10%", "플레이어가 더 빠르게 움직입니다.", 0,
                () -> PlayerStats.moveSpeed *= 1.1f)
        );

        allOptions.add(new UpgradeOption(
                "최대 체력 +10", "더 많은 피해를 버틸 수 있습니다.", 0,
                () -> PlayerStats.maxHp += 10f)
        );

        allOptions.add(new UpgradeOption(
                "방어력 +2", "받는 피해가 감소합니다.", 0,
                () -> PlayerStats.defense += 2)
        );

        // 원하면 더 추가 가능
    }

    public static List<UpgradeOption> getRandomOptions(int count) {
        List<UpgradeOption> copy = new ArrayList<>(allOptions);
        Collections.shuffle(copy);
        return copy.subList(0, Math.min(count, copy.size()));
    }
}
