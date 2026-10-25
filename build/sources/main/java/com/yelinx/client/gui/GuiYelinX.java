package com.yelinx.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiYelinX extends GuiScreen {
    @Override
    public void initGui() {
        buttonList.clear();
        int buttonWidth = Math.min(280, width - 36);
        int x = (width - buttonWidth) / 2;
        int y = height / 2 - 15;
        buttonList.add(new YelinXButton(0, x, y, buttonWidth, 23, "MODULES"));
        buttonList.add(new YelinXButton(1, x, y + 29, buttonWidth, 23, "OPTIONS"));
        buttonList.add(new YelinXButton(2, x, y + 58, buttonWidth, 23, "CLOSE"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) mc.displayGuiScreen(new GuiModules(this));
        else if (button.id == 1) mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
        else if (button.id == 2) mc.displayGuiScreen(null);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        int panelWidth = Math.min(330, width - 24);
        int left = (width - panelWidth) / 2;
        int top = height / 2 - 92;
        drawRect(left, top, left + panelWidth, top + 60, 0xF0202020);
        drawRect(left, top, left + panelWidth, top + 3, 0xFFFFD600);
        drawCenteredString(fontRendererObj, "YelinX Client", width / 2, top + 15, 0xFFFFD600);
        drawCenteredString(fontRendererObj, "RIGHT SHIFT  /  CLIENT MENU", width / 2, top + 34, 0xFFF5F5F5);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) mc.displayGuiScreen(null);
        else super.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }
}
