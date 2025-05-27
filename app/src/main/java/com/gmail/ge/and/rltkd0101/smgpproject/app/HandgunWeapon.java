package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.RectF;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IGameObject;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

public class HandgunWeapon implements Weapon {
    private static final float COOLDOWN = 0.3f; // 쿨타임 0.3초
    private float timeSinceLastShot = 0f;

    @Override
    public void attack(Player player, Scene scene) {
        timeSinceLastShot += GameView.frameTime;
        if (timeSinceLastShot < COOLDOWN) return;

        timeSinceLastShot = 0f;

        float x = player.getX();
        float y = player.getY();
        float dx = player.isFacingLeft() ? -1f : 1f;
        float dy = 0f;

        Bullet bullet = new Bullet(x, y, dx, dy, player.getDamage());
        scene.add(MainScene.Layer.bullet, (IGameObject) bullet);  // Bullet 레이어에 추가
    }

    @Override
    public RectF getAttackBox(Player player) {
        return null; // 원거리 무기는 사용하지 않음
    }
}
