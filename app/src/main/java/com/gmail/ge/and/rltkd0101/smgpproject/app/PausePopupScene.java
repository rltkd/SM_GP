package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.Metrics;

public class PausePopupScene extends Scene {
    private final String[] options = { "계속하기", "스탯 보기", "종료하기" };
    private final float yStart = 250f;
    private final float gap = 140f;

    public PausePopupScene() {
        setTransparent(true); // 화면 아래가 보이도록 설정
    }

    @Override
    public void update() {
        // 일시정지 상태이므로 논리 처리 없음
    }

    @Override
    public void draw(Canvas canvas) {
        Paint bg = new Paint();
        bg.setColor(Color.argb(160, 0, 0, 0));
        canvas.drawRect(0, 0, Metrics.width, Metrics.height, bg);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(80f);
        paint.setTextAlign(Paint.Align.CENTER);

        float cx = Metrics.width / 2f;

        for (int i = 0; i < options.length; i++) {
            canvas.drawText(options[i], cx, yStart + i * gap, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) return false;

        float[] pos = Metrics.fromScreen(event.getX(), event.getY());
        float touchY = pos[1];
        int index = (int)((touchY - yStart + gap / 2) / gap);
        if (index < 0 || index >= options.length) return false;

        switch (index) {
            case 0:
                GameView.view.popScene(); // 계속하기
                break;
            case 1:
                GameView.view.changeScene(new StatPopupScene());
                break;
            case 2:
                GameView.view.popAllScenes(); // 종료
                break;
        }
        return true;
    }
}
