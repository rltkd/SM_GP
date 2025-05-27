package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gmail.ge.and.rltkd0101.smgpproject.R;

public class WeaponSelectActivity extends Activity {

    public static final String EXTRA_WEAPON_TYPE = "weapon_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon_select);

        Button swordButton = findViewById(R.id.button_sword);
        Button gunButton = findViewById(R.id.button_gun);

        swordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchGame("sword");
            }
        });

        gunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchGame("gun");
            }
        });
    }

    private void launchGame(String weaponType) {
        Intent intent = new Intent(this, SurvivorActivity.class);
        intent.putExtra(EXTRA_WEAPON_TYPE, weaponType);
        startActivity(intent);
        finish();
    }
}
