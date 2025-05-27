package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.os.Bundle;

import com.gmail.ge.and.rltkd0101.smgpproject.BuildConfig;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.activity.GameActivity;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.Metrics;

public class SurvivorActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 디버그용 디버깅 정보 출력 여부
        GameView.drawsDebugStuffs = BuildConfig.DEBUG;

        // 게임 해상도 설정
        Metrics.setGameSize(1600, 900);

        super.onCreate(savedInstanceState);

        // 무기 타입 인텐트에서 받아오기
        String weaponType = getIntent().getStringExtra(WeaponSelectActivity.EXTRA_WEAPON_TYPE);
        Weapon weapon;

        if ("gun".equals(weaponType)) {
            weapon = new HandgunWeapon();
        } else {
            weapon = new SwordWeapon();
        }

        // 선택한 무기를 MainScene에 넘겨서 게임 시작
        new MainScene(weapon).push();
    }
}
