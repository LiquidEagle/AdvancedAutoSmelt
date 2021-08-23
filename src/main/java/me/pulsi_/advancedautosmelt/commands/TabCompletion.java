package me.pulsi_.advancedautosmelt.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender s, Command command, String alias, String[] args) {

        List<String> args1 = new ArrayList<>();
        if (s.hasPermission("advancedautosmelt.reload")) args1.add("reload");
        if (s.hasPermission("advancedautosmelt.help")) args1.add("help");
        if (s.hasPermission("advancedautosmelt.toggle")) args1.add("toggle");

        List<String> toggleArgs = new ArrayList<>();
        if (s.hasPermission("advancedautosmelt.toggle")) {
            toggleArgs.add("autopickup");
            toggleArgs.add("autosmelt");
            toggleArgs.add("inventoryalerts");
        }

        List<String> resultArgs1 = new ArrayList<>();
        if (args.length == 1) {
            for (String a : args1) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase()))
                    resultArgs1.add(a);
            }
            return resultArgs1;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("toggle")) {
            return toggleArgs;
        }
        return null;
    }
}