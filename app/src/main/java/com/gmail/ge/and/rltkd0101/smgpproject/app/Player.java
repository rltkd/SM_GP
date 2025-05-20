package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;

import com.gmail.ge.and.rltkd0101.smgpproject.R;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.objects.AnimSprite;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

public class Player extends AnimSprite {
    public enum WeaponType {
        SWORD,
        HANDGUN
    }

    private float dx = 0, dy = 0;
    private float speed = 300f;
    private boolean facingLeft = false;
    private WeaponType weaponType;

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

        // 🔒 이동 경계 제한 (스프라이트 크기 고려)
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

        dstRect.offset(-GameView.offsetX, -GameView.offsetY); // ✅ 카메라 오프셋 적용

        if (facingLeft) {
            canvas.save();
            // 중심 기준으로 반전할 때도 offset이 적용된 중심 좌표를 사용해야 함
            canvas.scale(-1, 1, x - GameView.offsetX, y - GameView.offsetY);
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
            canvas.restore();
        } else {
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
        }

        dstRect.offset(GameView.offsetX, GameView.offsetY); // ✅ 원상 복구
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
}
