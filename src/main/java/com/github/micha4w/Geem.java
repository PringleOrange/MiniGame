package com.github.micha4w;

import org.bukkit.plugin.java.JavaPlugin;

public class Geem extends JavaPlugin {

    @Override
    public void onEnable() {
        Bases.BLUE.opposite = Bases.YELLOW;
        Bases.YELLOW.opposite = Bases.BLUE;
        Bases.GREEN.opposite = Bases.RED;
        Bases.RED.opposite = Bases.GREEN;


        this.getCommand("test").setExecutor(new CommandHandler());
        this.getCommand("updategeem").setExecutor(new CommandHandler());

        getLogger().info("Geem!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Geemn't!");
    }

}
