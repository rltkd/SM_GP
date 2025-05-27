package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.gmail.ge.and.rltkd0101.smgpproject.R;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IBoxCollidable;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IRecyclable;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.objects.Sprite;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

public class Enemy extends Sprite implements IRecyclable, IBoxCollidable {
    public enum EnemyType {
        SLIME, ZOMBIE, GHOST
    }

    private boolean active = true;
    private float speed;
    private Player target;
    private EnemyType type;
    private int hp;

    // 피격 쿨타임 + 넉백 관련
    private float hitCooldown = 0f;
    private static final float HIT_COOLDOWN_TIME = 0.3f;

    public Enemy() {
        super(R.mipmap.slime); // 기본 이미지
    }

    public void revive(float x, float y, Player target, EnemyType type) {
        this.active = true;
        this.target = target;
        this.type = type;

        switch (type) {
            case SLIME:
                setImageResourceId(R.mipmap.slime);
                this.speed = 100f;
                this.hp = 10;
                break;
         /*   case ZOMBIE:
                setImageResourceId(R.mipmap.zombie);
                this.speed = 60f;
                this.hp = 30;
                break;
            case GHOST:
                setImageResourceId(R.mipmap.ghost);
                this.speed = 140f;
                this.hp = 5;
                break;*/
        }

        setPosition(x, y, 64, 64);
    }

    @Override
    public void onRecycle() {
        this.active = false;
        this.target = null;
        this.hp = 0;
    }

    @Override
    public void update() {
        if (!isReadyToMove()) return;

        updateHitCooldown();
        moveTowardsTarget();
    }

    private boolean isReadyToMove() {
        return active && target != null;
    }

    private void updateHitCooldown() {
        if (hitCooldown > 0f) {
            hitCooldown -= GameView.frameTime;
        }
    }

    private void moveTowardsTarget() {
        float dx = target.getX() - x;
        float dy = target.getY() - y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        if (dist <= 1f) return;

        float normX = dx / dist;
        float normY = dy / dist;
        float move = speed * GameView.frameTime;

        x += normX * move;
        y += normY * move;

        setPosition(x, y, width, height);
    }

    @Override
    public void draw(Canvas canvas) {
        if (!active) return;
        super.draw(canvas);
    }

    public boolean isActive() {
        return active;
    }

    public EnemyType getType() {
        return type;
    }

    public boolean checkHit(RectF attackBox) {
        return RectF.intersects(attackBox, getHitBox());
    }

    public RectF getHitBox() {
        return new RectF(
                x - width / 2, y - height / 2,
                x + width / 2, y + height / 2
        );
    }

    @Override
    public RectF getCollisionRect() {
        return getHitBox();
    }

    public void hit(float damage) {
        if (hitCooldown > 0f) return;

        hp -= damage;
        hitCooldown = HIT_COOLDOWN_TIME;

        applyKnockbackFrom(target);

        if (hp <= 0) {
            Scene.top().remove(MainScene.Layer.enemy, this);
            Scene.top().collectRecyclable(this); // 재활용 통에 수동 수집

        }
    }

    private void applyKnockbackFrom(Player target) {
        if (target == null) return;

        float dx = x - target.getX();
        float dy = y - target.getY();
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        if (dist > 0.001f) {
            float knockback = 30f; // 넉백 거리
            dx = (dx / dist) * knockback;
            dy = (dy / dist) * knockback;

            x += dx;
            y += dy;

            setPosition(x, y, width, height);
        }
    }
}
