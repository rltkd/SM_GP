package com.gmail.ge.and.rltkd0101.smgpproject.app;

import com.gmail.ge.and.rltkd0101.smgpproject.R;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.objects.Sprite;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.Metrics;

class MainScene extends Scene {
    private Player player;
    public enum Layer {
        bg, Player,UI, enemy;
        public static final int COUNT = values().length;
    }

    public MainScene() {
        initLayers(Layer.COUNT);

        add(Layer.bg, new Sprite(
                R.mipmap.background,
               1500f,1000f,
                3000f,2000f
        )); 
        player = new Player(Player.WeaponType.SWORD);
        add(Layer.Player, player);
        Joystick joystick = new Joystick(player);
        add(Layer.UI, joystick);
        add(Layer.enemy,new EnemySpawner(player));
    }
    public void update() {
        super.update();

        if (player == null) return;//플레이어가 초기화전이면 스킵

        // 5. 카메라 offset = 플레이어 중심 - 화면 중심
        GameView.offsetX = player.getX() - Metrics.width / 2f;
        GameView.offsetY = player.getY() - Metrics.height / 2f;

        // 6. 맵 범위 제한 (스프라이트 크기 고려해서 여유 여백 줌)
        float marginX = player.getWidth() / 2f;
        float marginY = player.getHeight() / 2f;
        GameView.offsetX = Math.max(0, Math.min(GameView.offsetX, 3000f - Metrics.width));
        GameView.offsetY = Math.max(0, Math.min(GameView.offsetY, 2000f - Metrics.height));
    }
    @Override
    protected int getTouchLayerIndex() {
        return Layer.UI.ordinal(); // UI 레이어에서 터치 받음
    }
}
