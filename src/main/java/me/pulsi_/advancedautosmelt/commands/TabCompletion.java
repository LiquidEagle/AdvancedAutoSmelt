package me.pulsi_.advancedautosmelt.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabCompletion implements TabCompleter {

    public List<String> args0 = new ArrayList<String>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args0.isEmpty()) {
            args0.add("reload");
            args0.add("help");
            args0.add("list");
            args0.add("info");
            args0.add("toggle");
        }

        List<String> resultArgs0 = new ArrayList<String>();
        if (args.length == 1) {
            for (String a : args0) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase()))
                    resultArgs0.add(a);
            }
            return resultArgs0;
        }
        return null;
    }
}