package com.github.micha4w;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ( command.getLabel().equalsIgnoreCase("test") ) {
            sender.sendMessage(args);
            WorldCreator wc;

            return true;
        }

        return false;
    }

}
