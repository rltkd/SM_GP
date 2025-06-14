package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IGameObject;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

import java.util.Random;

public class EnemySpawner implements IGameObject {
    private float timer = 0f;
    private final float spawnInterval = 2.0f;

    private float scalingTimer = 0f;
    private static final float SCALING_INTERVAL = 10f;
    private static final float SCALING_RATE = 1.1f;

    private float hpMultiplier = 1.0f;
    private float damageMultiplier = 1.0f;

    private int maxSpawnCount = 5;
    private static final int MAX_SPAWN_LIMIT = 30;

    private final Player player;
    private final Random random = new Random();

    private static final float MAP_WIDTH = 3000f;
    private static final float MAP_HEIGHT = 2000f;
    private static final float OFFSET_FROM_PLAYER = 500f;
    private static final float SPAWN_MARGIN = 100f;

    public EnemySpawner(Player player) {
        this.player = player;
    }

    @Override
    public void update() {
        float frameTime = GameView.frameTime;
        timer += frameTime;
        scalingTimer += frameTime;

        if (scalingTimer >= SCALING_INTERVAL) {
            scalingTimer -= SCALING_INTERVAL;
            hpMultiplier *= SCALING_RATE;
            damageMultiplier *= SCALING_RATE;
            if (maxSpawnCount < MAX_SPAWN_LIMIT) {
                maxSpawnCount++;
            }
        }

        if (timer >= spawnInterval) {
            timer -= spawnInterval;

            int spawnCount = random.nextInt(maxSpawnCount + 1); // 0 ~ maxSpawnCount
            for (int i = 0; i < spawnCount; i++) {
                float[] pos = generateSpawnPosition();
                float spawnX = pos[0], spawnY = pos[1];

                Enemy enemy = Scene.top().getRecyclable(Enemy.class);
                if (enemy == null) enemy = new Enemy();

                Enemy.EnemyType type = chooseEnemyType();
                enemy.revive(spawnX, spawnY, player, type, hpMultiplier, damageMultiplier);
                Scene.top().add(MainScene.Layer.enemy, enemy);
            }
        }
    }

    private float[] generateSpawnPosition() {
        float px = player.getX();
        float py = player.getY();

        int direction = random.nextInt(4); // 0: 위, 1: 아래, 2: 왼쪽, 3: 오른쪽
        float dx = 0f, dy = 0f;

        switch (direction) {
            case 0: dy = -1f; break;
            case 1: dy = 1f; break;
            case 2: dx = -1f; break;
            case 3: dx = 1f; break;
        }

        float spawnX = clamp(px + dx * (OFFSET_FROM_PLAYER + random.nextFloat() * SPAWN_MARGIN), 0f, MAP_WIDTH);
        float spawnY = clamp(py + dy * (OFFSET_FROM_PLAYER + random.nextFloat() * SPAWN_MARGIN), 0f, MAP_HEIGHT);

        return new float[] { spawnX, spawnY };
    }

    private Enemy.EnemyType chooseEnemyType() {
        int roll = random.nextInt(100);
        if (roll < 5) return Enemy.EnemyType.ZOMBIE;     // 5%
        if (roll < 25) return Enemy.EnemyType.GHOST;     // 20%
        return Enemy.EnemyType.SLIME;                    // 75%
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }

    @Override
    public void draw(Canvas canvas) {
        // EnemySpawner는 그릴 요소 없음
    }
}
