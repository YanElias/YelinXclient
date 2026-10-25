package com.yelinx.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModuleManager {
    private static final List<ClientModule> MODULES = new ArrayList<ClientModule>();

    static {
        MODULES.add(new ClientModule("fps", "FPS Counter", "Mostra FPS e a marca YelinX no HUD."));
        MODULES.add(new ClientModule("coordinates", "Coordinates", "Mostra suas coordenadas XYZ."));
        MODULES.add(new ClientModule("keystrokes", "Keystrokes", "Mostra teclas e botões pressionados."));
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
