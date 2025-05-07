package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.os.Bundle;

import com.gmail.ge.and.rltkd0101.smgpproject.BuildConfig;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.activity.GameActivity;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.Metrics;

public class SurvivorActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GameView.drawsDebugStuffs = BuildConfig.DEBUG;
        Metrics.setGameSize(1600, 900);
        super.onCreate(savedInstanceState);
        new Scene().push();
    }
}