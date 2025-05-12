package com.gmail.ge.and.rltkd0101.smgpproject.app;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IGameObject;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

import java.util.Random;

public class EnemySpawner implements IGameObject {
    private float timer = 0f;
    private float spawnInterval = 2.0f; // 2초마다 적 생성
    private Player player;
    private Random random = new Random();

    public EnemySpawner(Player player) {
        this.player = player;
    }

    @Override
    public void update() {
        timer += GameView.frameTime;
        if (timer >= spawnInterval) {
            timer -= spawnInterval;

            float spawnX = random.nextInt(1600); // 맵 가로 범위에 따라 조정
            float spawnY = random.nextInt(900);

            Enemy enemy = Scene.top().getRecyclable(Enemy.class);
            if (enemy == null) enemy = new Enemy();

            // 타입 무작위 선택
            Enemy.EnemyType type = Enemy.EnemyType.values()[random.nextInt(3)];
            enemy.revive(spawnX, spawnY, player, type);
            Scene.top().add(MainScene.Layer.enemy, enemy);
        }
    }

    @Override
    public void draw(android.graphics.Canvas canvas) {
        // 이 오브젝트는 그릴 게 없음
    }
}
