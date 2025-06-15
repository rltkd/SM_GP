package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IGameObject;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.ITouchable;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.Metrics;

public class Joystick implements IGameObject, ITouchable {
    private final float baseRadius = 150f;
    private final float hatRadius = 50f;
    private final PointF center = new PointF();
    private final PointF actuator = new PointF(0f, 0f);
    private boolean isPressed = false;
    private int pointerId = -1;

    private Player player;
    private Paint basePaint, hatPaint;

    public Joystick(Player player) {
        this.player = player;

        basePaint = new Paint();
        basePaint.setColor(Color.argb(100, 0, 0, 0));
        basePaint.setStyle(Paint.Style.FILL);

        hatPaint = new Paint();
        hatPaint.setColor(Color.argb(180, 255, 255, 255));
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
        if (!isPressed) return;

        canvas.drawCircle(center.x, center.y, baseRadius, basePaint);
        float hatX = center.x + actuator.x * baseRadius;
        float hatY = center.y + actuator.y * baseRadius;
        canvas.drawCircle(hatX, hatY, hatRadius, hatPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int actionIndex = event.getActionIndex();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (!isPressed) {
                    pointerId = event.getPointerId(actionIndex);
                    float[] logicPos = toLogicalCoords(event.getX(actionIndex), event.getY(actionIndex));
                    center.set(logicPos[0], logicPos[1]);
                    isPressed = true;
                    updateActuator(logicPos[0], logicPos[1]);
                    return true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (isPressed) {
                    int index = event.findPointerIndex(pointerId);
                    if (index != -1 && index < event.getPointerCount()) {
                        float[] logicPos = toLogicalCoords(event.getX(index), event.getY(index));
                        updateActuator(logicPos[0], logicPos[1]);
                        return true;
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                int releasedId = event.getPointerId(actionIndex);
                if (isPressed && releasedId == pointerId) {
                    isPressed = false;
                    pointerId = -1;
                    actuator.set(0f, 0f);
                    return true;
                }
                break;
        }

        return false;
    }

    private void updateActuator(float touchX, float touchY) {
        float dx = touchX - center.x;
        float dy = touchY - center.y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance < baseRadius) {
            actuator.set(dx / baseRadius, dy / baseRadius);
        } else {
            actuator.set(dx / distance, dy / distance);
        }
    }

    private float[] toLogicalCoords(float screenX, float screenY) {
        float scaleX = (float) GameView.view.getWidth() / Metrics.width;
        float scaleY = (float) GameView.view.getHeight() / Metrics.height;
        return new float[] { screenX / scaleX, screenY / scaleY };
    }
}
