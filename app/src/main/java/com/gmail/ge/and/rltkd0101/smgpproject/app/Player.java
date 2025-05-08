package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.media.AsyncPlayer;

import com.gmail.ge.and.rltkd0101.smgpproject.R;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.objects.Sprite;

public class Player extends Sprite {
    public enum WeaponType {
        SWORD,
        HANDGUN
    }

    public Player(WeaponType weaponType) {
        super(getImageResId(weaponType)); // 무기 종류에 따라 이미지 설정
        setPosition(800f, 300f, 150f, 150f); // 크기도 적당히 조정
    }

    private static int getImageResId(WeaponType weaponType) {
        switch (weaponType) {
            case HANDGUN:
                return R.mipmap.idle_handgun;
            case SWORD:
            default:
                return R.mipmap.idle_sword;
        }
    }
}
