package me.pulsi_.advancedautosmelt.listeners;

import me.pulsi_.advancedautosmelt.values.Values;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.HashMap;
import java.util.UUID;

public class PickupListener implements Listener {

    public static HashMap<UUID, Player> itemOwners = new HashMap<>();

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        if (!Values.getConfig().isProcessPlayerPickupEvent()) return;
        UUID uuid = e.getItem().getUniqueId();
        if (!itemOwners.containsKey(uuid)) return;

        Player owner = itemOwners.get(uuid);
        Player p = e.getPlayer();

        if (!p.equals(owner)) e.setCancelled(true);
        else itemOwners.remove(uuid);
    }
}