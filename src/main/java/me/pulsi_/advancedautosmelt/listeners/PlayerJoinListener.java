package me.pulsi_.advancedautosmelt.listeners;

import me.pulsi_.advancedautosmelt.utils.MapUtils;
import me.pulsi_.advancedautosmelt.values.Values;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void enableAutoPickupOnJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!Values.getConfig().isAutoPickupEnabled() || !Values.getConfig().isAutoPickupEnabledOnJoin()) {
            MapUtils.isAutoPickupEnabled.put(p, false);
            return;
        }
        if (!Values.getConfig().isAutoPickupEnabledOnJoinNeedPerm()) {
            MapUtils.isAutoPickupEnabled.put(p, true);
            return;
        }
        if (p.hasPermission(Values.getConfig().getAutoPickupJoinPermission())) {
            MapUtils.isAutoPickupEnabled.put(p, true);
        } else {
            MapUtils.isAutoPickupEnabled.put(p, false);
        }
    }

    @EventHandler
    public void enableAutoSmeltOnJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!Values.getConfig().isAutoSmeltEnabled() || !Values.getConfig().isAutoSmeltEnabledOnJoin()) {
            MapUtils.isAutoSmeltEnabled.put(p, false);
            return;
        }
        if (!Values.getConfig().isAutoSmeltEnabledOnJoinNeedPerm()) {
            MapUtils.isAutoSmeltEnabled.put(p, true);
            return;
        }
        if (p.hasPermission(Values.getConfig().getAutoSmeltJoinPermission())) {
            MapUtils.isAutoSmeltEnabled.put(p, true);
        } else {
            MapUtils.isAutoSmeltEnabled.put(p, false);
        }
    }

    @EventHandler
    public void enableInventoryAlertsOnJoin(PlayerJoinEvent e) {
        MapUtils.isInventoryAlerts.put(e.getPlayer(), true);
    }
}