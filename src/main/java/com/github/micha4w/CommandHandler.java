package com.github.micha4w;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;


public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ( command.getLabel().equalsIgnoreCase("test") ) {

            World world = Bukkit.getWorld("test");
            if ( world != null ) {
                Bukkit.unloadWorld(world, false);

                try {
                    FileUtils.deleteDirectory(world.getWorldFolder());
                } catch ( IOException e ) { e.printStackTrace(); }
            }

            WorldCreator wc = new WorldCreator("test");
            wc.generator(new WorldGenerator());

            world = wc.createWorld();
            if ( world == null ) {
                sender.sendMessage("Error creating world");
                return true;
            }

            world.getWorldBorder().setSize(256);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setTime(6000);

            return true;
        }

        return false;
    }

}
