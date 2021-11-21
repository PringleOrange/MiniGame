package com.github.micha4w;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public enum Teams {
    GREEN(-8, -8, ChatColor.GREEN, Material.GREEN_CONCRETE, Material.GREEN_STAINED_GLASS),
    YELLOW(7, -8, ChatColor.YELLOW, Material.YELLOW_CONCRETE, Material.YELLOW_STAINED_GLASS),
    BLUE(-8, 7, ChatColor.BLUE, Material.BLUE_CONCRETE, Material.BLUE_STAINED_GLASS),
    RED(7, 7, ChatColor.RED, Material.RED_CONCRETE, Material.RED_STAINED_GLASS);

    static final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    static final Objective objective = scoreboard.registerNewObjective("scores", "dummy", ChatColor.DARK_PURPLE+""+ChatColor.BOLD+ "Team Scores:");;

    int chunkX;
    int chunkZ;

    //int score = 0;
    Score score;

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

        Teams.BLUE.score = objective.getScore(ChatColor.BLUE + "Blue");
        Teams.YELLOW.score = objective.getScore(ChatColor.YELLOW + "Yellow");
        Teams.GREEN.score = objective.getScore(ChatColor.GREEN + "Green");
        Teams.RED.score = objective.getScore(ChatColor.RED + "Red");

        Teams.BLUE.score.setScore(0);
        Teams.YELLOW.score.setScore(0);
        Teams.GREEN.score.setScore(0);
        Teams.RED.score.setScore(0);

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
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
            team.score.setScore(0);
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

        player.setBedSpawnLocation(this.getSpawn(), true);
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();

        player.teleport(new Location(getWorld(), 0, 256, 0));
        Bukkit.getScheduler().scheduleSyncDelayedTask(Geem.plugin, () -> player.setHealth(0), 1);

        player.setScoreboard(scoreboard);
    }

    public Location getSpawn() { return getFromCorner(6, 73); }
    public static World getWorld() { return Bukkit.getWorld("minigame"); }
    public static Location getLobby() { return new Location(Bukkit.getWorld("lobby"), 0, 7, 0); }

    public void removePlayer(Player player) {
        players.remove(player);

        player.setDisplayName(player.getDisplayName().substring(color.toString().length()));
        player.setPlayerListName(player.getDisplayName());
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());

        player.getInventory().clear();
        player.teleport(getLobby());
        player.setBedSpawnLocation(getLobby(), true);
    }

    public Location getFromCorner(double distanceToCorner, double yLevel) {
        final int signX = -Integer.signum(chunkX);
        final int signZ = -Integer.signum(chunkZ);

        Chunk chunk = getWorld().getChunkAt(chunkX, chunkZ);
        Location corner = chunk.getBlock(signX < 0 ? 15 : 0, (int) yLevel, signZ < 0 ? 15 : 0).getLocation();

        return corner.add(distanceToCorner * signX, 0, distanceToCorner * signZ);
    }

    public void addScore (int points) {
        this.score.setScore( this.score.getScore() + points );

        if ( this.score.getScore() >= 10 ) {
            for ( Teams teams : Teams.values() ) {
                for ( Player player : teams.players ) {
                    player.sendMessage(ChatColor.BOLD +""+ChatColor.DARK_PURPLE+ "Team " + this.color + this +ChatColor.DARK_PURPLE+ " has won!");
                }
            }

            Teams.endGame();
        }
    }
}