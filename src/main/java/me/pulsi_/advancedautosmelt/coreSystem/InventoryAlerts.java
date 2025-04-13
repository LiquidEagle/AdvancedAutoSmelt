package me.pulsi_.advancedautosmelt.coreSystem;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.players.PlayerRegistry;
import me.pulsi_.advancedautosmelt.utils.AASChat;
import me.pulsi_.advancedautosmelt.utils.AASUtils;
import me.pulsi_.advancedautosmelt.values.ConfigValues;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

import static me.pulsi_.advancedautosmelt.coreSystem.CoreLoader.inventoryAlertsCooldown;

public class InventoryAlerts {

    /**
     * Send alerts about the inventory full.
     *
     * @param p The player to alert.
     */
    public static void inventoryAlerts(Player p) {
        if (!ConfigValues.isInventoryAlertsEnabled() || !AASUtils.isInventoryFull(p) || !PlayerRegistry.getPlayer(p).isInventoryAlertsEnabled()) return;

        UUID uuid = p.getUniqueId();
        if (inventoryAlertsCooldown.contains(uuid)) return;

        if (ConfigValues.isInventoryAlertsTitleEnabled()) AASUtils.sendTitle(ConfigValues.getInventoryAlertsTitle(), p);

        if (ConfigValues.isInventoryAlertsActionbarEnabled())
            AASUtils.sendActionBar(p, ConfigValues.getInventoryAlertsActionbar());

        if (ConfigValues.isInventoryAlertsMessageEnabled())
            for (String messages : ConfigValues.getInventoryAlertsMessage())
                p.sendMessage(AASChat.color(messages));

        if (ConfigValues.isInventoryAlertsSoundEnabled()) AASUtils.playSound(p, ConfigValues.getInventoryAlertsSound());

        if (ConfigValues.getInventoryAlertsDelay() != 0) {
            inventoryAlertsCooldown.add(uuid);
            Bukkit.getScheduler().runTaskLater(AdvancedAutoSmelt.INSTANCE(), () -> inventoryAlertsCooldown.remove(uuid), ConfigValues.getInventoryAlertsDelay());
        }
    }
}
