package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IGameObject;

import java.util.function.Supplier;

public class PlayTimeText implements IGameObject {
    private final Paint paint;
    private final Supplier<Float> timeSupplier;

    public PlayTimeText(Supplier<Float> timeSupplier) {
        this.timeSupplier = timeSupplier;
        this.paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(60f);
        paint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    public void update() {
        // nothing to update
    }

    @Override
    public void draw(Canvas canvas) {
        float time = timeSupplier.get();
        int totalSeconds = (int) time;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        String text = String.format("TIME: %02d:%02d", minutes, seconds);
        canvas.drawText(text, 600f, 100f, paint);
    }
}
