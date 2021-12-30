package me.pulsi_.advancedautosmelt.listeners;

import me.pulsi_.advancedautosmelt.utils.MapUtils;
import me.pulsi_.advancedautosmelt.values.Values;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void enabledAutoPickupOnJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!Values.getConfig().isAutoPickupEnabled() || !Values.getConfig().isAutoPickupEnabledOnJoin()) {
            MapUtils.isAutoPickupEnabled.put(p.getUniqueId(), false);
            return;
        }
        if (!Values.getConfig().isAutoPickupEnabledOnJoinNeedPerm()) {
            MapUtils.isAutoPickupEnabled.put(p.getUniqueId(), true);
            return;
        }
        if (p.hasPermission(Values.getConfig().getAutoPickupJoinPermission())) {
            MapUtils.isAutoPickupEnabled.put(p.getUniqueId(), true);
        } else {
            MapUtils.isAutoPickupEnabled.put(p.getUniqueId(), false);
        }
    }

    @EventHandler
    public void enabledAutoSmeltOnJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!Values.getConfig().isAutoSmeltEnabled() || !Values.getConfig().isAutoSmeltEnabledOnJoin()) {
            MapUtils.isAutoSmeltEnabled.put(p.getUniqueId(), false);
            return;
        }
        if (!Values.getConfig().isAutoSmeltEnabledOnJoinNeedPerm()) {
            MapUtils.isAutoSmeltEnabled.put(p.getUniqueId(), true);
            return;
        }
        if (p.hasPermission(Values.getConfig().getAutoSmeltJoinPermission())) {
            MapUtils.isAutoSmeltEnabled.put(p.getUniqueId(), true);
        } else {
            MapUtils.isAutoSmeltEnabled.put(p.getUniqueId(), false);
        }
    }
}