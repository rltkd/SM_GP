package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.gmail.ge.and.rltkd0101.smgpproject.R;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IBoxCollidable;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.objects.AnimSprite;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

public class Player extends AnimSprite implements IBoxCollidable {
    private float hp = 100f;
    private static final float MAX_HP = 100f;

    public float getHpRatio() {
        return hp / MAX_HP;
    }

    public int getHp() {
        return (int) hp; // UI 표기용
    }

    public float getHpRaw() {
        return hp; // 내부 계산 또는 이펙트용
    }

    public float getMaxHp() {
        return MAX_HP;
    }

    public enum WeaponType {
        SWORD,
        HANDGUN
    }

    private float dx = 0, dy = 0;
    private float speed = 300f;
    private boolean facingLeft = false;
    private WeaponType weaponType;


    // 초당 최대 데미지 제한을 위한 필드
    private float damageTimer = 0f;
    private int damageThisSecond = 0;
    private static final int MAX_DAMAGE_PER_SECOND = 6;

    public Player(WeaponType weaponType) {
        super(R.mipmap.sword_attack_sheet, 8f, 3); // 3프레임, 초당 8fps
        this.weaponType = weaponType;
        setPosition(1500f, 1000f, 150f, 150f);
    }

    @Override
    public void update() {
        float distance = speed * GameView.frameTime;
        x += dx * distance;
        y += dy * distance;

        // 이동 제한
        float halfW = width / 2f;
        float halfH = height / 2f;
        x = Math.max(halfW, Math.min(x, 3000f - halfW));
        y = Math.max(halfH, Math.min(y, 2000f - halfH));
        setPosition(x, y, width, height);

        // 초당 데미지 누적 초기화
        damageTimer += GameView.frameTime;
        if (damageTimer >= 1.0f) {
            damageTimer = 0f;
            damageThisSecond = 0;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        int frameIndex = Math.round(time * fps) % frameCount;
        srcRect.set(frameIndex * frameWidth, 0, (frameIndex + 1) * frameWidth, frameHeight);

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

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public RectF getAttackBox() {
        if (weaponType == WeaponType.HANDGUN) {
            return new RectF(0, 0, 0, 0); // or return null; and null 체크 필요
        }

        // 근접 무기 (SWORD)의 공격 범위
        float w = 90f;
        float h = 40f;
        float overlap = 30f; // 히트박스와 겹치게 보정

        if (facingLeft) {
            return new RectF(
                    x - width / 2 - w + overlap, y - h / 2,
                    x - width / 2 + overlap, y + h / 2
            );
        } else {
            return new RectF(
                    x + width / 2 - overlap, y - h / 2,
                    x + width / 2 + w - overlap, y + h / 2
            );
        }
    }


    public RectF getHitBox() {
        float scale = 0.7f; // 히트박스를 60% 크기로 줄임
        float w = width * scale;
        float h = height * scale;

        return new RectF(
                x - w / 2, y - h / 2,
                x + w / 2, y + h / 2
        );
    }


    @Override
    public RectF getCollisionRect() {
        return getHitBox();
    }

    public void takeDamage(float damage) {
        if (damageThisSecond + damage > MAX_DAMAGE_PER_SECOND) return;

        hp -= damage;
        damageThisSecond += damage;

        if (hp < 0f) hp = 0f;

        System.out.println("Player hit! HP: " + (int) hp);

        if (hp <= 0f) {
            System.out.println("Player died!");
            // TODO: Game Over 처리
        }
    }


    public void reset() {
        hp = getMaxHp();
        damageTimer = 0f;
        damageThisSecond = 0;
    }
}
