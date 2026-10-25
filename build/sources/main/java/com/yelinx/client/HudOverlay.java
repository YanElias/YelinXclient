package com.yelinx.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HudOverlay {
    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        if (mc.thePlayer == null || mc.gameSettings.showDebugInfo) {
            return;
        }

        String text = "YelinX  |  " + Minecraft.getDebugFPS() + " FPS";
        mc.fontRendererObj.drawStringWithShadow(text, 6, 6, 0xFFFFD600);
    }
}
