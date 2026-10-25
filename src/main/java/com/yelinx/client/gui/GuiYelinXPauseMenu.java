package com.yelinx.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptions;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiYelinXPauseMenu extends GuiIngameMenu {
    @Override
    public void initGui() {
        buttonList.clear();
        int buttonWidth = Math.min(260, width - 40);
        int x = (width - buttonWidth) / 2;
        int startY = height / 2 - 36;
        int gap = 27;
        buttonList.add(new YelinXButton(0, x, startY, buttonWidth, 22, "BACK TO GAME"));
        buttonList.add(new YelinXButton(1, x, startY + gap, buttonWidth, 22, "MODULES"));
        buttonList.add(new YelinXButton(2, x, startY + gap * 2, buttonWidth, 22, "OPTIONS"));
        buttonList.add(new YelinXButton(3, x, startY + gap * 3, buttonWidth, 22, "SAVE & QUIT TO TITLE"));
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
            default: super.actionPerformed(button);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawRect(0, 0, width, height, 0x55000000);
        int panelWidth = Math.min(340, width - 24);
        int left = (width - panelWidth) / 2;
        int top = Math.max(16, height / 2 - 100);
        drawRect(left, top, left + panelWidth, top + 58, 0xD8202020);
        drawRect(left, top, left + panelWidth, top + 3, 0xFFFFD600);
        drawCenteredString(fontRendererObj, "GAME PAUSED", width / 2, top + 14, 0xFFFFD600);
        drawCenteredString(fontRendererObj, "YelinX Client", width / 2, top + 32, 0xFFF5F5F5);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
            mc.setIngameFocus();
        } else super.keyTyped(typedChar, keyCode);
    }
}
