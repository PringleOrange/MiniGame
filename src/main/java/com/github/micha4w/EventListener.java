package com.github.micha4w;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerKill (PlayerDeathEvent event) {
        Player player = event.getEntity();

        Teams killedTeam = Teams.getTeam(player);
        if ( killedTeam != null ) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1, false, false, false));

            Player killer = player.getKiller();
            if ( killer != null ) {
                Teams killerTeam = Teams.getTeam(killer);
                if ( killerTeam == null ) {
                    Bukkit.getLogger().info("Somehow somebody not inside a game killed someone inside a game, pls fix");
                    return;
                }


                final int distanceToCorner = 1;
                Location loc = killedTeam.opposite.getFromCorner(1, 85);

                int addX = Integer.signum(killerTeam.chunkX);
                int addZ = Integer.signum(killerTeam.chunkZ);

                if ( addX == Integer.signum(killedTeam.opposite.chunkX) )
                    addX = 0;

                if ( addZ == Integer.signum(killedTeam.opposite.chunkZ) )
                    addZ = 0;

                loc.add(addX, 0, addZ).getBlock().setType(killerTeam.treasureBlock);
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

        if ( team != null ) {
            if ( event.getBlock().getType() == team.treasureBlock ) {
                team.addScore(1);

                event.setDropItems(false);
                return;
            }

            for ( Teams otherTeam : Teams.values() ) {
                if ( otherTeam == team ) continue;

                if ( event.getBlock().getType() == otherTeam.treasureBlock ) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

}
