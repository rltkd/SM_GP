package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.RectF;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;

public interface Weapon {
    void attack(Player player, Scene scene);
    RectF getAttackBox(Player player);
    int getSpriteResId();      // 무기별 애니메이션 시트
    float getBaseDamage();     // 무기별 공격력
    float getCooldown();       // 무기 쿨다운

    int getFrameCount(); // 🔄 무기별 애니메이션 프레임 수
}

