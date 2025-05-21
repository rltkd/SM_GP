package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.RectF;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;

public interface Weapon {
    void attack(Player player , Scene scene);
    RectF getAttackBox(Player player);
}
