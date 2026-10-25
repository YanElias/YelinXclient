package com.yelinx.client.gui;

import com.yelinx.client.ClientModule;
import com.yelinx.client.ModuleManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GuiModules extends GuiScreen {
    private static final String[] CATEGORIES = {"ALL", "HUD", "VISUAL", "PLAYER", "UTILITY"};
    private final GuiScreen parent;
    private final List<ClientModule> displayedModules = new ArrayList<ClientModule>();
    private String selectedCategory = "ALL";
    private String searchText = "";
    private boolean searchFocused;
    private int panelLeft;
    private int panelTop;
    private int panelWidth;
    private int panelHeight;
    private int sidebarWidth;
    private int searchLeft;
    private int searchTop;
    private int searchWidth;
    private int cardHeight;

    public GuiModules(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        buttonList.clear();
        displayedModules.clear();
        panelWidth = Math.min(720, width - 16);
        panelHeight = Math.min(450, height - 16);
        panelLeft = (width - panelWidth) / 2;
        panelTop = (height - panelHeight) / 2;
        sidebarWidth = Math.min(132, panelWidth / 4);

        for (ClientModule module : ModuleManager.getModules()) {
            boolean categoryMatches = selectedCategory.equals("ALL")
                    || module.getCategory().equalsIgnoreCase(selectedCategory);
            boolean searchMatches = searchText.length() == 0
                    || module.getName().toLowerCase(Locale.ROOT).contains(searchText.toLowerCase(Locale.ROOT))
                    || module.getDescription().toLowerCase(Locale.ROOT).contains(searchText.toLowerCase(Locale.ROOT));
            if (categoryMatches && searchMatches) displayedModules.add(module);
        }

        int sideX = panelLeft + 10;
        int sideButtonWidth = sidebarWidth - 20;
        for (int i = 0; i < CATEGORIES.length; i++) {
            buttonList.add(new YelinXButton(10 + i, sideX, panelTop + 91 + i * 32,
                    sideButtonWidth, 25, CATEGORIES[i], selectedCategory.equals(CATEGORIES[i])));
        }

        int contentLeft = panelLeft + sidebarWidth + 14;
        int contentWidth = panelLeft + panelWidth - contentLeft - 14;
        searchLeft = contentLeft;
        searchTop = panelTop + 53;
        searchWidth = contentWidth;
        int rows = Math.max(1, (displayedModules.size() + 1) / 2);
        int availableHeight = panelHeight - 145;
        cardHeight = Math.max(39, Math.min(52, (availableHeight - (rows - 1) * 6) / rows));
        int cardWidth = (contentWidth - 8) / 2;
        int startY = panelTop + 91;

        for (int i = 0; i < displayedModules.size(); i++) {
            int col = i % 2;
            int row = i / 2;
            int x = contentLeft + col * (cardWidth + 8);
            int y = startY + row * (cardHeight + 6);
            buttonList.add(new YelinXModuleButton(100 + i, x, y, cardWidth, cardHeight, displayedModules.get(i)));
        }

        buttonList.add(new YelinXButton(1, sideX, panelTop + panelHeight - 38,
                sideButtonWidth, 26, "BACK"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id >= 10 && button.id < 10 + CATEGORIES.length) {
            selectedCategory = CATEGORIES[button.id - 10];
            initGui();
        } else if (button.id >= 100 && button.id < 100 + displayedModules.size()) {
            int index = button.id - 100;
            ModuleManager.toggle(displayedModules.get(index).getId());
            initGui();
        } else if (button.id == 1) {
            mc.displayGuiScreen(parent);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        searchFocused = mouseX >= searchLeft && mouseX <= searchLeft + searchWidth
                && mouseY >= searchTop && mouseY <= searchTop + 21;
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            if (searchFocused) {
                searchFocused = false;
            } else {
                mc.displayGuiScreen(parent);
            }
            return;
        }

        if (searchFocused) {
            if (keyCode == Keyboard.KEY_BACK) {
                if (searchText.length() > 0) searchText = searchText.substring(0, searchText.length() - 1);
            } else if (keyCode == Keyboard.KEY_RETURN || keyCode == Keyboard.KEY_NUMPADENTER) {
                searchFocused = false;
            } else if (typedChar >= 32 && typedChar < 127 && searchText.length() < 32) {
                searchText += typedChar;
            }
            initGui();
            searchFocused = keyCode != Keyboard.KEY_RETURN && keyCode != Keyboard.KEY_NUMPADENTER;
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawRect(0, 0, width, height, 0x88000000);
        drawRect(panelLeft, panelTop, panelLeft + panelWidth, panelTop + panelHeight, 0xF5151618);
        drawRect(panelLeft, panelTop, panelLeft + panelWidth, panelTop + 3, 0xFFFFD600);
        drawRect(panelLeft, panelTop + 3, panelLeft + 4, panelTop + panelHeight, 0xFFFFD600);
        drawRect(panelLeft + sidebarWidth, panelTop + 14, panelLeft + sidebarWidth + 1,
                panelTop + panelHeight - 14, 0xFF333438);

        drawString(fontRendererObj, "YelinX", panelLeft + 14, panelTop + 17, 0xFFFFD600);
        drawString(fontRendererObj, "CLIENT", panelLeft + 14, panelTop + 31, 0xFFF5F5F5);
        drawString(fontRendererObj, "MODULES", panelLeft + 14, panelTop + 58, 0xFF888A90);

        int contentLeft = panelLeft + sidebarWidth + 14;
        drawString(fontRendererObj, "MODULE LIBRARY", contentLeft, panelTop + 17, 0xFFF5F5F5);
        drawString(fontRendererObj, "Find a tool and customize your game.", contentLeft, panelTop + 32, 0xFF999BA1);

        drawRect(searchLeft, searchTop, searchLeft + searchWidth, searchTop + 21, 0xFF222326);
        drawRect(searchLeft, searchTop, searchLeft + 2, searchTop + 21,
                searchFocused ? 0xFFFFD600 : 0xFF55565A);
        String searchLabel = searchText.length() == 0 ? "SEARCH MODULES..." : searchText;
        drawString(fontRendererObj, searchLabel, searchLeft + 9, searchTop + 7,
                searchText.length() == 0 ? 0xFF77797F : 0xFFF5F5F5);
        if (searchFocused && (System.currentTimeMillis() / 500L) % 2L == 0L) {
            int cursorX = searchLeft + 9 + fontRendererObj.getStringWidth(searchText);
            drawRect(cursorX, searchTop + 5, cursorX + 1, searchTop + 16, 0xFFFFD600);
        }

        String count = displayedModules.size() + " MODULES";
        drawString(fontRendererObj, count,
                panelLeft + panelWidth - fontRendererObj.getStringWidth(count) - 14,
                panelTop + panelHeight - 21, 0xFF77797F);

        if (displayedModules.isEmpty()) {
            drawCenteredString(fontRendererObj, "NO MODULES FOUND",
                    contentLeft + (panelWidth - sidebarWidth - 28) / 2,
                    panelTop + 150, 0xFFB8B8B8);
            drawCenteredString(fontRendererObj, "Try another search or category.",
                    contentLeft + (panelWidth - sidebarWidth - 28) / 2,
                    panelTop + 165, 0xFF77797F);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }
}
