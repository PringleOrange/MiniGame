package com.github.micha4w;

import org.bukkit.plugin.java.JavaPlugin;

public class Geem extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("test").setExecutor(new CommandHandler());

        getLogger().info("Geem!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Geemn't!");
    }

}
