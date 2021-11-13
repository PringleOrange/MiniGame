package com.github.micha4w;

import org.bukkit.plugin.java.JavaPlugin;

public class Geem extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Geem!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Geemn't!");
    }

}
