package me.pulsi_.advancedautosmelt.commands;

import me.pulsi_.advancedautosmelt.managers.MessageManager;
import me.pulsi_.advancedautosmelt.utils.MapUtils;
import me.pulsi_.advancedautosmelt.values.Values;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AutoSmeltCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {

        if (!(s instanceof Player)) {
            MessageManager.notPlayer(s);
            return false;
        }

        Player p = (Player) s;
        if (!Values.getConfig().isAutoSmeltEnabled()) {
            MessageManager.autoSmeltDisabled(p);
            return false;
        }

        if (!p.hasPermission("advancedautosmelt.toggle.autosmelt")) {
            MessageManager.noPermission(s);
            return false;
        }

        if (!MapUtils.isAutoSmeltEnabled.containsKey(p)) {
            MapUtils.isAutoSmeltEnabled.put(p, true);
            MessageManager.autoSmeltActivated(p);
            return false;
        }

        boolean isEnabled = MapUtils.isAutoSmeltEnabled.get(p);
        if (isEnabled) {
            MapUtils.isAutoSmeltEnabled.put(p, false);
            MessageManager.autoSmeltDeactivated(p);
        } else {
            MapUtils.isAutoSmeltEnabled.put(p, true);
            MessageManager.autoSmeltActivated(p);
        }

        return true;
    }
}
