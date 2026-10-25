package com.yelinx.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class YelinXButton extends GuiButton {
    private static final int YELLOW = 0xFFFFD600;
    private static final int GRAPHITE = 0xFF292929;
    private static final int HOVER = 0xFF45402B;
    private static final int MUTED = 0xFF777777;

    public YelinXButton(int id, int x, int y, int width, int height, String text) {
        super(id, x, y, width, height, text);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (!visible) return;
        this.hovered = mouseX >= xPosition && mouseY >= yPosition
                && mouseX < xPosition + width && mouseY < yPosition + height;
        int background = !enabled ? 0xFF202020 : (hovered ? HOVER : GRAPHITE);
        drawRect(xPosition, yPosition, xPosition + width, yPosition + height, background);
        drawRect(xPosition, yPosition, xPosition + 3, yPosition + height, enabled ? YELLOW : MUTED);
        int color = enabled ? (hovered ? YELLOW : 0xFFF5F5F5) : MUTED;
        drawCenteredString(mc.fontRendererObj, displayString, xPosition + width / 2,
                yPosition + (height - 8) / 2, color);
    }
}
