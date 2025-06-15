package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IGameObject;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IRecyclable;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.util.CollisionHelper;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bullet implements IGameObject, IRecyclable {
    private float x, y;
    private float dx, dy;
    private float speed = 1000f;
    private float radius = 10f;
    private float damage;

    private boolean active = true;
    private float distanceTraveled = 0f;
    private final float maxDistance = 2000f;

    private final Set<Enemy> hitEnemies = new HashSet<>(); // 중복 피격 방지용

    public Bullet(float x, float y, float dx, float dy, float damage) {
        init(x, y, dx, dy, damage);
    }

    @Override
    public void update() {
        if (!active) return;

        float dist = speed * GameView.frameTime;
        x += dx * dist;
        y += dy * dist;
        distanceTraveled += dist;

        RectF bulletRect = getCollisionRect();
        MainScene mainScene = (MainScene) Scene.top();
        List<IGameObject> enemies = new ArrayList<>(mainScene.objectsAt(MainScene.Layer.enemy));


        for (IGameObject obj : enemies) {
            if (!(obj instanceof Enemy)) continue;
            Enemy enemy = (Enemy) obj;
            if (!enemy.isActive()) continue;

            if (CollisionHelper.collides(bulletRect, enemy.getCollisionRect())) {
                if (!hitEnemies.contains(enemy)) {
                    enemy.hit(damage);
                    hitEnemies.add(enemy); // ✅ 중복 피격 방지
                }
            }
        }

        if (distanceTraveled > maxDistance) {
            deactivate();
        }
    }

    public void init(float x, float y, float dx, float dy, float damage) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.damage = damage;
        this.active = true;
        this.distanceTraveled = 0f;
        this.hitEnemies.clear(); // ✅ 재활용 시 초기화
    }

    @Override
    public void draw(Canvas canvas) {
        if (!active) return;

        Paint paint = new Paint();
        paint.setColor(0xFFFFFF00); // 노란색
        canvas.drawCircle(x - GameView.offsetX, y - GameView.offsetY, radius, paint);
    }

    @Override
    public void onRecycle() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        Scene.top().remove(MainScene.Layer.bullet, this);
    }

    public RectF getCollisionRect() {
        return new RectF(x - radius, y - radius, x + radius, y + radius);
    }
}
