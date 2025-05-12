package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.Matrix;
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
        super(R.mipmap.sword_attack_sheet, 8f, 3); // 3프레임, 8fps
        this.weaponType = weaponType;
        setPosition(800f, 450f, 150f, 150f);
    }

    @Override
    public void update() {
        float distance = speed * GameView.frameTime;
        x += dx * distance;
        y += dy * distance;
        setPosition(x, y, width, height);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.d("Player", "draw 호출됨: x=" + x + " y=" + y + " width=" + width + " height=" + height);
        if (facingLeft) {
            Matrix m = new Matrix();
            m.setScale(-1, 1);
            m.postTranslate(x + width, y);
            canvas.save();
            canvas.concat(m);
            super.draw(canvas); // 꼭 호출해야 AnimSprite가 프레임 계산함
            canvas.restore();
        } else {
            super.draw(canvas);
        }
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
