package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IGameObject;

public class HpBar implements IGameObject {
    private final Player player;
    private final RectF baseRect = new RectF();
    private final Paint backPaint = new Paint();
    private final Paint barPaint = new Paint();
    private final Paint textPaint = new Paint();

    public HpBar(Player player) {
        this.player = player;
        backPaint.setColor(Color.DKGRAY);
        barPaint.setColor(Color.RED);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(28f);
        textPaint.setAntiAlias(true);
    }

    @Override
    public void update() {
        // 체력바는 따로 업데이트할 게 없음
    }

    @Override
    public void draw(Canvas canvas) {

        float left = 50f;
        float top = 50f;
        float width = 300f;
        float height = 30f;

        float ratio = Math.max(0f, player.getHpRatio());
        float hpWidth = width * ratio;

        baseRect.set(left, top, left + width, top + height);
        canvas.drawRect(baseRect, backPaint);

        baseRect.right = left + hpWidth;
        canvas.drawRect(baseRect, barPaint);

        canvas.drawText("HP: " + player.getHp() + " / " + (int)player.getMaxHp(), left + 10f, top + height + 30f, textPaint);
    }
}
