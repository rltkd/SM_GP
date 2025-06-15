package com.gmail.ge.and.rltkd0101.smgpproject.app;

import com.gmail.ge.and.rltkd0101.smgpproject.R;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.objects.Sprite;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

public class MainScene extends Scene {
    private Player player;
    private float elapsedPlayTime = 0f;

    public enum Layer {
        bg, Player, UI, enemy, bullet;
        public static final int COUNT = values().length;
    }

    public MainScene(Weapon weapon) {
        initScene(weapon);
    }

    private void initScene(Weapon weapon) {
        // [1] 전역 상태 초기화
        PlayerStats.reset();
        initLayers(Layer.COUNT);
        elapsedPlayTime = 0f;

        // [2] 배경
        add(Layer.bg, new Sprite(
                R.mipmap.background,
                1500f, 1000f,
                3000f, 2000f
        ));

        // [3] 플레이어
        player = new Player(weapon);
        add(Layer.Player, player);

        // [4] UI
        add(Layer.UI, new HpBar(player));
        add(Layer.UI, new ExpBar(player));
        add(Layer.UI, new Joystick(player));
        add(Layer.UI, new PlayTimeText(() -> elapsedPlayTime));

        // [5] 몬스터 스포너
        add(Layer.enemy, new EnemySpawner(player));
    }

    @Override
    public void update() {
        super.update();

        if (!GameView.view.isPaused()) {
            if (GameView.frameTime < 0.5f) {
                elapsedPlayTime += GameView.frameTime;
            }
        }

        CameraSystem.update(player, 3000f, 2000f);
        CollisionManager.handlePlayerAttack(this, player);
        CollisionManager.handleEnemyCollision(this, player);
    }

    @Override
    public boolean onBackPressed() {
        GameView.view.pushScene(new PausePopupScene());
        return true;
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.UI.ordinal();
    }
}
