package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.graphics.RectF;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;

public class HandgunWeapon implements Weapon {

    public void attack(Player player, Scene scene) {
        RectF box = getAttackBox(player);
        if (box == null || box.isEmpty()) return;
    }

    @Override
    public RectF getAttackBox(Player player) {
        return null;
    }
}