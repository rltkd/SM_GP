package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IBoxCollidable;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.objects.AnimSprite;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

public class Player extends AnimSprite implements IBoxCollidable {
    private float hp = 100f;
    private static final float MAX_HP = 100f;

    private float dx = 0, dy = 0;
    private boolean facingLeft = false;

    private float damageTimer = 0f;
    private int damageThisSecond = 0;
    private static final int MAX_DAMAGE_PER_SECOND = 6;

    private final Weapon weapon;

    private float attackCooldown = 0f;
    private float animationTimer = 0f;
    private boolean isAttacking = false;
    private int currentFrame = 0;

    private float actuatorX = 0f;
    private float actuatorY = 0f;

    public void setActuator(float ax, float ay) {
        this.actuatorX = ax;
        this.actuatorY = ay;
        if (Math.abs(ax) > 0.01f) {
            this.facingLeft = ax < 0;
        }
    }

    public Player(Weapon weapon) {
        super(weapon.getSpriteResId(), 0f, weapon.getFrameCount()); // fps 무시
        this.weapon = weapon;
        setPosition(1500f, 1000f, 150f, 150f);
    }

    @Override
    public void update() {
        updateMovement();
        clampPosition();
        updateDamageCooldown();
        updateAttackLogic();
        updateAnimationFrame();
    }

    private void updateAttackLogic() {
        if (weapon == null) return;

        float cooldown = weapon.getCooldown();

        if (attackCooldown > 0f) {
            attackCooldown -= GameView.frameTime;
            return;
        }

        weapon.attack(this, Scene.top());
        attackCooldown = cooldown;

        isAttacking = true;
        animationTimer = 0f;
        currentFrame = 0;
    }

    private void updateAnimationFrame() {
        if (!isAttacking || weapon == null) {
            currentFrame = 0;
            return;
        }

        int frameCount = weapon.getFrameCount();
        float frameDuration = weapon.getCooldown() / frameCount;

        animationTimer += GameView.frameTime;
        currentFrame = (int)(animationTimer / frameDuration);

        if (currentFrame >= frameCount) {
            currentFrame = frameCount - 1;
            isAttacking = false;
        }
    }

    private void updateMovement() {
        float speed = 200f;
        float distance = speed * GameView.frameTime;
        x += actuatorX * distance;
        y += actuatorY * distance;
    }

    private void clampPosition() {
        float halfW = width / 2f;
        float halfH = height / 2f;
        x = Math.max(halfW, Math.min(x, 3000f - halfW));
        y = Math.max(halfH, Math.min(y, 2000f - halfH));
        setPosition(x, y, width, height);
    }

    private void updateDamageCooldown() {
        damageTimer += GameView.frameTime;
        if (damageTimer >= 1.0f) {
            damageTimer = 0f;
            damageThisSecond = 0;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        int frameWidth = bitmap.getWidth() / frameCount;
        srcRect.set(currentFrame * frameWidth, 0, (currentFrame + 1) * frameWidth, frameHeight);

        dstRect.offset(-GameView.offsetX, -GameView.offsetY);

        if (facingLeft) {
            canvas.save();
            canvas.scale(-1, 1, x - GameView.offsetX, y - GameView.offsetY);
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
            canvas.restore();
        } else {
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
        }

        dstRect.offset(GameView.offsetX, GameView.offsetY);
    }

    public void setDirection(float dx, float dy) {
        float len = (float) Math.sqrt(dx * dx + dy * dy);
        if (len > 0) {
            this.dx = dx / len;
            this.dy = dy / len;
            this.facingLeft = dx < 0;
        } else {
            this.dx = this.dy = 0;
        }
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public boolean isFacingLeft() { return facingLeft; }

    public float getDamage() {
        return weapon != null ? weapon.getBaseDamage() : 0f;
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

    public void takeDamage(float damage) {
        if (damageThisSecond + damage > MAX_DAMAGE_PER_SECOND) return;

        hp -= damage;
        damageThisSecond += (int) damage;
        if (hp < 0f) hp = 0f;

        System.out.println("Player hit! HP: " + (int) hp);

        if (hp <= 0f) {
            System.out.println("Player died!");
            // TODO: Game Over 처리
        }
    }

    public float getHpRatio() {
        return hp / MAX_HP;
    }

    public int getHp() {
        return (int) hp;
    }

    public void reset() {
        hp = MAX_HP;
        damageTimer = 0f;
        damageThisSecond = 0;
    }

    public RectF getAttackBox() {
        return weapon != null ? weapon.getAttackBox(this) : new RectF(0, 0, 0, 0);
    }
}
