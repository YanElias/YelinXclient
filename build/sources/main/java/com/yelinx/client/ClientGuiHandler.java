package com.yelinx.client;

import com.yelinx.client.gui.GuiYelinXMainMenu;
import com.yelinx.client.gui.GuiYelinXPauseMenu;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientGuiHandler {
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (event.gui instanceof GuiYelinXMainMenu || event.gui instanceof GuiYelinXPauseMenu) {
            return;
        }
        if (event.gui instanceof GuiMainMenu) {
            event.gui = new GuiYelinXMainMenu();
        } else if (event.gui instanceof GuiIngameMenu) {
            event.gui = new GuiYelinXPauseMenu();
        }
    }
}
