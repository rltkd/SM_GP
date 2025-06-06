package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IBoxCollidable;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.objects.AnimSprite;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

public class Player extends AnimSprite implements IBoxCollidable {
    private float hp;
    private float maxHp;
    private float speed;

    private int exp = 0;
    private int level = 1;
    private int expToNextLevel = 100;

    private boolean facingLeft = false;
    private final Weapon weapon;

    private float attackCooldown = 0f;
    private float animationTimer = 0f;
    private boolean isAttacking = false;
    private int currentFrame = 0;

    private float actuatorX = 1f;
    private float actuatorY = 0f;

    private float damageTimer = 0f;
    private int damageThisSecond = 0;
    private static final int MAX_DAMAGE_PER_SECOND = 6;

    private float lastDirectionX = 1f;
    private float lastDirectionY = 0f;

    public Player(Weapon weapon) {
        super(weapon.getSpriteResId(), 0f, weapon.getFrameCount());
        this.weapon = weapon;
        this.maxHp = PlayerStats.maxHp;
        this.hp = maxHp;
        this.speed = PlayerStats.moveSpeed;
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

    public void setActuator(float ax, float ay) {
        this.actuatorX = ax;
        this.actuatorY = ay;

        if (Math.abs(ax) > 0.01f || Math.abs(ay) > 0.01f) {
            lastDirectionX = ax;
            lastDirectionY = ay;
            if (Math.abs(ax) > 0.01f) {
                this.facingLeft = ax < 0;
            }
        }
    }

    private void updateMovement() {
        this.speed = PlayerStats.moveSpeed; // 항상 최신값 반영
        float distance = speed * GameView.frameTime;
        x += actuatorX * distance;
        y += actuatorY * distance;
    }

    private void updateAttackLogic() {
        if (weapon == null) return;

        if (attackCooldown > 0f) {
            attackCooldown -= GameView.frameTime;
            return;
        }

        weapon.attack(this, Scene.top());
        attackCooldown = weapon.getCooldown();

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

    public float getDamage() {
        return PlayerStats.attack;
    }

    public void takeDamage(float damage) {
        if (damageThisSecond + damage > MAX_DAMAGE_PER_SECOND) return;

        hp -= damage;
        damageThisSecond += (int) damage;
        if (hp < 0f) hp = 0f;

        if (hp <= 0f) {
            System.out.println("Player died!");
            // TODO: Game Over 처리
        }
    }

    public void reset() {
        this.maxHp = PlayerStats.maxHp;
        this.hp = maxHp;
        damageTimer = 0f;
        damageThisSecond = 0;
    }

    public void gainExp(int amount) {
        exp += amount;
        while (exp >= expToNextLevel) {
            exp -= expToNextLevel;
            level++;
            expToNextLevel = 100 + (level - 1) * 50;

            GameView.view.pauseGame();      // 게임 일시정지
            LevelUpManager.request();       // Alert 띄우기
        }
    }

    public int getExp() { return exp; }
    public int getExpToNextLevel() { return expToNextLevel; }

    public float getHpRatio() { return hp / maxHp; }
    public int getHp() { return (int) hp; }

    public RectF getHitBox() {
        float scale = 0.7f;
        float w = width * scale;
        float h = height * scale;
        return new RectF(x - w / 2, y - h / 2, x + w / 2, y + h / 2);
    }

    public RectF getCollisionRect() { return getHitBox(); }

    public RectF getAttackBox() {
        return weapon != null ? weapon.getAttackBox(this) : new RectF(0, 0, 0, 0);
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public boolean isFacingLeft() { return facingLeft; }
    public float getLastDirectionX() { return lastDirectionX; }
    public float getLastDirectionY() { return lastDirectionY; }
}
