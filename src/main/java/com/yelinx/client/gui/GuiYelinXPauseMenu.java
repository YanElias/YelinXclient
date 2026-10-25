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
    private int panelHeight;

    @Override
    public void initGui() {
        buttonList.clear();
        panelWidth = Math.min(440, width - 24);
        panelHeight = Math.min(250, height - 24);
        panelLeft = (width - panelWidth) / 2;
        panelTop = (height - panelHeight) / 2;

        int gap = 8;
        int buttonWidth = (panelWidth - 44) / 2;
        int leftX = panelLeft + 14;
        int rightX = leftX + buttonWidth + gap;
        int startY = panelTop + 105;
        buttonList.add(new YelinXButton(0, leftX, startY, buttonWidth, 28, "BACK TO GAME"));
        buttonList.add(new YelinXButton(1, rightX, startY, buttonWidth, 28, "MODULES"));
        buttonList.add(new YelinXButton(2, leftX, startY + 36, buttonWidth, 28, "SETTINGS"));
        buttonList.add(new YelinXButton(3, rightX, startY + 36, buttonWidth, 28, "SAVE & QUIT"));
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
        drawRect(0, 0, width, height, 0x78000000);
        drawRect(panelLeft, panelTop, panelLeft + panelWidth, panelTop + panelHeight, 0xF5161719);
        drawRect(panelLeft, panelTop, panelLeft + panelWidth, panelTop + 3, 0xFFFFD21C);
        drawRect(panelLeft, panelTop + 3, panelLeft + 4, panelTop + panelHeight, 0xFFFFD21C);

        drawString(fontRendererObj, "GAME PAUSED", panelLeft + 16, panelTop + 18, 0xFFFFD21C);
        drawString(fontRendererObj, "Take a break. Your world is safe.", panelLeft + 16, panelTop + 35, 0xFFF4F4F4);
        drawString(fontRendererObj, "YelinX Client  /  Minecraft 1.8.9", panelLeft + 16, panelTop + 53, 0xFF929499);
        drawRect(panelLeft + 14, panelTop + 76, panelLeft + panelWidth - 14, panelTop + 77, 0xFF393A3D);
        drawString(fontRendererObj, "QUICK ACTIONS", panelLeft + 16, panelTop + 87, 0xFFB8BABE);

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
