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
    private float damageCooldown = 0f;

    public void updateDamageCooldown() {
        if (damageCooldown > 0f) {
            damageCooldown -= GameView.frameTime;
        }
    }

    public enum EnemyType {
        SLIME, ZOMBIE, GHOST
    }

    private float scale = 1.0f;
    private final float baseWidth = 64f;
    private final float baseHeight = 64f;

    private boolean active = true;
    private float speed;
    private Player target;
    private EnemyType type;
    private float hp;
    private float damage;

    private float hitCooldown = 0f;
    private static final float HIT_COOLDOWN_TIME = 0.3f;

    public Enemy() {
        super(R.mipmap.slime);
    }

    public void setScale(float scale) {
        this.scale = scale;
        setPosition(x, y, baseWidth * scale, baseHeight * scale);
    }

    public void revive(float x, float y, Player target, EnemyType type, float hpMultiplier, float dmgMultiplier) {
        this.active = true;
        this.target = target;
        this.type = type;

        switch (type) {
            case SLIME:
                setImageResourceId(R.mipmap.slime);
                this.speed = 30f;
                this.hp = 2f * hpMultiplier;
                this.damage = 2f * dmgMultiplier;
                setScale(1.0f);
                break;
            case ZOMBIE:
                setImageResourceId(R.mipmap.zombie);
                this.speed = 20f;
                this.hp = 4f * hpMultiplier;
                this.damage = 3f * dmgMultiplier;
                setScale(2.0f);
                break;
            case GHOST:
                setImageResourceId(R.mipmap.ghost);
                this.speed = 50f;
                this.hp = 1f * hpMultiplier;
                this.damage = 1f * dmgMultiplier;
                setScale(0.6f);
                break;
        }

        setPosition(x, y, width, height);
    }

    public float getDamage() {
        return damage;
    }

    public boolean canDamagePlayer() {
        return damageCooldown <= 0f;
    }

    public void resetDamageCooldown() {
        damageCooldown = 1.0f;
    }

    @Override
    public void onRecycle() {
        this.active = false;
        this.target = null;
        this.hp = 0;
        this.speed = 0f;
        this.scale = 1.0f;
        this.type = null;
        this.damage = 0f;
    }

    @Override
    public void update() {
        if (!isReadyToMove()) return;

        updateHitCooldown();
        moveTowardsTarget();

        if (type == EnemyType.GHOST) {
            resolveOverlapWithPlayer();
        }
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

    private void resolveOverlapWithPlayer() {
        float overlapDistance = 10f;
        float dx = x - target.getX();
        float dy = y - target.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        float minDistance = (this.width + target.getHitBox().width()) / 2f;

        if (distance < minDistance && distance > 0f) {
            float pushBack = (minDistance - distance) + overlapDistance;
            x += (dx / distance) * pushBack;
            y += (dy / distance) * pushBack;
            setPosition(x, y, width, height);
        }
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
        float scale = 0.7f;
        float w = width * scale;
        float h = height * scale;
        return new RectF(x - w / 2, y - h / 2, x + w / 2, y + h / 2);
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
            if (target instanceof Player) {
                ((Player) target).gainExp(30);
                ((Player) target).healOnKill();
            }

            Scene.top().remove(MainScene.Layer.enemy, this);
            Scene.top().collectRecyclable(this);
        }
    }

    private void applyKnockbackFrom(Player target) {
        if (target == null) return;

        float dx = x - target.getX();
        float dy = y - target.getY();
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        if (dist > 0.001f) {
            float knockback = 50f;
            dx = (dx / dist) * knockback;
            dy = (dy / dist) * knockback;

            x += dx;
            y += dy;

            setPosition(x, y, width, height);
        }
    }
}
