package com.yelinx.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

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
        int box = 20;
        int gap = 2;
        int x = resolution.getScaledWidth() - 68;
        int y = 8;
        drawKey("W", Keyboard.isKeyDown(Keyboard.KEY_W), x + box + gap, y, box);
        drawKey("A", Keyboard.isKeyDown(Keyboard.KEY_A), x, y + box + gap, box);
        drawKey("S", Keyboard.isKeyDown(Keyboard.KEY_S), x + box + gap, y + box + gap, box);
        drawKey("D", Keyboard.isKeyDown(Keyboard.KEY_D), x + (box + gap) * 2, y + box + gap, box);
        drawKey("LMB", Mouse.isButtonDown(0), x, y + (box + gap) * 2, 31);
        drawKey("RMB", Mouse.isButtonDown(1), x + 33, y + (box + gap) * 2, 31);
        drawKey("SP", Keyboard.isKeyDown(Keyboard.KEY_SPACE), x, y + (box + gap) * 3, 64);
    }

    private void drawKey(String label, boolean pressed, int x, int y, int width) {
        int background = pressed ? 0xFFFFD600 : 0xCC202020;
        int text = pressed ? 0xFF202020 : 0xFFF5F5F5;
        Gui.drawRect(x, y, x + width, y + 20, background);
        mc.fontRendererObj.drawString(label,
                x + (width - mc.fontRendererObj.getStringWidth(label)) / 2,
                y + 6, text);
    }
}
