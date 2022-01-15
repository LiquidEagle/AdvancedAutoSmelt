package me.pulsi_.advancedautosmelt.mechanics;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.utils.ChatUtils;
import me.pulsi_.advancedautosmelt.utils.MapUtils;
import me.pulsi_.advancedautosmelt.utils.Methods;
import me.pulsi_.advancedautosmelt.values.Values;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class InventoryAlerts {

    private static final Set<UUID> TIMEOUT = new HashSet<>();

    public static void inventoryAlerts(Player p) {
        if (p.getInventory().firstEmpty() > -1 || !MapUtils.isInventoryAlerts.get(p.getUniqueId())
                || !Values.getConfig().isInventoryAlertsEnabled() || TIMEOUT.contains(p.getUniqueId())) return;

        if (Values.getConfig().isInventoryAlertsTitleEnabled()) Methods.sendTitle(Values.getConfig().getInventoryAlertsTitle(), p);

        if (Values.getConfig().isInventoryAlertsActionbarEnabled()) Methods.sendActionBar(p, Values.getConfig().getInventoryAlertsActionbar());

        if (Values.getConfig().isInventoryAlertsMessageEnabled())
            for (String messages : Values.getConfig().getInventoryAlertsMessage())
                p.sendMessage(ChatUtils.color(messages));

        if (Values.getConfig().isInventoryAlertsSoundEnabled()) Methods.playSound(p, Values.getConfig().getInventoryAlertsSound());

        if (Values.getConfig().getInventoryAlertsDelay() != 0) {
            TIMEOUT.add(p.getUniqueId());
            Bukkit.getScheduler().runTaskLater(AdvancedAutoSmelt.getInstance(), () -> TIMEOUT.remove(p.getUniqueId()), Values.getConfig().getInventoryAlertsDelay());
        }
    }
}