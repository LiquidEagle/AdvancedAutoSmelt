package me.pulsi_.advancedautosmelt.listeners;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.gui.GuiHolder;
import me.pulsi_.advancedautosmelt.managers.DataManager;
import me.pulsi_.advancedautosmelt.managers.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiListener implements Listener {

    private final AdvancedAutoSmelt plugin;

    public GuiListener(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player) || !(e.getInventory().getHolder() instanceof GuiHolder)) return;
        Player p = (Player) e.getWhoClicked();
        e.setCancelled(true);

        if (e.getSlot() == 13) {
            MessageManager.pluginReloaded(p);
            DataManager.reloadPlugin(plugin);
            p.closeInventory();
        }
    }
}