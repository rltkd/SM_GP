package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.scene.Scene;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Scene().push();
    }
}