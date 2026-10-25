package com.yelinx.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiYelinXMainMenu extends GuiScreen {
    private int panelLeft;
    private int panelTop;
    private int panelWidth;
    private int panelHeight;

    @Override
    public void initGui() {
        buttonList.clear();
        panelWidth = Math.min(580, width - 24);
        panelHeight = Math.min(310, height - 24);
        panelLeft = (width - panelWidth) / 2;
        panelTop = (height - panelHeight) / 2;

        int menuWidth = Math.min(154, panelWidth / 3);
        int leftX = panelLeft + 16;
        int startY = panelTop + 91;
        int rowGap = 29;
        int buttonHeight = 25;

        buttonList.add(new YelinXButton(0, leftX, startY, menuWidth, buttonHeight, "SINGLEPLAYER"));
        buttonList.add(new YelinXButton(1, leftX, startY + rowGap, menuWidth, buttonHeight, "MULTIPLAYER"));
        buttonList.add(new YelinXButton(2, leftX, startY + rowGap * 2, menuWidth, buttonHeight, "MODULES"));
        buttonList.add(new YelinXButton(3, leftX, startY + rowGap * 3, menuWidth, buttonHeight, "SETTINGS"));
        buttonList.add(new YelinXButton(4, leftX, startY + rowGap * 4, menuWidth, buttonHeight, "LANGUAGE"));
        buttonList.add(new YelinXButton(5, leftX, startY + rowGap * 5, menuWidth, buttonHeight, "QUIT GAME"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0: mc.displayGuiScreen(new GuiSelectWorld(this)); break;
            case 1: mc.displayGuiScreen(new GuiMultiplayer(this)); break;
            case 2: mc.displayGuiScreen(new GuiModules(this)); break;
            case 3: mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings)); break;
            case 4: mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager())); break;
            case 5: mc.shutdown(); break;
            default: break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawRect(0, 0, width, height, 0x70000000);

        drawRect(panelLeft, panelTop, panelLeft + panelWidth, panelTop + panelHeight, 0xF5161719);
        drawRect(panelLeft, panelTop, panelLeft + panelWidth, panelTop + 3, 0xFFFFD21C);
        drawRect(panelLeft, panelTop + 3, panelLeft + 4, panelTop + panelHeight, 0xFFFFD21C);

        int dividerX = panelLeft + Math.min(186, panelWidth / 3 + 22);
        drawRect(dividerX, panelTop + 16, dividerX + 1, panelTop + panelHeight - 16, 0xFF393A3D);

        drawString(fontRendererObj, "YelinX", panelLeft + 17, panelTop + 17, 0xFFFFD21C);
        drawString(fontRendererObj, "CLIENT", panelLeft + 17, panelTop + 31, 0xFFF4F4F4);
        drawString(fontRendererObj, "MINECRAFT 1.8.9", panelLeft + 17, panelTop + 49, 0xFF929499);
        drawRect(panelLeft + 16, panelTop + 72, dividerX - 12, panelTop + 73, 0xFF393A3D);
        drawString(fontRendererObj, "PLAY", panelLeft + 17, panelTop + 79, 0xFFB8BABE);

        int contentX = dividerX + 18;
        int contentWidth = panelLeft + panelWidth - contentX - 16;
        int contentTop = panelTop + 22;
        drawString(fontRendererObj, "YOUR GAME. YOUR WAY.", contentX, contentTop, 0xFFF4F4F4);
        drawString(fontRendererObj, "A cleaner Minecraft experience.", contentX, contentTop + 15, 0xFFB8BABE);

        int cardTop = contentTop + 43;
        drawRect(contentX, cardTop, contentX + contentWidth, cardTop + 61, 0xFF222326);
        drawRect(contentX, cardTop, contentX + 3, cardTop + 61, 0xFFFFD21C);
        drawString(fontRendererObj, "BUILT FOR YOUR NEXT WORLD", contentX + 12, cardTop + 10, 0xFFFFD21C);
        drawString(fontRendererObj, "Lightweight tools, right when you", contentX + 12, cardTop + 27, 0xFFE1E1E1);
        drawString(fontRendererObj, "need them.", contentX + 12, cardTop + 39, 0xFFE1E1E1);

        int featureTop = cardTop + 76;
        drawString(fontRendererObj, "CLIENT FEATURES", contentX, featureTop, 0xFFB8BABE);
        drawFeature(contentX, featureTop + 17, "01", "HUD", "FPS and coordinates");
        drawFeature(contentX, featureTop + 39, "02", "MODULES", "Quick toggles in-game");

        String version = "YELINX 0.2.0";
        drawString(fontRendererObj, version,
                panelLeft + panelWidth - fontRendererObj.getStringWidth(version) - 16,
                panelTop + panelHeight - 17, 0xFFFFD21C);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawFeature(int x, int y, String number, String title, String description) {
        drawString(fontRendererObj, number, x, y, 0xFFFFD21C);
        drawString(fontRendererObj, title, x + 22, y, 0xFFF4F4F4);
        drawString(fontRendererObj, description, x + 22, y + 10, 0xFF929499);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode != Keyboard.KEY_ESCAPE) super.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }
}
