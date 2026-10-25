package com.yelinx.client.gui;

import com.yelinx.client.ClientModule;
import com.yelinx.client.ModuleManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiModules extends GuiScreen {
    private final GuiScreen parent;

    public GuiModules(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        buttonList.clear();
        int panelWidth = Math.min(380, width - 24);
        int left = (width - panelWidth) / 2;
        int panelHeight = 100 + ModuleManager.getModules().size() * 40;
        int top = Math.max(12, (height - panelHeight) / 2);
        int buttonWidth = panelWidth - 32;
        int y = top + 48;
        int id = 100;
        for (ClientModule module : ModuleManager.getModules()) {
            buttonList.add(new YelinXButton(id++, left + 16, y, buttonWidth, 22,
                    module.getName() + "  [" + (module.isEnabled() ? "ON" : "OFF") + "]"));
            y += 40;
        }
        buttonList.add(new YelinXButton(1, left + 16, Math.min(height - 32, y + 2), buttonWidth, 22, "BACK"));
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
        int panelWidth = Math.min(380, width - 24);
        int left = (width - panelWidth) / 2;
        int panelHeight = 100 + ModuleManager.getModules().size() * 40;
        int top = Math.max(12, (height - panelHeight) / 2);
        drawRect(left, top, left + panelWidth, top + panelHeight, 0xF0202020);
        drawRect(left, top, left + panelWidth, top + 3, 0xFFFFD600);
        drawCenteredString(fontRendererObj, "YelinX MODULES", width / 2, top + 13, 0xFFFFD600);
        drawCenteredString(fontRendererObj, "Click a module to enable or disable it", width / 2, top + 29, 0xFFF5F5F5);
        int i = 0;
        for (ClientModule module : ModuleManager.getModules()) {
            drawCenteredString(fontRendererObj, module.getDescription(), width / 2, top + 73 + i * 40, 0xFFBDBDBD);
            i++;
        }
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
