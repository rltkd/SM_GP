package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.Metrics;

public class PausePopupScene extends Scene {
    private final MainScene parent;
    private final String[] options = { "계속하기", "스탯 보기", "종료하기" };
    private int selectedIndex = -1;
    private final float x = 600f, yStart = 400f, gap = 150f;

    public PausePopupScene(MainScene parent) {
        this.parent = parent;
    }

    @Override
    public void update() {
        // 일시정지이므로 로직 없음
    }

    @Override
    public void draw(Canvas canvas) {
        // 반투명 배경
        Paint bg = new Paint();
        bg.setColor(Color.argb(160, 0, 0, 0)); // 어두운 반투명
        canvas.drawRect(0, 0, Metrics.width, Metrics.height, bg);

        // 메뉴 텍스트
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(80f);
        paint.setTextAlign(Paint.Align.CENTER); // 중앙 정렬

        float cx = Metrics.width / 2f;
        float yStart = 700f;
        float gap = 140f;

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
            case 0: // 계속하기
                GameView.view.popScene();
                break;
            case 1: // 스탯 보기
                GameView.view.pushScene(new StatPopupScene());
                break;
            case 2: // 종료
                GameView.view.popAllScenes();
                break;
        }
        return true;
    }

}
