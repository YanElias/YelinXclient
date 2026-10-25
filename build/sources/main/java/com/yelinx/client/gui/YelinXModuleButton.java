package com.yelinx.client.gui;

import com.yelinx.client.ClientModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.util.List;

public class YelinXModuleButton extends GuiButton {
    private final ClientModule module;

    public YelinXModuleButton(int id, int x, int y, int width, int height, ClientModule module) {
        super(id, x, y, width, height, "");
        this.module = module;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (!visible) return;
        hovered = mouseX >= xPosition && mouseY >= yPosition
                && mouseX < xPosition + width && mouseY < yPosition + height;

        boolean enabled = module.isEnabled();
        int background = hovered ? 0xFF303030 : 0xFF252525;
        drawRect(xPosition, yPosition, xPosition + width, yPosition + height, background);
        drawRect(xPosition, yPosition, xPosition + 3, yPosition + height,
                enabled ? 0xFFFFD600 : 0xFF555555);

        mc.fontRendererObj.drawString(module.getName(), xPosition + 12, yPosition + 10,
                enabled ? 0xFFFFD600 : 0xFFF5F5F5);

        String state = enabled ? "ON" : "OFF";
        int stateWidth = mc.fontRendererObj.getStringWidth(state) + 12;
        int stateLeft = xPosition + width - stateWidth - 9;
        drawRect(stateLeft, yPosition + 7, stateLeft + stateWidth, yPosition + 20,
                enabled ? 0xFFFFD600 : 0xFF414141);
        mc.fontRendererObj.drawString(state, stateLeft + 6, yPosition + 10,
                enabled ? 0xFF202020 : 0xFFBDBDBD);

        List<String> lines = mc.fontRendererObj.listFormattedStringToWidth(
                module.getDescription(), Math.max(40, width - 24));
        int lineY = yPosition + 28;
        int maxLines = height >= 47 ? 2 : 1;
        for (int i = 0; i < lines.size() && i < maxLines; i++) {
            mc.fontRendererObj.drawString(lines.get(i), xPosition + 12, lineY + i * 9, 0xFFBDBDBD);
        }
    }
}
