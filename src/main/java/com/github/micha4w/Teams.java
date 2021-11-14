package com.github.micha4w;

import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public enum Teams {
    GREEN(-8, -8, ChatColor.GREEN, Material.GREEN_WOOL, Material.GREEN_STAINED_GLASS),
    YELLOW(7, -8, ChatColor.YELLOW, Material.YELLOW_WOOL, Material.YELLOW_STAINED_GLASS),
    BLUE(-8, 7, ChatColor.BLUE, Material.BLUE_WOOL, Material.BLUE_STAINED_GLASS),
    RED(7, 7, ChatColor.RED, Material.RED_WOOL, Material.RED_STAINED_GLASS);

    static final World world = Bukkit.getWorld("test2");

    int chunkX;
    int chunkZ;
    Material baseBlock;
    Material roofBlock;
    ChatColor color;
    List<Player> players = new ArrayList<>();
    Teams opposite;

    Teams(int chunkX, int chunkZ, ChatColor color, Material baseBlock, Material roofBlock) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.color = color;

        this.baseBlock = baseBlock;
        this.roofBlock = roofBlock;
    }

    public static void innit() {
        Teams.BLUE.opposite = Teams.YELLOW;
        Teams.YELLOW.opposite = Teams.BLUE;
        Teams.GREEN.opposite = Teams.RED;
        Teams.RED.opposite = Teams.GREEN;
    }

    public static Teams getTeam(Player player) {
        for ( Teams team : Teams.values() ) {
            if ( team.players.contains(player) ) {
                return team;
            }
        }
        return null;
    }

    public boolean sameAs (int chunkX, int chunkZ) {
        return this.chunkX == chunkX && this.chunkZ == chunkZ;
    }

    public void addPlayer (Player player) {
        players.add(player);

        player.setDisplayName(color + player.getDisplayName());
        player.setPlayerListName(color + player.getDisplayName());

        player.teleport(this.getSpawn());
    }

    public Location getSpawn() {
        return new Location(world, chunkX * 16 + 7.5, 2, chunkZ * 16 + 7.5);
    }

    public void removePlayer(Player player) {
        players.remove(player);

        player.setDisplayName( player.getDisplayName().substring(color.toString().length()) );
        player.setPlayerListName( player.getDisplayName().substring(color.toString().length()) );
    }
}
