package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.RectF;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IGameObject;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

public class HandgunWeapon implements Weapon {
    private static final float COOLDOWN = 0.3f;
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

        Bullet bullet = Scene.top().getRecyclable(Bullet.class);
        if (bullet != null) {
            bullet.init(x, y, dx, dy, player.getDamage());
            scene.add(MainScene.Layer.bullet, bullet); // ✅ 여기만 있으면 됨
        }
    }

    @Override
    public RectF getAttackBox(Player player) {
        return null;
    }
}
