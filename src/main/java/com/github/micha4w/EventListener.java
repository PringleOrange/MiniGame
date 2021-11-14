package com.github.micha4w;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerKill (PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();
        if ( killer != null ) {
            Teams killedTeam = Teams.getTeam(player);

            if ( killedTeam != null ) {
                Teams killerTeam = Teams.getTeam(killer);
                if ( killerTeam == null ) {
                    Bukkit.getLogger().info("Somehow somebody not inside a game killed someone inside a game, pls fix");
                    return;
                }

                Block block = new Location(Teams.world,
                                                16 * killedTeam.opposite.chunkX + Math.abs(killerTeam.chunkX),
                                                7,
                                                16 * killedTeam.opposite.chunkZ + Math.abs(killerTeam.chunkZ)
                                            ).getBlock();

                block.setType(killerTeam.baseBlock);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Teams team = Teams.getTeam(player);
        if ( team != null ) {
            team.removePlayer(player);
        }
    }

    @EventHandler
    public void onBlockMine (BlockBreakEvent event) {
        Player player = event.getPlayer();
        Teams team = Teams.getTeam(player);
        player.sendMessage(event.getBlock().getType() + " " + team);

        if ( team != null ) {
            for ( Teams otherTeam : Teams.values() ) {
                if ( otherTeam == team ) continue;

                if ( event.getBlock().getType() == otherTeam.baseBlock ) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

}
