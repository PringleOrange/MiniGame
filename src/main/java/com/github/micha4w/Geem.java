package com.github.micha4w;

import org.bukkit.plugin.java.JavaPlugin;

public class Geem extends JavaPlugin {

    @Override
    public void onEnable() {
        Teams.innit();

        this.getCommand("test").setExecutor(new CommandHandler());
        this.getCommand("updategeem").setExecutor(new CommandHandler());
        this.getCommand("addplayer").setExecutor(new CommandHandler());
        this.getCommand("removeplayer").setExecutor(new CommandHandler());

        this.getServer().getPluginManager().registerEvents(new EventListener(), this);

        getLogger().info("Geem!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Geemn't!");
    }
}
