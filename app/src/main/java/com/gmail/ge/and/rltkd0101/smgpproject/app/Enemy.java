package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.gmail.ge.and.rltkd0101.smgpproject.R;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IGameObject;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.interfaces.IRecyclable;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.objects.Sprite;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

public class Enemy extends Sprite implements IRecyclable {
    public enum EnemyType {
        SLIME, ZOMBIE, GHOST
    }

    private boolean active = true;
    private float speed;
    private Player target;
    private EnemyType type;

    public Enemy() {
        super(R.mipmap.slime); // 기본값은 SLIME
    }

    public void revive(float x, float y, Player target, EnemyType type) {
        this.active = true;
        this.target = target;
        this.type = type;

        switch (type) {
            case SLIME:
                setImageResourceId(R.mipmap.slime);
                this.speed = 100f;
                break;
      /*      case ZOMBIE:
                setImageResourceId(R.mipmap.zombie);
                this.speed = 60f;
                break;
            case GHOST:
                setImageResourceId(R.mipmap.ghost);
                this.speed = 140f;
                break;
       */ }
        setPosition(x, y, 64, 64);
    }

    @Override
    public void onRecycle() {
        this.active = false;
        this.target = null;
    }

    @Override
    public void update() {
        if (!active || target == null) return;

        float dx = target.getX() - x;
        float dy = target.getY() - y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        if (dist > 1f) {
            float normX = dx / dist;
            float normY = dy / dist;
            float move = speed * GameView.frameTime;
            x += normX * move;
            y += normY * move;
            setPosition(x, y, width, height);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (!active) return;
        super.draw(canvas);
    }

    public boolean isActive() {
        return active;
    }

    public EnemyType getType() {
        return type;
    }

    public boolean checkHit(RectF attackBox) {
        return RectF.intersects(attackBox, getHitBox());
    }

    public RectF getHitBox() {
        return new RectF(
                x - width / 2, y - height / 2,
                x + width / 2, y + height / 2
        );
    }

}
