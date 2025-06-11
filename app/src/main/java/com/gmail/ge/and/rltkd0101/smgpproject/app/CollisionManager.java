package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.RectF;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IGameObject;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.util.CollisionHelper;

import java.util.List;
import java.util.stream.Collectors;

public class CollisionManager {

    public static void handlePlayerAttack(Scene scene, Player player) {
        RectF attackBox = player.getAttackBox();
        if (attackBox == null || attackBox.isEmpty()) return; // ✅ null 또는 0x0 체크

        for (Enemy enemy : getActiveEnemies(scene)) {
            if (CollisionHelper.collides(enemy.getCollisionRect(), attackBox)) {
                enemy.hit(player.getDamage());
            }
        }
    }


    public static void handleEnemyCollision(Scene scene, Player player) {
        for (Enemy enemy : getActiveEnemies(scene)) {
            if (CollisionHelper.collides(enemy, player)) {
                if (enemy.canDamagePlayer()) {
                    player.takeDamage(enemy.getDamage());
                    enemy.resetDamageCooldown();
                }
            }

            enemy.updateDamageCooldown(); // 쿨타임 감소
        }
    }

    private static List<IGameObject> getEnemyObjects(Scene scene) {
        return scene.objectsAt(MainScene.Layer.enemy);
    }

    private static List<Enemy> getActiveEnemies(Scene scene) {
        return getEnemyObjects(scene).stream()
                .filter(obj -> obj instanceof Enemy)
                .map(obj -> (Enemy) obj)
                .filter(Enemy::isActive)
                .collect(Collectors.toList()); // ✅ Android 호환 방식
    }

}

