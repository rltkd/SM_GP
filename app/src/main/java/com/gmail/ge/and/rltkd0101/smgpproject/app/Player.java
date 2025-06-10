package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IBoxCollidable;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.objects.AnimSprite;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

public class Player extends AnimSprite implements IBoxCollidable {
    private float hp;
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
        this.hp = PlayerStats.maxHp;
        setPosition(1500f, 1000f, 150f, 150f);
    }

    @Override
    public void update() {
        updateMovement();
        clampPosition();
        updateDamageCooldown();
        updateAttackLogic();
        updateAnimationFrame();
        applyPassiveHeal();
    }

    private void updateMovement() {
        float distance = PlayerStats.moveSpeed * GameView.frameTime;
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

    private void applyPassiveHeal() {
        if (PlayerStats.healPerSec > 0f) {
            hp += PlayerStats.healPerSec * GameView.frameTime;
            if (hp > PlayerStats.maxHp) hp = PlayerStats.maxHp;
        }
    }

    public void healOnKill() {
        hp += PlayerStats.healOnKill;
        if (hp > PlayerStats.maxHp) hp = PlayerStats.maxHp;
    }

    public void gainExp(int amount) {
        exp += amount;
        while (exp >= expToNextLevel) {
            exp -= expToNextLevel;
            level++;
            expToNextLevel = 100 + (level - 1) * 50;

            GameView.view.pauseGame();      // 게임 일시정지
            LevelUpManager.request();       // 강화창 알림
        }
    }

    public void takeDamage(float rawDamage) {
        if (damageThisSecond >= MAX_DAMAGE_PER_SECOND) return;

        float reduced = Math.max(0, rawDamage - PlayerStats.defense);
        hp -= reduced;
        damageThisSecond += reduced;

        if (hp <= 0f) {
            hp = 0f;
            // TODO: 게임 오버 처리
        }
    }

    public void reset() {
        this.hp = PlayerStats.maxHp;
        this.exp = 0;
        this.level = 1;
        this.expToNextLevel = 100;
        this.damageTimer = 0f;
        this.damageThisSecond = 0;
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

    public float getHpRatio() { return hp / PlayerStats.maxHp; }
    public int getHp() { return (int) hp; }
    public float getDamage() { return PlayerStats.attack; }
    public float getX() { return x; }
    public float getY() { return y; }
    public boolean isFacingLeft() { return facingLeft; }
    public float getLastDirectionX() { return lastDirectionX; }
    public float getLastDirectionY() { return lastDirectionY; }
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
}
