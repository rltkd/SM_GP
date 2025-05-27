package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.RectF;
import android.util.Log;

import com.gmail.ge.and.rltkd0101.smgpproject.R;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

public class HandgunWeapon implements Weapon {
    private static final float COOLDOWN = 0.3f;
    private static final float BASE_DAMAGE = 2.0f;
    private static final int FRAME_COUNT = 2;
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
        if (bullet == null) {
            bullet = new Bullet(x, y, dx, dy, player.getDamage()); // 새로 생성
        } else {
            bullet.init(x, y, dx, dy, player.getDamage());         // 재사용 초기화
        }
        Log.d("Weapon", "Trying to add Bullet");
        scene.add(MainScene.Layer.bullet, bullet);
        Log.d("Weapon", "Bullet added");
    }

    @Override
    public RectF getAttackBox(Player player) {
        return null;
    }

    @Override
    public int getSpriteResId() {
        return R.mipmap.handgun_attack_sheet;
    }

    @Override
    public float getBaseDamage() {
        return BASE_DAMAGE;
    }

    @Override
    public float getCooldown() {
        return COOLDOWN;
    }

    @Override
    public int getFrameCount() {
        return FRAME_COUNT;
    }
}
