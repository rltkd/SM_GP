package com.gmail.ge.and.rltkd0101.smgpproject.app;

import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.GameView;
import com.gmail.ge.and.rltkd0101.smgpproject.a2dg.framework.view.Metrics;

public class CameraSystem {
    public static void update(Player player, float mapWidth, float mapHeight) {
        if (player == null) return;

        GameView.offsetX = player.getX() - Metrics.width / 2f;
        GameView.offsetY = player.getY() - Metrics.height / 2f;

        float marginX = player.getWidth() / 2f;
        float marginY = player.getHeight() / 2f;

        GameView.offsetX = Math.max(marginX, Math.min(GameView.offsetX, mapWidth - Metrics.width - marginX));
        GameView.offsetY = Math.max(marginY, Math.min(GameView.offsetY, mapHeight - Metrics.height - marginY));
    }
}
