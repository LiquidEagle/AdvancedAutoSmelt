package me.pulsi_.advancedautosmelt.commands;

import me.pulsi_.advancedautosmelt.managers.MessageManager;
import me.pulsi_.advancedautosmelt.utils.MapUtils;
import me.pulsi_.advancedautosmelt.values.Values;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InventoryAlertsCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {

        if (!(s instanceof Player)) {
            MessageManager.notPlayer(s);
            return false;
        }

        Player p = (Player) s;
        if (!Values.getConfig().isInventoryAlertsEnabled()) {
            MessageManager.inventoryAlertsDeactivated(p);
            return false;
        }

        if (!p.hasPermission("advancedautosmelt.toggle.inventory-alerts")) {
            MessageManager.noPermission(s);
            return false;
        }


        if (!MapUtils.isInventoryAlerts.containsKey(p)) {
            MapUtils.isInventoryAlerts.put(p, true);
            MessageManager.inventoryAlertsActivated(p);
            return false;
        }

        boolean isEnabled = MapUtils.isInventoryAlerts.get(p);
        if (isEnabled) {
            MapUtils.isInventoryAlerts.put(p, false);
            MessageManager.inventoryAlertsDisabled(p);
        } else {
            MapUtils.isInventoryAlerts.put(p, true);
            MessageManager.inventoryAlertsActivated(p);
        }

        return true;
    }
}
