package com.yelinx.client;

public class ClientModule {
    private final String id;
    private final String name;
    private final String description;
    private final String category;
    private boolean enabled;

    public ClientModule(String id, String name, String description) {
        this(id, name, description, "UTILITY");
    }

    public ClientModule(String id, String name, String description, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;
        this.enabled = enabled;
        if (enabled) onEnable();
        else onDisable();
    }

    protected void onEnable() { }
    protected void onDisable() { }
}
