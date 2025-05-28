package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.RectF;

import com.gmail.ge.and.rltkd0101.smgpproject.R;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IGameObject;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.util.CollisionHelper;

import java.util.ArrayList;
import java.util.List;

public class SwordWeapon implements Weapon {
    public static final float BASE_DAMAGE = 1.0f;
    public static final float COOLDOWN = 1.0f;
    public static final int FRAME_COUNT = 3;

    @Override
    public void attack(Player player, Scene scene) {
        RectF box = getAttackBox(player);
        if (box == null || box.isEmpty()) return;

        List<IGameObject> enemies = new ArrayList<>(scene.objectsAt(MainScene.Layer.enemy)); // ✅ 복사본 사용
        for (IGameObject obj : enemies) {
            if (obj instanceof Enemy) {
                Enemy enemy = (Enemy) obj;
                if (enemy.isActive() && CollisionHelper.collides(enemy.getCollisionRect(), box)) {
                    enemy.hit(player.getDamage());
                }
            }
        }

    }

    @Override
    public RectF getAttackBox(Player player) {
        float x = player.getX();
        float y = player.getY();
        float width = player.getWidth();
        float height = player.getHeight();
        boolean facingLeft = player.isFacingLeft();

        float w = 90f;
        float h = 40f;
        float overlap = 30f;

        if (facingLeft) {
            return new RectF(
                    x - width / 2 - w + overlap, y - h / 2,
                    x - width / 2 + overlap, y + h / 2
            );
        } else {
            return new RectF(
                    x + width / 2 - overlap, y - h / 2,
                    x + width / 2 + w - overlap, y + h / 2
            );
        }
    }

    @Override
    public int getSpriteResId() {
        return R.mipmap.sword_attack_sheet;
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
