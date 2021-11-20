package com.github.micha4w;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class CommandHandler implements CommandExecutor, TabCompleter {

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

            WorldCreator wc = new WorldCreator("test2");
            wc.generator(new WorldGenerator());

            world = wc.createWorld();
            if ( world == null ) {
                sender.sendMessage("Error creating world");
                return true;
            }

            world.getWorldBorder().setSize(256);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.KEEP_INVENTORY, true );
//            world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
            world.setTime(6000);

            if ( sender instanceof Player ) {
                ((Player) sender).teleport(new Location(world, 0, 100, 0));
            }

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

            Player player = Bukkit.getPlayer(args[1]);
            if ( Teams.getTeam(player) != null ) {
                sender.sendMessage("Player is already in a team");
                return true;
            }

            switch ( args[0] ) {
                case "r":
                    Teams.RED.addPlayer(player);
                    sender.sendMessage("Played added to team RED");
                    break;
                case "g":
                    Teams.GREEN.addPlayer(player);
                    sender.sendMessage("Played added to team GREEN");
                    break;
                case "b":
                    Teams.BLUE.addPlayer(player);
                    sender.sendMessage("Played added to team BLUE");
                    break;
                case "y":
                    Teams.YELLOW.addPlayer(player);
                    sender.sendMessage("Played added to team YELLOW");
                    break;
            }

        } else if ( command.getLabel().equalsIgnoreCase("removeplayer") ) {
            if ( args.length != 1 ) return false;

            Player player = Bukkit.getPlayer(args[0]);
            Teams team = Teams.getTeam(player);
            if ( team == null ) {
                sender.sendMessage("Player is not in a team");
            } else {
                team.removePlayer(player);
                sender.sendMessage("Played removed");
            }

        } else if ( command.getLabel().equalsIgnoreCase("prepare") ) {
            Teams.prepare();
        } else {
            return false;
        }
        return true;
    }

    private static final Map<String, String[][]> tabOptions = ImmutableMap.<String, String[][]>builder()
            .put("removeplayer", new String[][]{{"pleyers"}})
            .put("addplayer", new String[][]{{"r","g","b","y"},{"pleyers"}})
            .build();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        sender.sendMessage(args);

        for ( String option : tabOptions.keySet() ) {
            if (option.equalsIgnoreCase(label)) {
                String[][] optionsList = tabOptions.get(option);
                if ( optionsList.length >= args.length ) {
                    String[] options = optionsList[args.length - 1];
                    if (Arrays.equals(options, new String[]{"pleyers"}))
                        options = Bukkit.getOnlinePlayers().stream().map(Player::getDisplayName).toArray(String[]::new);
                    sender.sendMessage(options);
                    StringUtil.copyPartialMatches(args[args.length - 1], Arrays.asList(options.clone()), completions);
                }
                break;
            }
        }

        return completions;
    }
}
