package me.pulsi_.advancedautosmelt.commands.baseCmds;

import me.pulsi_.advancedautosmelt.players.AASPlayer;
import me.pulsi_.advancedautosmelt.players.PlayerRegistry;
import me.pulsi_.advancedautosmelt.utils.AASMessages;
import me.pulsi_.advancedautosmelt.utils.AASPermissions;
import me.pulsi_.advancedautosmelt.utils.AASUtils;
import me.pulsi_.advancedautosmelt.values.ConfigValues;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InventoryAlertsCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (!AASUtils.isPlayer(s)) return false;

        Player p = (Player) s;
        if (!ConfigValues.isInventoryAlertsEnabled()) {
            AASMessages.send(p, "InventoryAlerts-Disabled");
            return false;
        }

        if (!AASUtils.hasPermission(p, AASPermissions.inventoryAlertsTogglePermission)) return false;

        AASPlayer player = PlayerRegistry.getPlayer(p);

        boolean enabled = player.isInventoryAlertsEnabled();
        if (enabled) AASMessages.send(p, "InventoryAlerts-Deactivated");
        else AASMessages.send(p, "InventoryAlerts-Activated");

        player.setInventoryAlertsEnabled(!enabled);
        return true;
    }
}
