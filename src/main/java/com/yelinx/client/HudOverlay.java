package com.yelinx.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class HudOverlay {
    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        if (mc.thePlayer == null || mc.gameSettings.showDebugInfo) return;

        int y = 6;
        if (ModuleManager.isEnabled("fps")) {
            String label = "YelinX  |  " + Minecraft.getDebugFPS() + " FPS";
            mc.fontRendererObj.drawStringWithShadow(label, 6, y, 0xFFFFD600);
            y += 12;
        }

        if (ModuleManager.isEnabled("coordinates")) {
            String coords = String.format("XYZ: %d, %d, %d",
                    (int) Math.floor(mc.thePlayer.posX),
                    (int) Math.floor(mc.thePlayer.posY),
                    (int) Math.floor(mc.thePlayer.posZ));
            mc.fontRendererObj.drawStringWithShadow(coords, 6, y, 0xFFF5F5F5);
        }

        if (ModuleManager.isEnabled("keystrokes")) {
            drawKeystrokes(new ScaledResolution(mc));
        }
    }

    private void drawKeystrokes(ScaledResolution resolution) {
        int box = 18;
        int gap = 2;
        int x = resolution.getScaledWidth() - 68;
        int y = 8;
        drawKey("W", Keyboard.KEY_W, x + box + gap, y, box);
        drawKey("A", Keyboard.KEY_A, x, y + box + gap, box);
        drawKey("S", Keyboard.KEY_S, x + box + gap, y + box + gap, box);
        drawKey("D", Keyboard.KEY_D, x + (box + gap) * 2, y + box + gap, box);
        drawKey("J", Keyboard.KEY_LBUTTON, x, y + (box + gap) * 2, box);
        drawKey("SP", Keyboard.KEY_SPACE, x + box + gap, y + (box + gap) * 2, box * 2 + gap);
    }

    private void drawKey(String label, int key, int x, int y, int width) {
        boolean pressed = Keyboard.isKeyDown(key);
        int background = pressed ? 0xFFFFD600 : 0xCC202020;
        int text = pressed ? 0xFF202020 : 0xFFF5F5F5;
        net.minecraft.client.gui.Gui.drawRect(x, y, x + width, y + 18, background);
        mc.fontRendererObj.drawString(label,
                x + (width - mc.fontRendererObj.getStringWidth(label)) / 2,
                y + 5, text);
    }
}
