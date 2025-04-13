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

public class AutoPickupCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (!AASUtils.isPlayer(s)) return false;

        Player p = (Player) s;
        if (!ConfigValues.isAutoPickupEnabled()) {
            AASMessages.send(p, "AutoPickup-Disabled");
            return false;
        }
        // Moved player declaration here to use in permission check
        AASPlayer player = PlayerRegistry.getPlayer(p);

        if (!AASUtils.hasPermission(p, AASPermissions.autoPickupTogglePermission)) {
            // ~ Check if player had AutoPickup enabled before losing permission & disable if true.
            if (player.isAutoPickupEnabled()) {player.setAutoPickupEnabled(false);}
            return false;
        }

        boolean enabled = player.isAutoPickupEnabled();
        if (enabled) AASMessages.send(p, "AutoPickup-Deactivated");
        else AASMessages.send(p, "AutoPickup-Activated");

        player.setAutoPickupEnabled(!enabled);
        return true;
    }
}
