package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.gmail.ge.and.rltkd0101.smgpproject.R;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IBoxCollidable;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.objects.AnimSprite;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

public class Player extends AnimSprite implements IBoxCollidable {
    public enum WeaponType {
        SWORD,
        HANDGUN
    }

    private float dx = 0, dy = 0;
    private float speed = 300f;
    private boolean facingLeft = false;
    private WeaponType weaponType;

    private int hp = 100;

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

        // 이동 경계 제한
        float halfW = width / 2f;
        float halfH = height / 2f;
        x = Math.max(halfW, Math.min(x, 3000f - halfW));
        y = Math.max(halfH, Math.min(y, 2000f - halfH));

        setPosition(x, y, width, height);
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

    // 공격 범위: 방향 기준 앞쪽 사각형
    public RectF getAttackBox() {
        float w = 60f;
        float h = 40f;
        if (facingLeft) {
            return new RectF(
                    x - width / 2 - w, y - h / 2,
                    x - width / 2, y + h / 2
            );
        } else {
            return new RectF(
                    x + width / 2, y - h / 2,
                    x + width / 2 + w, y + h / 2
            );
        }
    }

    // 피격 범위: 몸 전체
    public RectF getHitBox() {
        return new RectF(
                x - width / 2, y - height / 2,
                x + width / 2, y + height / 2
        );
    }

    // 충돌 시스템 통합용
    @Override
    public RectF getCollisionRect() {
        return getHitBox();
    }

    public void takeDamage() {
        hp--;
        System.out.println("Player hit! HP: " + hp);

        if (hp <= 0) {
            System.out.println("Player died!");
            // TODO: 게임 오버 처리 (씬 변경, 재시작 등)
        }
    }
}
