package me.pulsi_.advancedautosmelt.commands;

import me.pulsi_.advancedautosmelt.commands.list.ReloadCmd;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CmdRegisterer {

    private static final LinkedHashMap<String, AASCommand> commands = new LinkedHashMap<>();

    public static List<AASCommand> getRegisteredCommands() {
        return new ArrayList<>(commands.values());
    }

    public static List<String> getRegisteredCommandNames() {
        return new ArrayList<>(commands.keySet());
    }

    public static AASCommand getCommand(String identifier) {
        return commands.get(identifier.toLowerCase());
    }

    public static void registerCmds() {
        registerCmd(new ReloadCmd("reload"));
    }

    public static void resetCmds() {
        commands.clear();
    }

    public static void registerCmd(AASCommand command) {
        commands.put(command.getIdentifier().toLowerCase(), command);
        for (String alias : command.getAliases())
            commands.put(alias.toLowerCase(), command);
    }
}