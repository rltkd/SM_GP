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

import java.util.List;

public class Bullet implements IGameObject, IRecyclable {
    private float x, y;
    private float dx, dy;
    private float speed = 1000f;
    private float radius = 10f;
    private float damage;

    private boolean active = true;
    private float distanceTraveled = 0f;
    private final float maxDistance = 2000f;

    public Bullet(float x, float y, float dx, float dy, float damage) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.damage = damage;
    }

    @Override
    public void update() {
        if (!active) return;

        float dist = speed * GameView.frameTime;
        x += dx * dist;
        y += dy * dist;
        distanceTraveled += dist;

        // 몬스터와 충돌 처리
        RectF bulletRect = getCollisionRect();
        MainScene mainScene = (MainScene) Scene.top(); // ✅ Scene.top() 사용
        List<IGameObject> enemies = mainScene.objectsAt(MainScene.Layer.enemy);

        for (IGameObject obj : enemies) {
            if (!(obj instanceof Enemy)) continue;
            Enemy enemy = (Enemy) obj;
            if (!enemy.isActive()) continue;

            if (CollisionHelper.collides(bulletRect, enemy.getCollisionRect())) {
                enemy.hit(damage);
                deactivate();
                return;
            }
        }

        // 사거리 초과 시 비활성화
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
        this.active = true;     // ❗반드시 재활성화
        this.distanceTraveled = 0f;
    }

    @Override
    public void draw(Canvas canvas) {
        if (!active) {
            Log.d("Bullet", "draw() skipped: not active");
            return;
        }

        Paint paint = new Paint();
        paint.setColor(0xFFFFFF00); // 노란색
        canvas.drawCircle(x - GameView.offsetX, y - GameView.offsetY, radius, paint);
        Log.d("Bullet", "draw(): x=" + x + ", y=" + y);
    }

    @Override
    public void onRecycle() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        onRecycle(); // 호출로 명확하게 처리
    }

    public RectF getCollisionRect() {
        return new RectF(x - radius, y - radius, x + radius, y + radius);
    }
}
