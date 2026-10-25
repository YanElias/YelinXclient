package com.yelinx.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HudOverlay {
    private static final int YELLOW = 0xFFFFD600;
    private static final int WHITE = 0xFFF5F5F5;
    private static final int MUTED = 0xFFB8B8B8;
    private static final int GRAPHITE = 0xCC171717;
    private final Minecraft mc = Minecraft.getMinecraft();
    private final SimpleDateFormat clockFormat = new SimpleDateFormat("HH:mm", Locale.ROOT);

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        if (mc.thePlayer == null || mc.gameSettings.showDebugInfo) return;

        int y = 7;
        if (ModuleManager.isEnabled("fps")) {
            String label = "YELINX";
            String fps = Minecraft.getDebugFPS() + " FPS";
            int totalWidth = mc.fontRendererObj.getStringWidth(label) + 8
                    + mc.fontRendererObj.getStringWidth(fps) + 16;
            drawPanel(7, y, totalWidth, 16);
            mc.fontRendererObj.drawString(label, 13, y + 4, YELLOW);
            int dividerX = 13 + mc.fontRendererObj.getStringWidth(label) + 4;
            Gui.drawRect(dividerX, y + 3, dividerX + 1, y + 13, 0xFF555555);
            mc.fontRendererObj.drawString(fps, dividerX + 6, y + 4, WHITE);
            y += 21;
        }

        if (ModuleManager.isEnabled("coordinates")) {
            String coords = String.format(Locale.ROOT, "XYZ  %d  /  %d  /  %d",
                    (int) Math.floor(mc.thePlayer.posX),
                    (int) Math.floor(mc.thePlayer.posY),
                    (int) Math.floor(mc.thePlayer.posZ));
            int panelWidth = mc.fontRendererObj.getStringWidth(coords) + 18;
            drawPanel(7, y, panelWidth, 16);
            mc.fontRendererObj.drawString(coords, 13, y + 4, WHITE);
            y += 21;
        }

        if (ModuleManager.isEnabled("direction")) {
            String direction = "FACING  " + getDirection(mc.thePlayer.rotationYaw);
            int panelWidth = mc.fontRendererObj.getStringWidth(direction) + 18;
            drawPanel(7, y, panelWidth, 16);
            mc.fontRendererObj.drawString("FACING", 13, y + 4, YELLOW);
            mc.fontRendererObj.drawString(getDirection(mc.thePlayer.rotationYaw),
                    57, y + 4, WHITE);
            y += 21;
        }

        if (ModuleManager.isEnabled("armor")) {
            int armorCount = 0;
            for (int i = 0; i < 4; i++) {
                if (mc.thePlayer.getCurrentArmor(i) != null) armorCount++;
            }
            String armor = "ARMOR  " + armorCount + "/4";
            int panelWidth = mc.fontRendererObj.getStringWidth(armor) + 18;
            drawPanel(7, y, panelWidth, 16);
            mc.fontRendererObj.drawString("ARMOR", 13, y + 4, YELLOW);
            mc.fontRendererObj.drawString(armorCount + "/4", 53, y + 4, WHITE);
            y += 21;
        }

        if (ModuleManager.isEnabled("clock")) {
            String time = "LOCAL TIME  " + clockFormat.format(new Date());
            int panelWidth = mc.fontRendererObj.getStringWidth(time) + 18;
            drawPanel(7, y, panelWidth, 16);
            mc.fontRendererObj.drawString("LOCAL TIME", 13, y + 4, YELLOW);
            mc.fontRendererObj.drawString(clockFormat.format(new Date()), 79, y + 4, WHITE);
        }

        if (ModuleManager.isEnabled("potions")) drawPotionEffects();
        if (ModuleManager.isEnabled("keystrokes")) drawKeystrokes(new ScaledResolution(mc));
    }

    private String getDirection(float yaw) {
        String[] directions = {"S", "SW", "W", "NW", "N", "NE", "E", "SE"};
        int index = (int) Math.floor((yaw * -1.0F / 45.0F) + 0.5D) & 7;
        return directions[index];
    }

    private void drawPotionEffects() {
        int y = 8;
        int count = 0;
        for (Object object : mc.thePlayer.getActivePotionEffects()) {
            if (!(object instanceof PotionEffect) || count >= 4) continue;
            PotionEffect effect = (PotionEffect) object;
            String name = effect.getEffectName();
            String duration = PotionEffect.getDurationString(effect);
            String text = name + "  " + duration;
            int panelWidth = mc.fontRendererObj.getStringWidth(text) + 18;
            int x = mc.displayWidth / 2;
            ScaledResolution sr = new ScaledResolution(mc);
            x = sr.getScaledWidth() - panelWidth - 7;
            drawPanel(x, y, panelWidth, 16);
            mc.fontRendererObj.drawString(name, x + 6, y + 4, YELLOW);
            int durationWidth = mc.fontRendererObj.getStringWidth(duration);
            mc.fontRendererObj.drawString(duration, x + panelWidth - durationWidth - 6, y + 4, MUTED);
            y += 20;
            count++;
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
