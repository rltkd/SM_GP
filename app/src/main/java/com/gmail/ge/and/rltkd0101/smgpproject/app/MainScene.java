package com.gmail.ge.and.rltkd0101.smgpproject.app;

import com.gmail.ge.and.rltkd0101.smgpproject.R;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.objects.Sprite;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.Metrics;

class MainScene extends Scene {
    private Player player;
    private HpBar hpBar;
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
        hpBar = new HpBar(player);
        add(Layer.UI,hpBar);
        Joystick joystick = new Joystick(player);
        add(Layer.UI, joystick);
        add(Layer.enemy,new EnemySpawner(player));
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
        return Layer.UI.ordinal(); // UI 레이어에서 터치 받음
    }
}
