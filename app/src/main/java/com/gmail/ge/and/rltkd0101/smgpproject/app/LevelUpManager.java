package com.gmail.ge.and.rltkd0101.smgpproject.app;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.gmail.ge.and.rltkd0101.smgpproject.R;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;

import java.util.List;

public class LevelUpManager {
    private static boolean showing = false;

    public static void request() {
        if (showing) return;
        showing = true;

        GameView.view.pauseGame();

        List<UpgradeOption> upgrades = UpgradeRegistry.getRandomOptions(3);

        GameView.view.post(() -> {
            LayoutInflater inflater = LayoutInflater.from(GameView.view.getContext());
            View popupView = inflater.inflate(R.layout.level_up_popup, null);

            LinearLayout container = popupView.findViewById(R.id.upgrade_container);

            for (UpgradeOption upgrade : upgrades) {
                Button button = new Button(GameView.view.getContext());
                button.setText(upgrade.name + "\n" + upgrade.description);
                button.setAllCaps(false); // 소문자 유지
                button.setTextSize(16f);
                button.setPadding(20, 20, 20, 20);
                button.setBackgroundColor(0xFF444444);
                button.setTextColor(0xFFFFFFFF);

                button.setOnClickListener(v -> {
                    upgrade.apply();
                    GameView.view.resumeGame();
                    dialog.dismiss();
                    showing = false;
                });

                container.addView(button);
            }

            dialog = new AlertDialog.Builder(GameView.view.getContext())
                    .setView(popupView)
                    .setCancelable(false)
                    .create();

            dialog.show();
        });
    }

    private static AlertDialog dialog;
}
