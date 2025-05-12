package com.gmail.ge.and.rltkd0101.smgpproject.app;

import com.gmail.ge.and.rltkd0101.smgpproject.R;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.objects.Sprite;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.Metrics;

class MainScene extends Scene {
    public enum Layer {
        bg, Player,UI, enemy;
        public static final int COUNT = values().length;
    }

    public MainScene() {
        initLayers(Layer.COUNT);

        add(Layer.bg, new Sprite(
                R.mipmap.background1,
                Metrics.width / 2, Metrics.height / 2,
                Metrics.width, Metrics.height
        )); 
        Player player = new Player(Player.WeaponType.HANDGUN);
        add(Layer.Player, player);
        Joystick joystick = new Joystick(player);
        add(Layer.UI, joystick);
        add(Layer.enemy,new EnemySpawner(player));
    }
    @Override
    protected int getTouchLayerIndex() {
        return Layer.UI.ordinal(); // UI 레이어에서 터치 받음
    }
}
