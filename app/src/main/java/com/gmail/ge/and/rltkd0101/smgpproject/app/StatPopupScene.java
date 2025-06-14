package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

public class StatPopupScene extends Scene {
    private final String[] lines = {
            "최대 체력: " + PlayerStats.maxHp,
            "공격력: " + PlayerStats.attack,
            "방어력: " + PlayerStats.defense,
            "이동속도: " + PlayerStats.moveSpeed,
            "공격속도: " + PlayerStats.attackSpeed,
            "초당 회복량: " + PlayerStats.healPerSec,
            "처치 회복량: " + PlayerStats.healOnKill,
            "← 화면 터치 시 닫기"
    };

    @Override
    public void update() {
        // nothing
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(60f);
        paint.setColor(Color.YELLOW);
        float x = 100f, y = 200f;
        for (String line : lines) {
            canvas.drawText(line, x, y, paint);
            y += 100f;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            GameView.view.popScene(); // 닫기
            return true;
        }
        return false;
    }
}
