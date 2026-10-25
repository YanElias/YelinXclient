package com.yelinx.client;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientLifecycle {
    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        if (event.world != null && event.world.isRemote) {
            ClientModule fullbright = ModuleManager.get("fullbright");
            if (fullbright != null) fullbright.setEnabled(false);
        }
    }
}
