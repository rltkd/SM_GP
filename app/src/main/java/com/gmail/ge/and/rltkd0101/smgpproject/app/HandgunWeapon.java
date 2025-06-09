package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.RectF;
import com.gmail.ge.and.rltkd0101.smgpproject.R;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;

public class HandgunWeapon implements Weapon {
    private static final float COOLDOWN = 0.5f;
    private static final float BASE_DAMAGE = 0.5f;
    private static final int FRAME_COUNT = 2;

    @Override
    public void attack(Player player, Scene scene) {
        float dirX = player.getLastDirectionX();
        float dirY = player.getLastDirectionY();

// 완전한 0이면 lastDirection 사용
        if (Math.abs(dirX) < 0.001f && Math.abs(dirY) < 0.001f) {
            dirX = player.getLastDirectionX();
            dirY = player.getLastDirectionY();
        }

        // 방향 정규화
        float length = (float) Math.sqrt(dirX * dirX + dirY * dirY);
        dirX /= length;
        dirY /= length;

        float x = player.getX();
        float y = player.getY();

        Bullet bullet = Scene.top().getRecyclable(Bullet.class);
        if (bullet == null) {
            bullet = new Bullet(x, y, dirX, dirY, player.getDamage());
        } else {
            bullet.init(x, y, dirX, dirY, player.getDamage());
        }

        scene.add(MainScene.Layer.bullet, bullet);
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
