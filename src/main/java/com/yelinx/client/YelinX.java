package com.yelinx.client;

import com.yelinx.client.gui.GuiYelinX;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

@Mod(modid = YelinX.MODID, name = YelinX.NAME, version = YelinX.VERSION, acceptedMinecraftVersions = "[1.8.9]")
public class YelinX {
    public static final String MODID = "yelinx";
    public static final String NAME = "YelinX Client";
    public static final String VERSION = "0.3.0";

    private static KeyBinding openMenuKey;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        openMenuKey = new KeyBinding("key.yelinx.open_menu", Keyboard.KEY_RSHIFT, "key.categories.yelinx");
        ClientRegistry.registerKeyBinding(openMenuKey);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new HudOverlay());
        MinecraftForge.EVENT_BUS.register(new ClientGuiHandler());
        MinecraftForge.EVENT_BUS.register(new ClientLifecycle());
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (openMenuKey != null && openMenuKey.isPressed()) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.thePlayer != null) mc.displayGuiScreen(new GuiYelinX());
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.theWorld == null) return;
        FreelookModule.update();
        if (ModuleManager.isEnabled("togglesprint")
                && mc.gameSettings.keyBindForward.isKeyDown()
                && !mc.gameSettings.keyBindSneak.isKeyDown()
                && mc.thePlayer.getFoodStats().getFoodLevel() > 6) {
            mc.thePlayer.setSprinting(true);
        }
    }
}
