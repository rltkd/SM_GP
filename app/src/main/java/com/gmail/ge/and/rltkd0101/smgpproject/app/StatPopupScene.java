package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

public class StatPopupScene extends Scene {

    @Override
    public void update() {
        // no logic
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(60f);
        paint.setColor(Color.BLACK); // 가시성 좋은 검정색
        float x = 600f, y = 100f;

        canvas.drawText("최대 체력: " + format(PlayerStats.maxHp), x, y, paint); y += 100f;
        canvas.drawText("공격력: " + format(PlayerStats.attack), x, y, paint); y += 100f;
        canvas.drawText("방어력: " + format(PlayerStats.defense), x, y, paint); y += 100f;
        canvas.drawText("이동속도: " + format(PlayerStats.moveSpeed), x, y, paint); y += 100f;
        canvas.drawText("공격속도: " + format(PlayerStats.attackSpeed), x, y, paint); y += 100f;
        canvas.drawText("초당 회복량: " + format(PlayerStats.healPerSec), x, y, paint); y += 100f;
        canvas.drawText("처치 회복량: " + format(PlayerStats.healOnKill), x, y, paint); y += 120f;

        paint.setColor(Color.DKGRAY);
        canvas.drawText("← 화면 터치 시 닫기", x, y, paint);
    }

    private String format(float value) {
        return String.format("%.2f", value); // 소수점 둘째 자리까지
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
