package com.github.micha4w;

import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public enum Teams {
    GREEN(-8, -8, ChatColor.GREEN, Material.GREEN_CONCRETE, Material.GREEN_STAINED_GLASS),
    YELLOW(7, -8, ChatColor.YELLOW, Material.YELLOW_CONCRETE, Material.YELLOW_STAINED_GLASS),
    BLUE(-8, 7, ChatColor.BLUE, Material.BLUE_CONCRETE, Material.BLUE_STAINED_GLASS),
    RED(7, 7, ChatColor.RED, Material.RED_CONCRETE, Material.RED_STAINED_GLASS);

    static final World world = Bukkit.getWorld("test2");

    int chunkX;
    int chunkZ;

    int treasureX;
    int treasureZ;

    int score = 0;

    Material baseBlock;
    Material treasureBlock;
    ChatColor color;
    List<Player> players = new ArrayList<>();
    Teams opposite;

    Teams(int chunkX, int chunkZ, ChatColor color, Material baseBlock, Material treasureBlock) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.color = color;

        this.baseBlock = baseBlock;
        this.treasureBlock = treasureBlock;
    }

    public static void innit() {
        Teams.BLUE.opposite = Teams.YELLOW;
        Teams.YELLOW.opposite = Teams.BLUE;
        Teams.GREEN.opposite = Teams.RED;
        Teams.RED.opposite = Teams.GREEN;
    }

    public static Teams getTeam(Player player) {
        for (Teams team : Teams.values()) {
            if (team.players.contains(player)) {
                return team;
            }
        }
        return null;
    }

    public static void endGame() {
        for (Teams team : Teams.values()) {
            team.score = 0;
            for (Player player : team.players) {
                team.removePlayer(player);
            }
        }
    }

    public boolean isBaseChunk(int chunkX, int chunkZ) {
        return this.chunkX == chunkX && this.chunkZ == chunkZ;
    }

    public void addPlayer(Player player) {
        players.add(player);

        player.setDisplayName(color + player.getDisplayName());
        player.setPlayerListName(color + player.getDisplayName());

        player.teleport(this.getSpawn());
        player.setBedSpawnLocation(this.getSpawn(), true);
    }

    public Location getSpawn() {
        return getFromCorner(6.5, 73);
    }

    public void removePlayer(Player player) {
        players.remove(player);

        player.setDisplayName(player.getDisplayName().substring(color.toString().length()));
        player.setPlayerListName(player.getDisplayName());
    }

    public Location getFromCorner(double distanceToCorner, double yLevel) {
        final int signX = Integer.signum(opposite.chunkX);
        final int signZ = Integer.signum(opposite.chunkZ);

        Chunk chunk = Teams.world.getChunkAt(16 * chunkX, 16 * chunkZ);
        Location corner = chunk.getBlock(signX < 0 ? 15 : 0, (int) yLevel, signZ < 0 ? 15 : 0).getLocation();

        return corner.add(distanceToCorner * signX, 0, distanceToCorner * signZ);
    }
}