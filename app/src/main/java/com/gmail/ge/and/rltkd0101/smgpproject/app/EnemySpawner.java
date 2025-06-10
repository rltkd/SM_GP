package com.gmail.ge.and.rltkd0101.smgpproject.app;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IGameObject;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

import java.util.Random;

public class EnemySpawner implements IGameObject {
    private float timer = 0f;
    private final float spawnInterval = 1.0f;
    private float zombieTimer = 0f;
    private final float zombieSpawnInterval = 15.0f;

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
        zombieTimer += frameTime;
        scalingTimer += frameTime;

        if (scalingTimer >= SCALING_INTERVAL) {
            scalingTimer -= SCALING_INTERVAL;
            hpMultiplier *= SCALING_RATE;
            damageMultiplier *= SCALING_RATE;
            if (maxSpawnCount < MAX_SPAWN_LIMIT) {
                maxSpawnCount += 1;
            }
        }

        if (timer >= spawnInterval) {
            timer -= spawnInterval;

            int spawnCount = random.nextInt(maxSpawnCount + 1); // 0 ~ maxSpawnCount
            for (int i = 0; i < spawnCount; i++) {
                float spawnX, spawnY;
                float px = player.getX();
                float py = player.getY();

                int side = random.nextInt(4);
                switch (side) {
                    case 0:
                        spawnX = clamp(random.nextFloat() * MAP_WIDTH, 0f, MAP_WIDTH);
                        spawnY = py - OFFSET_FROM_PLAYER - random.nextFloat() * SPAWN_MARGIN;
                        break;
                    case 1:
                        spawnX = clamp(random.nextFloat() * MAP_WIDTH, 0f, MAP_WIDTH);
                        spawnY = py + OFFSET_FROM_PLAYER + random.nextFloat() * SPAWN_MARGIN;
                        break;
                    case 2:
                        spawnX = px - OFFSET_FROM_PLAYER - random.nextFloat() * SPAWN_MARGIN;
                        spawnY = clamp(random.nextFloat() * MAP_HEIGHT, 0f, MAP_HEIGHT);
                        break;
                    default:
                        spawnX = px + OFFSET_FROM_PLAYER + random.nextFloat() * SPAWN_MARGIN;
                        spawnY = clamp(random.nextFloat() * MAP_HEIGHT, 0f, MAP_HEIGHT);
                        break;
                }

                Enemy enemy = Scene.top().getRecyclable(Enemy.class);
                if (enemy == null) enemy = new Enemy();

                Enemy.EnemyType type = (zombieTimer >= zombieSpawnInterval)
                        ? Enemy.EnemyType.ZOMBIE
                        : (random.nextBoolean() ? Enemy.EnemyType.SLIME : Enemy.EnemyType.GHOST);

                if (type == Enemy.EnemyType.ZOMBIE) zombieTimer = 0f;

                enemy.revive(spawnX, spawnY, player, type, hpMultiplier, damageMultiplier);
                Scene.top().add(MainScene.Layer.enemy, enemy);
            }
        }
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }

    @Override
    public void draw(android.graphics.Canvas canvas) {
        // EnemySpawner는 그릴 요소 없음
    }
}
