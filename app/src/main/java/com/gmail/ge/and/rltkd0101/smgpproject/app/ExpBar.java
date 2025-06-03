package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IGameObject;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.Metrics;

public class ExpBar implements IGameObject {
    private Player player;
    private Paint bgPaint, fgPaint;
    private RectF bgRect = new RectF();
    private RectF fgRect = new RectF();

    public ExpBar(Player player) {
        this.player = player;

        bgPaint = new Paint();
        bgPaint.setColor(Color.DKGRAY); // 배경: 어두운 회색
        fgPaint = new Paint();
        fgPaint.setColor(Color.GREEN);  // 경험치: 초록색
    }

    @Override
    public void update() {
        // 경험치 수치는 외부에서 바뀌므로 별도 처리 없음
    }

    @Override
    public void draw(Canvas canvas) {
        float margin = 20f;
        float barHeight = 20f;
        float width = Metrics.width - margin * 2;

        float ratio = (float) player.getExp() / player.getExpToNextLevel();

        bgRect.set(margin, margin, margin + width, margin + barHeight);
        fgRect.set(margin, margin, margin + width * ratio, margin + barHeight);

        canvas.drawRect(bgRect, bgPaint);
        canvas.drawRect(fgRect, fgPaint);
    }
}
