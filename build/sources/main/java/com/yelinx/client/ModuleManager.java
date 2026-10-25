package com.yelinx.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModuleManager {
    private static final List<ClientModule> MODULES = new ArrayList<ClientModule>();

    static {
        MODULES.add(new ClientModule("fps", "FPS Counter", "Mostra FPS no HUD.", "HUD"));
        MODULES.add(new ClientModule("coordinates", "Coordinates", "Mostra suas coordenadas XYZ.", "HUD"));
        MODULES.add(new ClientModule("keystrokes", "Keystrokes", "Mostra teclas e botões pressionados.", "HUD"));
        MODULES.add(new ClientModule("direction", "Direction HUD", "Mostra a direção para onde você olha.", "HUD"));
        MODULES.add(new ClientModule("armor", "Armor Status", "Mostra quantas peças de armadura você usa.", "HUD"));
        MODULES.add(new ClientModule("potions", "Potion Effects", "Mostra os efeitos de poções ativos.", "HUD"));
        MODULES.add(new ClientModule("clock", "In-game Clock", "Mostra o horário local no HUD.", "HUD"));
        MODULES.add(new ClientModule("togglesprint", "Toggle Sprint", "Corre automaticamente ao andar para frente.", "PLAYER"));
        MODULES.add(new FullbrightModule());
        get("fps").setEnabled(true);
    }

    private ModuleManager() { }

    public static List<ClientModule> getModules() {
        return Collections.unmodifiableList(MODULES);
    }

    public static ClientModule get(String id) {
        for (ClientModule module : MODULES) {
            if (module.getId().equals(id)) return module;
        }
        return null;
    }

    public static boolean isEnabled(String id) {
        ClientModule module = get(id);
        return module != null && module.isEnabled();
    }

    public static void toggle(String id) {
        ClientModule module = get(id);
        if (module != null) module.setEnabled(!module.isEnabled());
    }
}
