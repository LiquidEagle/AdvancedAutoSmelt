package me.pulsi_.advancedautosmelt.listeners;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.coreSystem.AdvancedAutoSmeltDropSystem;
import me.pulsi_.advancedautosmelt.coreSystem.AutoSell;
import me.pulsi_.advancedautosmelt.values.ConfigValues;
import me.pulsi_.prisonenchants.events.PEExplosionEvent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

public class PrisonEnchantsListener implements Listener {

    // If the autosell is enabled, register the event and manage the blocks
    // before giving them to the player, in case the instant sell is enabled.

    @EventHandler (priority = EventPriority.LOWEST)
    public void onExplosion(PEExplosionEvent e) {
        if (e.isCancelled() || !ConfigValues.isAutoSellInstantSell()) return;
        Player p = e.getPlayer();


    }
}