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

    static final World world = Bukkit.getWorld("minigame");
    static final Location lobby = new Location(Bukkit.getWorld("lobby"), 0, 7, 0);

    int chunkX;
    int chunkZ;

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

    public static void prepare() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvdelete minigame");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvconfirm");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvclone test2 minigame");
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
        return getFromCorner(6, 73);
    }

    public void removePlayer(Player player) {
        players.remove(player);

        player.setDisplayName(player.getDisplayName().substring(color.toString().length()));
        player.setPlayerListName(player.getDisplayName());

        player.teleport(lobby);
    }

    public Location getFromCorner(double distanceToCorner, double yLevel) {
        final int signX = -Integer.signum(chunkX);
        final int signZ = -Integer.signum(chunkZ);

        Chunk chunk = Teams.world.getChunkAt(chunkX, chunkZ);
        Location corner = chunk.getBlock(signX < 0 ? 15 : 0, (int) yLevel, signZ < 0 ? 15 : 0).getLocation();

        return corner.add(distanceToCorner * signX, 0, distanceToCorner * signZ);
    }
}