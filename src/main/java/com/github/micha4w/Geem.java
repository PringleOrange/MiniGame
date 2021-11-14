package com.github.micha4w;

import org.bukkit.entity.Player;
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
        for ( Teams team : Teams.values() ) {
            for ( Player player : team.players ) {
                team.removePlayer(player);
            }
        }

        getLogger().info("Geemn't!");
    }
}
