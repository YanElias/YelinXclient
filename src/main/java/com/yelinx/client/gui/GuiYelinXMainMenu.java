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

    @Override
    public void initGui() {
        buttonList.clear();
        panelWidth = Math.min(500, width - 24);
        panelLeft = (width - panelWidth) / 2;
        panelTop = Math.max(12, (height - 240) / 2);

        int gap = 8;
        int buttonWidth = (panelWidth - 44) / 2;
        int leftX = panelLeft + 14;
        int rightX = leftX + buttonWidth + gap;
        int startY = panelTop + 94;
        int rowGap = 36;

        buttonList.add(new YelinXButton(0, leftX, startY, buttonWidth, 28, "SINGLEPLAYER"));
        buttonList.add(new YelinXButton(1, rightX, startY, buttonWidth, 28, "MULTIPLAYER"));
        buttonList.add(new YelinXButton(2, leftX, startY + rowGap, buttonWidth, 28, "YELINX MODULES"));
        buttonList.add(new YelinXButton(3, rightX, startY + rowGap, buttonWidth, 28, "OPTIONS"));
        buttonList.add(new YelinXButton(4, leftX, startY + rowGap * 2, buttonWidth, 28, "LANGUAGE"));
        buttonList.add(new YelinXButton(5, rightX, startY + rowGap * 2, buttonWidth, 28, "QUIT"));
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
        drawRect(0, 0, width, height, 0x66000000);

        drawRect(panelLeft, panelTop, panelLeft + panelWidth, panelTop + 240, 0xF51B1B1B);
        drawRect(panelLeft, panelTop, panelLeft + panelWidth, panelTop + 3, 0xFFFFD600);
        drawRect(panelLeft + 14, panelTop + 15, panelLeft + 17, panelTop + 65, 0xFFFFD600);

        drawString(fontRendererObj, "YelinX", panelLeft + 27, panelTop + 17, 0xFFFFD600);
        drawString(fontRendererObj, "CLIENT", panelLeft + 27, panelTop + 31, 0xFFF5F5F5);
        drawString(fontRendererObj, "A cleaner way to play.", panelLeft + 27, panelTop + 49, 0xFFBDBDBD);
        drawRect(panelLeft + 14, panelTop + 77, panelLeft + panelWidth - 14, panelTop + 78, 0xFF393939);
        drawString(fontRendererObj, "QUICK ACCESS", panelLeft + 14, panelTop + 84, 0xFFBDBDBD);

        String gameVersion = "MINECRAFT 1.8.9";
        String clientVersion = "YELINX 0.2.0";
        drawString(fontRendererObj, gameVersion, 8, height - 16, 0xFFBDBDBD);
        drawString(fontRendererObj, clientVersion, width - fontRendererObj.getStringWidth(clientVersion) - 8,
                height - 16, 0xFFFFD600);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode != Keyboard.KEY_ESCAPE) super.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }
}
