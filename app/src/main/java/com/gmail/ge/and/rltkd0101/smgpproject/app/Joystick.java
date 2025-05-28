package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IGameObject;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.ITouchable;

public class Joystick implements IGameObject, ITouchable {
    private final float baseRadius = 150f; // 외곽 원 반지름
    private final float hatRadius = 50f; // 안쪽 원 반지름
    private final PointF center = new PointF(200f, 700f); // 조이스틱 위치 (왼쪽 아래 고정)
    private final PointF actuator = new PointF(0f, 0f); // 방향 벡터
    private boolean isPressed = false;

    private Player player;
    private Paint basePaint, hatPaint;

    public Joystick(Player player) {
        this.player = player;

        basePaint = new Paint();
        basePaint.setColor(Color.argb(100, 0, 0, 0)); // 반투명 회색
        basePaint.setStyle(Paint.Style.FILL);

        hatPaint = new Paint();
        hatPaint.setColor(Color.argb(180, 255, 255, 255)); // 반투명 흰색
        hatPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void update() {
        if (!isPressed) {
            actuator.set(0f, 0f);
        }
        player.setActuator(actuator.x, actuator.y);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(center.x, center.y, baseRadius, basePaint);
        float hatX = center.x + actuator.x * baseRadius;
        float hatY = center.y + actuator.y * baseRadius;
        canvas.drawCircle(hatX, hatY, hatRadius, hatPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float dx = event.getX() - center.x;
        float dy = event.getY() - center.y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (distance < baseRadius) {
                    actuator.set(dx / baseRadius, dy / baseRadius);
                } else {
                    actuator.set(dx / distance, dy / distance);
                }
                isPressed = true;
                return true;

            case MotionEvent.ACTION_UP:
                actuator.set(0f, 0f);
                isPressed = false;
                return true;
        }
        return false;
    }
}
