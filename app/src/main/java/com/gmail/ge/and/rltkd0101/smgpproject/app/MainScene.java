package com.gmail.ge.and.rltkd0101.smgpproject.app;

import com.gmail.ge.and.rltkd0101.smgpproject.R;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.objects.Sprite;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.Metrics;

class MainScene extends Scene {
    public enum Layer {
        bg, // 배경
        // 다른 레이어...
        COUNT
    }

    public MainScene() {
        initLayers(Layer.COUNT.ordinal());

        // 배경 이미지 Sprite 추가
        add(Layer.bg, new Sprite(
                R.mipmap.background1,
                Metrics.width / 2, Metrics.height / 2,
                Metrics.width, Metrics.height
        ));
    }
}
