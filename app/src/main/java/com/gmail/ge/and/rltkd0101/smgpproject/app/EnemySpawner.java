package com.gmail.ge.and.rltkd0101.smgpproject.app;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IGameObject;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

import java.util.Random;

public class EnemySpawner implements IGameObject {
    private float timer = 0f;
    private final float spawnInterval = 2.0f;
    private float zombieTimer = 0f;
    private final float zombieSpawnInterval = 15.0f;

    private final Player player;
    private final Random random = new Random();

    // 전체 맵 크기 정의
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

        if (timer >= spawnInterval) {
            timer -= spawnInterval;

            float spawnX, spawnY;
            float px = player.getX();
            float py = player.getY();

            int side = random.nextInt(4); // 0: top, 1: bottom, 2: left, 3: right

            switch (side) {
                case 0: // 상단
                    spawnX = clamp(random.nextFloat() * MAP_WIDTH, 0f, MAP_WIDTH);
                    spawnY = py - OFFSET_FROM_PLAYER - random.nextFloat() * SPAWN_MARGIN;
                    break;
                case 1: // 하단
                    spawnX = clamp(random.nextFloat() * MAP_WIDTH, 0f, MAP_WIDTH);
                    spawnY = py + OFFSET_FROM_PLAYER + random.nextFloat() * SPAWN_MARGIN;
                    break;
                case 2: // 좌측
                    spawnX = px - OFFSET_FROM_PLAYER - random.nextFloat() * SPAWN_MARGIN;
                    spawnY = clamp(random.nextFloat() * MAP_HEIGHT, 0f, MAP_HEIGHT);
                    break;
                case 3: // 우측
                default:
                    spawnX = px + OFFSET_FROM_PLAYER + random.nextFloat() * SPAWN_MARGIN;
                    spawnY = clamp(random.nextFloat() * MAP_HEIGHT, 0f, MAP_HEIGHT);
                    break;
            }

            // Enemy 생성
            Enemy enemy = Scene.top().getRecyclable(Enemy.class);
            if (enemy == null) {
                enemy = new Enemy();
            }

            Enemy.EnemyType type;
            if (zombieTimer >= zombieSpawnInterval) {
                zombieTimer = 0f;
                type = Enemy.EnemyType.ZOMBIE;
            } else {
                type = random.nextBoolean() ? Enemy.EnemyType.SLIME : Enemy.EnemyType.GHOST;
            }

            enemy.revive(spawnX, spawnY, player, type);
            Scene.top().add(MainScene.Layer.enemy, enemy);
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
