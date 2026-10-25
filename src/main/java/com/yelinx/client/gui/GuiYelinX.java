package com.yelinx.client.gui;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiYelinX extends GuiScreen {
    private static final int YELLOW = 0xFFFFD600;
    private static final int GRAPHITE = 0xFF202020;
    private static final int PANEL = 0xFF292929;
    private static final int WHITE = 0xFFF5F5F5;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        int left = width / 2 - 150;
        int top = height / 2 - 105;
        int right = width / 2 + 150;
        int bottom = height / 2 + 105;

        drawRect(left, top, right, bottom, GRAPHITE);
        drawRect(left, top, right, top + 3, YELLOW);

        drawCenteredString(fontRendererObj, "YelinX Client", width / 2, top + 16, YELLOW);
        drawCenteredString(fontRendererObj, "YOUR GAME. YOUR STYLE.", width / 2, top + 34, WHITE);

        drawRect(left + 18, top + 58, right - 18, top + 86, PANEL);
        drawCenteredString(fontRendererObj, "Client menu foundation", width / 2, top + 68, WHITE);

        drawRect(left + 18, top + 94, right - 18, top + 122, PANEL);
        drawCenteredString(fontRendererObj, "More modules coming soon", width / 2, top + 104, WHITE);

        drawCenteredString(fontRendererObj, "Press ESC to return", width / 2, bottom - 20, 0xFFBDBDBD);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
