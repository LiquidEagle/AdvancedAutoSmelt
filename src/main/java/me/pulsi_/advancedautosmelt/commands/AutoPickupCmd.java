package me.pulsi_.advancedautosmelt.commands;

import me.pulsi_.advancedautosmelt.managers.MessageManager;
import me.pulsi_.advancedautosmelt.utils.MapUtils;
import me.pulsi_.advancedautosmelt.values.Values;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AutoPickupCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {

        if (!(s instanceof Player)) {
            MessageManager.notPlayer(s);
            return false;
        }

        Player p = (Player) s;
        if (!Values.getConfig().isAutoPickupEnabled()) {
            MessageManager.autoPickupDisabled(p);
            return false;
        }

        if (!p.hasPermission("advancedautosmelt.toggle.autopickup")) {
            MessageManager.noPermission(s);
            return false;
        }

        if (!MapUtils.isAutoPickupEnabled.containsKey(p)) {
            MapUtils.isAutoPickupEnabled.put(p, true);
            MessageManager.autoPickupActivated(p);
            return false;
        }

        boolean isEnabled = MapUtils.isAutoPickupEnabled.get(p);
        if (isEnabled) {
            MapUtils.isAutoPickupEnabled.put(p, false);
            MessageManager.autoPickupDeactivated(p);
        } else {
            MapUtils.isAutoPickupEnabled.put(p, true);
            MessageManager.autoPickupActivated(p);
        }

        return true;
    }
}
