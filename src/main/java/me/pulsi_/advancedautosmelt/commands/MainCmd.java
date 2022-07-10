package me.pulsi_.advancedautosmelt.commands;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.gui.GuiHolder;
import me.pulsi_.advancedautosmelt.managers.DataManager;
import me.pulsi_.advancedautosmelt.managers.MessageManager;
import me.pulsi_.advancedautosmelt.utils.AASLogger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MainCmd implements CommandExecutor, TabCompleter {

    private final AdvancedAutoSmelt plugin;

    public MainCmd(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {

        if (!s.hasPermission("advancedautosmelt.admin")) {
            MessageManager.noPermission(s);
            return false;
        }

        if (args.length == 0) {
            if (!(s instanceof Player)) {
                AASLogger.info("Console commands: /aas reload");
            } else {
                Player p = (Player) s;
                GuiHolder.getGuiHolder().openGui(p);
            }
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            MessageManager.pluginReloaded(s);
            DataManager.reloadPlugin(plugin);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command command, String alias, String[] args) {

        if (args.length == 1) {
            List<String> listOfArgs = new ArrayList<>();
            if (s.hasPermission("advancedautosmelt.admin")) listOfArgs.add("reload");

            List<String> args1 = new ArrayList<>();
            for (String arg : listOfArgs) if (arg.startsWith(args[0].toLowerCase())) args1.add(arg);
            return args1;
        }
        return null;
    }
}