package com.yelinx.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiYelinXPauseMenu extends GuiScreen {
    @Override
    public void initGui() {
        buttonList.clear();
        int buttonWidth = Math.min(260, width - 40);
        int x = (width - buttonWidth) / 2;
        int startY = height / 2 - 32;
        int gap = 26;
        buttonList.add(new YelinXButton(0, x, startY, buttonWidth, 21, "BACK TO GAME"));
        buttonList.add(new YelinXButton(1, x, startY + gap, buttonWidth, 21, "MODULES"));
        buttonList.add(new YelinXButton(2, x, startY + gap * 2, buttonWidth, 21, "OPTIONS"));
        buttonList.add(new YelinXButton(3, x, startY + gap * 3, buttonWidth, 21, "SAVE & QUIT TO TITLE"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(null);
                mc.setIngameFocus();
                break;
            case 1:
                mc.displayGuiScreen(new GuiModules(this));
                break;
            case 2:
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                break;
            case 3:
                if (mc.theWorld != null) {
                    mc.theWorld.sendQuittingDisconnectingPacket();
                    mc.loadWorld(null);
                }
                mc.displayGuiScreen(new GuiYelinXMainMenu());
                break;
            default: break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawRect(0, 0, width, height, 0x55000000);
        int panelWidth = Math.min(340, width - 24);
        int left = (width - panelWidth) / 2;
        int top = Math.max(12, height / 2 - 93);
        drawRect(left, top, left + panelWidth, top + 54, 0xD8202020);
        drawRect(left, top, left + panelWidth, top + 3, 0xFFFFD600);
        drawCenteredString(fontRendererObj, "GAME PAUSED", width / 2, top + 12, 0xFFFFD600);
        drawCenteredString(fontRendererObj, "YelinX Client", width / 2, top + 30, 0xFFF5F5F5);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
            mc.setIngameFocus();
        } else super.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() { return true; }
}
