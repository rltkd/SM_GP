package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.os.Bundle;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.gmail.ge.and.rltkd0101.smgpproject.BuildConfig;
import com.gmail.ge.and.rltkd0101.smgpproject.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (BuildConfig.DEBUG) {
            startActivity(new Intent(this, SurvivorActivity.class));
        }
    }

    public void onBtnStartGame(View view) {
        startGame(1);
    }

    private void startGame(int stage) {
        Intent intent = new Intent(this, SurvivorActivity.class);
        startActivity(intent);
    }


}

