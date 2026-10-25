package com.yelinx.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class HudOverlay {
    private static final int YELLOW = 0xFFFFD600;
    private static final int WHITE = 0xFFF5F5F5;
    private static final int GRAPHITE = 0xCC202020;
    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        if (mc.thePlayer == null || mc.gameSettings.showDebugInfo) return;

        int y = 7;
        if (ModuleManager.isEnabled("fps")) {
            String label = "YELINX";
            String fps = Minecraft.getDebugFPS() + " FPS";
            int totalWidth = mc.fontRendererObj.getStringWidth(label) + 8
                    + mc.fontRendererObj.getStringWidth(fps) + 16;
            drawPanel(6, y, totalWidth, 15);
            mc.fontRendererObj.drawString(label, 12, y + 4, YELLOW);
            int dividerX = 12 + mc.fontRendererObj.getStringWidth(label) + 4;
            Gui.drawRect(dividerX, y + 3, dividerX + 1, y + 12, 0xFF555555);
            mc.fontRendererObj.drawString(fps, dividerX + 6, y + 4, WHITE);
            y += 20;
        }

        if (ModuleManager.isEnabled("coordinates")) {
            String coords = String.format("XYZ  %d  /  %d  /  %d",
                    (int) Math.floor(mc.thePlayer.posX),
                    (int) Math.floor(mc.thePlayer.posY),
                    (int) Math.floor(mc.thePlayer.posZ));
            int panelWidth = mc.fontRendererObj.getStringWidth(coords) + 16;
            drawPanel(6, y, panelWidth, 15);
            mc.fontRendererObj.drawString(coords, 12, y + 4, WHITE);
        }

        if (ModuleManager.isEnabled("keystrokes")) {
            drawKeystrokes(new ScaledResolution(mc));
        }
    }

    private void drawPanel(int x, int y, int width, int height) {
        Gui.drawRect(x, y, x + width, y + height, GRAPHITE);
        Gui.drawRect(x, y, x + 2, y + height, YELLOW);
        Gui.drawRect(x + 2, y + height - 1, x + width, y + height, 0x663F3F3F);
    }

    private void drawKeystrokes(ScaledResolution resolution) {
        int box = 20;
        int gap = 3;
        int x = resolution.getScaledWidth() - 72;
        int y = 8;
        drawKey("W", Keyboard.isKeyDown(Keyboard.KEY_W), x + box + gap, y, box);
        drawKey("A", Keyboard.isKeyDown(Keyboard.KEY_A), x, y + box + gap, box);
        drawKey("S", Keyboard.isKeyDown(Keyboard.KEY_S), x + box + gap, y + box + gap, box);
        drawKey("D", Keyboard.isKeyDown(Keyboard.KEY_D), x + (box + gap) * 2, y + box + gap, box);
        drawKey("LMB", Mouse.isButtonDown(0), x, y + (box + gap) * 2, 31);
        drawKey("RMB", Mouse.isButtonDown(1), x + 34, y + (box + gap) * 2, 31);
        drawKey("SPACE", Keyboard.isKeyDown(Keyboard.KEY_SPACE), x, y + (box + gap) * 3, 65);
    }

    private void drawKey(String label, boolean pressed, int x, int y, int width) {
        int background = pressed ? YELLOW : GRAPHITE;
        int text = pressed ? 0xFF202020 : WHITE;
        Gui.drawRect(x, y, x + width, y + 20, 0xFF101010);
        Gui.drawRect(x + 1, y + 1, x + width - 1, y + 19, background);
        mc.fontRendererObj.drawString(label,
                x + (width - mc.fontRendererObj.getStringWidth(label)) / 2,
                y + 6, text);
    }
}
