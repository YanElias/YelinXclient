package com.yelinx.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiYelinXPauseMenu extends GuiScreen {
    private int panelLeft;
    private int panelTop;
    private int panelWidth;

    @Override
    public void initGui() {
        buttonList.clear();
        panelWidth = Math.min(420, width - 24);
        panelLeft = (width - panelWidth) / 2;
        panelTop = Math.max(12, (height - 224) / 2);

        int gap = 8;
        int buttonWidth = (panelWidth - 44) / 2;
        int leftX = panelLeft + 14;
        int rightX = leftX + buttonWidth + gap;
        int startY = panelTop + 91;
        buttonList.add(new YelinXButton(0, leftX, startY, buttonWidth, 30, "BACK TO GAME"));
        buttonList.add(new YelinXButton(1, rightX, startY, buttonWidth, 30, "MODULES"));
        buttonList.add(new YelinXButton(2, leftX, startY + 40, buttonWidth, 30, "OPTIONS"));
        buttonList.add(new YelinXButton(3, rightX, startY + 40, buttonWidth, 30, "SAVE & QUIT"));
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
        drawRect(0, 0, width, height, 0x77000000);
        drawRect(panelLeft, panelTop, panelLeft + panelWidth, panelTop + 184, 0xF51B1B1B);
        drawRect(panelLeft, panelTop, panelLeft + panelWidth, panelTop + 3, 0xFFFFD600);
        drawString(fontRendererObj, "PAUSED", panelLeft + 16, panelTop + 17, 0xFFFFD600);
        drawString(fontRendererObj, "YelinX Client", panelLeft + 16, panelTop + 33, 0xFFF5F5F5);
        drawString(fontRendererObj, "Your world is waiting.", panelLeft + 16, panelTop + 52, 0xFFBDBDBD);
        drawRect(panelLeft + 14, panelTop + 72, panelLeft + panelWidth - 14, panelTop + 73, 0xFF393939);
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
