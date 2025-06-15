package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.Metrics;

public class GameOverPopupScene extends Scene {

    @Override
    public void update() {
        // 팝업이므로 로직 없음
    }

    @Override
    public void draw(Canvas canvas) {
        Paint bg = new Paint();
        bg.setColor(Color.argb(180, 0, 0, 0)); // 반투명 어두운 배경
        canvas.drawRect(0, 0, Metrics.width, Metrics.height, bg);

        Paint paint = new Paint();
        paint.setTextSize(100f);
        paint.setColor(Color.RED);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Game Over", Metrics.width / 2f, Metrics.height / 2f, paint);

        paint.setTextSize(50f);
        paint.setColor(Color.WHITE);
        canvas.drawText("터치하여 종료", Metrics.width / 2f, Metrics.height / 2f + 100f, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            GameView.view.popAllScenes(); // 전체 씬 정리
            PlayerStats.reset();
            return true;
        }
        return false;
    }
}
