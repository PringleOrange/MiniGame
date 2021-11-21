package com.github.micha4w;

import org.bukkit.plugin.java.JavaPlugin;

public class Geem extends JavaPlugin {

    public static Geem plugin;

    @Override
    public void onEnable() {
        plugin = this;

        Teams.innit();

        this.getCommand("test").setExecutor(new CommandHandler());
        this.getCommand("updategeem").setExecutor(new CommandHandler());
        this.getCommand("addplayer").setExecutor(new CommandHandler());
        this.getCommand("removeplayer").setExecutor(new CommandHandler());
        this.getCommand("prepare").setExecutor(new CommandHandler());

        this.getCommand("addplayer").setTabCompleter(new CommandHandler());
        this.getCommand("removeplayer").setTabCompleter(new CommandHandler());

        this.getServer().getPluginManager().registerEvents(new EventListener(), this);

        getLogger().info("Geem!");
    }

    @Override
    public void onDisable() {
        Teams.endGame();

        getLogger().info("Geemn't!");
    }
}
