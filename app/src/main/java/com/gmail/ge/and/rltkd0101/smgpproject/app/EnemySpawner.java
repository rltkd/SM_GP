package com.gmail.ge.and.rltkd0101.smgpproject.app;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IGameObject;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.Metrics;

import java.util.Random;

public class EnemySpawner implements IGameObject {
    private float timer = 0f;
    private final float spawnInterval = 2.0f; // 기본 스폰 간격
    private float zombieTimer = 0f;
    private final float zombieSpawnInterval = 15.0f; // 좀비는 15초마다 등장

    private final Player player;
    private final Random random = new Random();

    public EnemySpawner(Player player) {
        this.player = player;
    }

    @Override
    public void update() {
        float frameTime = GameView.frameTime;
        timer += frameTime;
        zombieTimer += frameTime;

        if (timer >= spawnInterval) {
            timer -= spawnInterval;

            float spawnX = random.nextInt((int) Metrics.width);
            float spawnY = random.nextInt((int) Metrics.height);

            float dx = spawnX - player.getX();
            float dy = spawnY - player.getY();
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance < 200f) return; // 플레이어 근처 스폰 방지

            Enemy enemy = Scene.top().getRecyclable(Enemy.class);
            if (enemy == null) {
                enemy = new Enemy();
            }

            // 좀비는 별도 타이머 기준
            Enemy.EnemyType type;
            if (zombieTimer >= zombieSpawnInterval) {
                zombieTimer = 0f;
                type = Enemy.EnemyType.ZOMBIE;
            } else {
                // SLIME 또는 GHOST 랜덤 선택
                type = random.nextBoolean() ? Enemy.EnemyType.SLIME : Enemy.EnemyType.GHOST;
            }

            enemy.revive(spawnX, spawnY, player, type);
            Scene.top().add(MainScene.Layer.enemy, enemy);
        }
    }

    @Override
    public void draw(android.graphics.Canvas canvas) {
        // EnemySpawner는 그릴 요소 없음
    }
}
