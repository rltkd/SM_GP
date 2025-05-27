package com.gmail.ge.and.rltkd0101.smgpproject.app;

import com.gmail.ge.and.rltkd0101.smgpproject.R;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.objects.Sprite;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;

public class MainScene extends Scene {
    private Player player;
    private HpBar hpBar;

    public enum Layer {
        bg, Player, UI, enemy, bullet;
        public static final int COUNT = values().length;
    }

    // ✅ 무기 외부에서 주입받는 생성자
    public MainScene(Weapon weapon) {
        initLayers(Layer.COUNT);

        // 배경
        add(Layer.bg, new Sprite(
                R.mipmap.background,
                1500f, 1000f,
                3000f, 2000f
        ));

        // 플레이어 + 무기 주입
        player = new Player(weapon);
        add(Layer.Player, player);

        // HP 바
        hpBar = new HpBar(player);
        add(Layer.UI, hpBar);

        // 조이스틱
        Joystick joystick = new Joystick(player);
        add(Layer.UI, joystick);

        // 몬스터 스폰
        add(Layer.enemy, new EnemySpawner(player));
    }

    // 무기 바꾸기 (선택적 사용 가능)
    public void setWeapon(Weapon weapon) {
        if (player != null) {
            player.setWeapon(weapon);
        }
    }

    @Override
    public void update() {
        super.update();
        CameraSystem.update(player, 3000f, 2000f);
        CollisionManager.handlePlayerAttack(this, player);
        CollisionManager.handleEnemyCollision(this, player);
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.UI.ordinal(); // UI 레이어에서 터치 처리
    }
}
