package com.gmail.ge.and.rltkd0101.smgpproject.app;

public class UpgradeOption {
    public final String name;
    public final String description;
    public final int iconResId;
    public final Runnable effect;

    public UpgradeOption(String name, String description, int iconResId, Runnable effect) {
        this.name = name;
        this.description = description;
        this.iconResId = iconResId;
        this.effect = effect;
    }

    public void apply() {
        effect.run();
    }
}
