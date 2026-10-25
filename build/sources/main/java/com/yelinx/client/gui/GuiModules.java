package com.yelinx.client.gui;

import com.yelinx.client.ClientModule;
import com.yelinx.client.ModuleManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiModules extends GuiScreen {
    private final GuiScreen parent;
    private int panelLeft;
    private int panelTop;
    private int panelWidth;

    public GuiModules(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        buttonList.clear();
        panelWidth = Math.min(540, width - 24);
        panelLeft = (width - panelWidth) / 2;
        panelTop = Math.max(8, (height - 320) / 2);

        int gap = 10;
        int cardWidth = (panelWidth - 42) / 2;
        int leftX = panelLeft + 14;
        int rightX = leftX + cardWidth + gap;
        int startY = panelTop + 69;
        int cardHeight = 70;
        int rowGap = 9;

        int i = 0;
        for (ClientModule module : ModuleManager.getModules()) {
            int col = i % 2;
            int row = i / 2;
            int x = col == 0 ? leftX : rightX;
            int y = startY + row * (cardHeight + rowGap);
            buttonList.add(new YelinXModuleButton(100 + i, x, y, cardWidth, cardHeight, module));
            i++;
        }
        buttonList.add(new YelinXButton(1, panelLeft + 14, panelTop + 280, panelWidth - 28, 26, "BACK TO MENU"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id >= 100 && button.id < 100 + ModuleManager.getModules().size()) {
            int index = button.id - 100;
            ModuleManager.toggle(ModuleManager.getModules().get(index).getId());
            initGui();
        } else if (button.id == 1) {
            mc.displayGuiScreen(parent);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawRect(0, 0, width, height, 0x70000000);
        drawRect(panelLeft, panelTop, panelLeft + panelWidth, panelTop + 320, 0xF5161719);
        drawRect(panelLeft, panelTop, panelLeft + panelWidth, panelTop + 3, 0xFFFFD21C);
        drawRect(panelLeft, panelTop + 3, panelLeft + 4, panelTop + 320, 0xFFFFD21C);

        drawString(fontRendererObj, "MODULES", panelLeft + 16, panelTop + 15, 0xFFFFD21C);
        drawString(fontRendererObj, "Turn your tools on or off.", panelLeft + 16, panelTop + 32, 0xFFB8BABE);
        String count = ModuleManager.getModules().size() + " AVAILABLE";
        drawString(fontRendererObj, count,
                panelLeft + panelWidth - fontRendererObj.getStringWidth(count) - 16,
                panelTop + 21, 0xFFF4F4F4);
        drawRect(panelLeft + 14, panelTop + 54, panelLeft + panelWidth - 14, panelTop + 55, 0xFF393A3D);
        drawString(fontRendererObj, "CLICK A CARD TO TOGGLE A MODULE", panelLeft + 16, panelTop + 263, 0xFF929499);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) mc.displayGuiScreen(parent);
        else super.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }
}
