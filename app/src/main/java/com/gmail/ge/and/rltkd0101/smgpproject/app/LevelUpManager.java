package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.app.AlertDialog;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

public class LevelUpManager {
    public static void request() {
        GameView.view.post(() -> {
            new AlertDialog.Builder(GameView.view.getContext())
                    .setTitle("레벨업!")
                    .setMessage("강화를 선택하세요")
                    .setCancelable(false)
                    .setPositiveButton("공격력 +1", (d, w) -> PlayerStats.upgradeAttack())
                    .setNegativeButton("이동속도 +10%", (d, w) -> PlayerStats.upgradeSpeed())
                    .setNeutralButton("체력 +2", (d, w) -> PlayerStats.upgradeHp())
                    .show();
        });
    }
}
