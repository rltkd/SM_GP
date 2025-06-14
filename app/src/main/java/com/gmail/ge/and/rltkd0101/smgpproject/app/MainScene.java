package com.gmail.ge.and.rltkd0101.smgpproject.app;

import com.gmail.ge.and.rltkd0101.smgpproject.R;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.objects.Sprite;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

public class MainScene extends Scene {
    private final Player player;

    @Override
    public boolean onBackPressed() {
        GameView.view.pushScene(new PausePopupScene(this));
        return true;
    }

    public enum Layer {
        bg, Player, UI, enemy, bullet;
        public static final int COUNT = values().length;
    }

    private float elapsedPlayTime = 0f;

    public MainScene(Weapon weapon) {
        initLayers(Layer.COUNT);

        // 배경
        add(Layer.bg, new Sprite(
                R.mipmap.background,
                1500f, 1000f,
                3000f, 2000f
        ));

        // 플레이어 생성 및 무기 주입
        player = new Player(weapon);
        add(Layer.Player, player);

        // UI
        add(Layer.UI, new HpBar(player));
        add(Layer.UI, new ExpBar(player));
        add(Layer.UI, new Joystick(player));

        // 타이머 UI
        PlayTimeText playTimeText = new PlayTimeText(() -> elapsedPlayTime);
        add(Layer.UI, playTimeText);

        // 몬스터 스포너
        add(Layer.enemy, new EnemySpawner(player));
    }

    @Override
    public void update() {
        super.update();

        if (!GameView.view.isPaused()) {
            elapsedPlayTime += GameView.frameTime;
        }

        CameraSystem.update(player, 3000f, 2000f);
        CollisionManager.handlePlayerAttack(this, player);
        CollisionManager.handleEnemyCollision(this, player);
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.UI.ordinal(); // UI 레이어에서 터치 처리
    }
}
