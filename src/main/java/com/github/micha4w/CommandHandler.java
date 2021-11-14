package com.github.micha4w;

import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;


public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ( command.getLabel().equalsIgnoreCase("test") ) {

            World world = Bukkit.getWorld("test2");
            if ( world != null ) {
                for ( Player player : world.getPlayers() )
                    player.teleport(new Location(Bukkit.getWorld("test"), 0, 100, 0));

                Bukkit.unloadWorld(world, false);

                try {
                    FileUtils.deleteDirectory(world.getWorldFolder());
                } catch ( IOException e ) { e.printStackTrace(); }
            }

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "reload confirm");

            WorldCreator wc = new WorldCreator("test2");
            wc.generator(new WorldGenerator());

            world = wc.createWorld();
            if ( world == null ) {
                sender.sendMessage("Error creating world");
                return true;
            }

            world.getWorldBorder().setSize(256);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
//            world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
            world.setTime(6000);

            if ( sender instanceof Player ) {
                ((Player) sender).teleport(new Location(world, 0, 100, 0));
            }

            return true;
        } else if ( command.getLabel().equalsIgnoreCase("updategeem") ) {
            {
                File file = new File("plugins/MiniGame.jar");
                file.delete();
            }

            try (BufferedInputStream in = new BufferedInputStream(new URL("https://github.com/micha4w/MiniGame/raw/master/target/MiniGame.jar").openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream("plugins/MiniGame.jar")) {
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
                sender.sendMessage(ChatColor.GREEN + "Successs!!");
            } catch (IOException e) {
                sender.sendMessage(ChatColor.RED + "failll.. D:");
            }
        } else if ( command.getLabel().equalsIgnoreCase("addplayer") ) {
            if ( args.length != 2 ) return false;

            switch ( args[0] ) {
                case "r":
                    Teams.RED.players.add(Bukkit.getPlayer(args[1]));
                    break;
                case "g":
                    Teams.GREEN.players.add(Bukkit.getPlayer(args[1]));
                    break;
                case "b":
                    Teams.BLUE.players.add(Bukkit.getPlayer(args[1]));
                    break;
                case "y":
                    Teams.YELLOW.players.add(Bukkit.getPlayer(args[1]));
                    break;
            }

            return true;
        }

        return false;
    }

}
