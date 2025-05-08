package com.gmail.ge.and.rltkd0101.smgpproject.app;

import com.gmail.ge.and.rltkd0101.smgpproject.R;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.objects.Sprite;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.Metrics;

class MainScene extends Scene {
    public enum Layer {
        bg, Player;// 배경
        // 다른 레이어...
        public static final int COUNT = values().length;
    }

    public MainScene() {
        initLayers(Layer.COUNT);

        // 배경 이미지 Sprite 추가
        add(Layer.bg, new Sprite(
                R.mipmap.background1,
                Metrics.width / 2, Metrics.height / 2,
                Metrics.width, Metrics.height
        ));
        Player player = new Player(Player.WeaponType.SWORD);
        add(Layer.Player, player);
    }
}
