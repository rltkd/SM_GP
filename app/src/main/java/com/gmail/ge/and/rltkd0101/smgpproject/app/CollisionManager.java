package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.RectF;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IGameObject;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;

import java.util.List;

public class CollisionManager {
    public static void handlePlayerAttack(Scene scene, Player player) {
        RectF attackBox = player.getAttackBox();

        List<IGameObject> enemies = scene.objectsAt(MainScene.Layer.enemy);

        for (IGameObject obj : enemies) {
            if (!(obj instanceof Enemy)) continue;
            Enemy enemy = (Enemy) obj;

            if (enemy.checkHit(attackBox)) {
                enemy.hit();
            }
        }
    }

    public static void handleEnemyCollision(Scene scene, Player player) {
        RectF playerHitBox = player.getHitBox();

        List<IGameObject> enemies = scene.objectsAt(MainScene.Layer.enemy);
        for (IGameObject obj : enemies) {
            if (!(obj instanceof Enemy)) continue;
            Enemy enemy = (Enemy) obj;

            if (RectF.intersects(enemy.getHitBox(), playerHitBox)) {
                player.takeDamage(); // TODO: implement damage system
            }
        }
    }
}
