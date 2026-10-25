package com.yelinx.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiSelectWorld;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiYelinXMainMenu extends GuiMainMenu {
    @Override
    public void initGui() {
        buttonList.clear();
        int buttonWidth = Math.min(260, width - 40);
        int x = (width - buttonWidth) / 2;
        int startY = height / 2 - 8;
        int gap = 27;
        buttonList.add(new YelinXButton(0, x, startY, buttonWidth, 22, "SINGLEPLAYER"));
        buttonList.add(new YelinXButton(1, x, startY + gap, buttonWidth, 22, "MULTIPLAYER"));
        buttonList.add(new YelinXButton(2, x, startY + gap * 2, buttonWidth, 22, "OPTIONS"));
        buttonList.add(new YelinXButton(3, x, startY + gap * 3, buttonWidth, 22, "LANGUAGE"));
        buttonList.add(new YelinXButton(4, x, startY + gap * 4, buttonWidth, 22, "QUIT"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0: mc.displayGuiScreen(new GuiSelectWorld(this)); break;
            case 1: mc.displayGuiScreen(new GuiMultiplayer(this)); break;
            case 2: mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings)); break;
            case 3: mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager())); break;
            case 4: mc.shutdown(); break;
            default: super.actionPerformed(button);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawRect(0, 0, width, height, 0x55000000);
        int panelWidth = Math.min(340, width - 24);
        int left = (width - panelWidth) / 2;
        int top = Math.max(18, height / 2 - 112);
        drawRect(left, top, left + panelWidth, top + 84, 0xD8202020);
        drawRect(left, top, left + panelWidth, top + 3, 0xFFFFD600);
        drawCenteredString(fontRendererObj, "YelinX", width / 2, top + 17, 0xFFFFD600);
        drawCenteredString(fontRendererObj, "CLIENT", width / 2, top + 34, 0xFFF5F5F5);
        drawCenteredString(fontRendererObj, "YOUR GAME. YOUR STYLE.", width / 2, top + 57, 0xFFBDBDBD);
        drawString(fontRendererObj, "Minecraft 1.8.9", 8, height - 18, 0xFFBDBDBD);
        drawString(fontRendererObj, "YelinX 0.2.0", width - fontRendererObj.getStringWidth("YelinX 0.2.0") - 8, height - 18, 0xFFFFD600);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) return;
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }
}
