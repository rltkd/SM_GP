package com.gmail.ge.and.rltkd0101.smgpproject.app;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IGameObject;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.Metrics;

import java.util.Random;

public class EnemySpawner implements IGameObject {
    private float timer = 0f;
    private float spawnInterval = 2.0f; // 2초마다 적 생성
    private Player player;
    private final Random random = new Random();
    private static final Enemy.EnemyType[] TYPES = Enemy.EnemyType.values();

    public EnemySpawner(Player player) {
        this.player = player;
    }

    @Override
    public void update() {
        timer += GameView.frameTime;
        if (timer >= spawnInterval) {
            timer -= spawnInterval;

            float spawnX = random.nextInt((int) Metrics.width);
            float spawnY = random.nextInt((int) Metrics.height);

            // 플레이어 근처에서 스폰되지 않게 최소 거리 제한 (예: 200px)
            float dx = spawnX - player.getX();
            float dy = spawnY - player.getY();
            float distance = (float)Math.sqrt(dx * dx + dy * dy);
            if (distance < 200f) return; // 너무 가까우면 스폰 취소

            Enemy enemy = Scene.top().getRecyclable(Enemy.class);
            if (enemy == null) {
                enemy = new Enemy();
            }

            Enemy.EnemyType type = TYPES[random.nextInt(TYPES.length)];
            enemy.revive(spawnX, spawnY, player, type);
            Scene.top().add(MainScene.Layer.enemy, enemy);
        }
    }

    @Override
    public void draw(android.graphics.Canvas canvas) {
        // EnemySpawner는 그릴 대상이 아님/컴파일 오류 방지
    }
}
