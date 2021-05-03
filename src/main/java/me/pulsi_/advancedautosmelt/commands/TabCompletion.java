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
        if (args1.isEmpty()) {
            args1.add("reload");
            args1.add("restart");
            args1.add("help");
            args1.add("list");
            args1.add("info");
            args1.add("toggle");
            args1.add("givepick");
        }

        List<String> infoArgs = new ArrayList<>();
        if (infoArgs.isEmpty()) {
            infoArgs.add("permissions");
        }

        List<String> permissionsArgs = new ArrayList<>();
        if (permissionsArgs.isEmpty()) {
            permissionsArgs.add("1");
            permissionsArgs.add("2");
            permissionsArgs.add("3");
        }

        List<String> toggleArgs = new ArrayList<>();
        if (toggleArgs.isEmpty()) {
            toggleArgs.add("autopickup");
            toggleArgs.add("autosmelt");
            toggleArgs.add("inventoryfull");
        }

        List<String> resultArgs1 = new ArrayList<>();

        if (args.length == 1) {
            for (String a : args1) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase()))
                    resultArgs1.add(a);
            }
            return resultArgs1;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("info")) {
            return infoArgs;
        }

        if (args.length == 3 && args[1].equalsIgnoreCase("permissions")) {
            return permissionsArgs;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("toggle")) {
            return toggleArgs;
        }
        return null;
    }
}