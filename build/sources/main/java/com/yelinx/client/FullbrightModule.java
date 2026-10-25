package com.yelinx.client;

import net.minecraft.client.Minecraft;

public class FullbrightModule extends ClientModule {
    private float previousGamma;

    public FullbrightModule() {
        super("fullbright", "Fullbright", "Aumenta a luminosidade do jogo.", "VISUAL");
    }

    @Override
    protected void onEnable() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.gameSettings != null) {
            previousGamma = mc.gameSettings.gammaSetting;
            mc.gameSettings.gammaSetting = 1000.0F;
        }
    }

    @Override
    protected void onDisable() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.gameSettings != null) mc.gameSettings.gammaSetting = previousGamma;
    }
}
