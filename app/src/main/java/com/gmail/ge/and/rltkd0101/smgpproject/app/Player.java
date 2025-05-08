package com.gmail.ge.and.rltkd0101.smgpproject.app;

import com.gmail.ge.and.rltkd0101.smgpproject.R;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.objects.Sprite;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

public class Player extends Sprite {
    public enum WeaponType {
        SWORD,
        HANDGUN
    }

    private float dx = 0, dy = 0;
    private float speed = 300f; // 초당 이동 속도 (픽셀 단위)
    private WeaponType weaponType;

    public Player(WeaponType weaponType) {
        super(getImageResId(weaponType)); // 이미지 설정
        this.weaponType = weaponType;

        // 위치 및 크기 설정 (중앙 기준, 크기 150x150)
        setPosition(800f, 450f, 150f, 150f);
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

    // 프레임마다 이동 처리
    @Override
    public void update() {
        float distance = speed * GameView.frameTime;
        x += dx * distance;
        y += dy * distance;
        setPosition(x, y, width, height);
    }

    // 외부에서 이동 방향 설정 (조이스틱에서 호출)
    public void setDirection(float dx, float dy) {
        float len = (float) Math.sqrt(dx * dx + dy * dy);
        if (len > 0) {
            this.dx = dx / len;
            this.dy = dy / len;
        } else {
            this.dx = this.dy = 0;
        }
    }

    // 무기 종류 가져오기 (필요 시)
    public WeaponType getWeaponType() {
        return weaponType;
    }
}
