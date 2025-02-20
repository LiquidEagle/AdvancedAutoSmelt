package me.pulsi_.advancedautosmelt.commands.baseCmds;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.AASCommand;
import me.pulsi_.advancedautosmelt.commands.CmdRegisterer;
import me.pulsi_.advancedautosmelt.utils.AASMessages;
import me.pulsi_.advancedautosmelt.utils.AASPermissions;
import me.pulsi_.advancedautosmelt.utils.AASUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MainCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (!AASUtils.hasPermission(s, AASPermissions.adminPermission)) return false;

        if (args.length == 0) {
            AASMessages.send(s, "%prefix% &7Plugin made by &aPulsi_&7, running on version &av" + AdvancedAutoSmelt.INSTANCE().getDescription().getVersion() + "&7:");
            AASMessages.send(s, "%prefix% &7Type &a/aas reload &7to reload the plugin.");
            return true;
        }

        String identifier = args[0].toLowerCase();

        if (!CmdRegisterer.getRegisteredCommandNames().contains(identifier)) {
            AASMessages.send(s, "Unknown-Command");
            return true;
        }

        AASCommand cmd = CmdRegisterer.getCommand(identifier);
        cmd.execute(s, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> cmds = new ArrayList<>();
            for (AASCommand cmd : CmdRegisterer.getRegisteredCommands())
                if (s.hasPermission("advancedautosmelt." + cmd.getIdentifier().toLowerCase())) cmds.add(cmd.getIdentifier());

            List<String> args0 = new ArrayList<>();
            for (String arg : cmds)
                if (arg.toLowerCase().startsWith(args[0].toLowerCase())) args0.add(arg);

            return args0;
        }

        String identifier = args[0].toLowerCase();
        if (!CmdRegisterer.getRegisteredCommandNames().contains(identifier)) return null;

        AASCommand cmd = CmdRegisterer.getCommand(identifier);
        if (cmd.playerOnly() && !(s instanceof Player)) return null;

        return s.hasPermission("advancedautosmelt." + identifier) ? cmd.tabCompletion(s, args) : null;
    }
}