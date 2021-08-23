package me.pulsi_.advancedautosmelt.listeners;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.utils.ChatUtils;
import me.pulsi_.advancedautosmelt.utils.MethodUtils;
import me.pulsi_.advancedautosmelt.utils.SetUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class InventoryAlerts implements Listener {

    private AdvancedAutoSmelt plugin;
    public InventoryAlerts(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void invAlerts(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.getInventory().firstEmpty() >= 0 | !plugin.config().getBoolean("InventoryAlerts.Enabled") | SetUtils.INVENTORYALERTS_OFF.contains(p.getUniqueId())) return;

        if (plugin.config().getBoolean("InventoryAlerts.Title.Enabled")) MethodUtils.sendTitle(plugin.config().getString("InventoryAlerts.Title.Title"), p);

        if (plugin.config().getBoolean("InventoryAlerts.Actionbar.Enabled")) MethodUtils.sendActionBar(p, plugin.config().getString("InventoryAlerts.Actionbar.Message"));

        if (plugin.config().getBoolean("InventoryAlerts.Messages.Enabled"))
            for (String messages : plugin.config().getStringList("InventoryAlerts.Messages.Messages"))
                p.sendMessage(ChatUtils.color(messages));

        if (plugin.config().getBoolean("InventoryAlerts.Sound.Enabled")) MethodUtils.playSound(plugin.config().getString("InventoryAlerts.Sound.Sound-Type"), p);

        if (plugin.config().getInt("InventoryAlerts.Alert-Delay") != 0) {
            SetUtils.INVENTORYALERTS_OFF.add(p.getUniqueId());
            Bukkit.getScheduler().runTaskLater(plugin, () -> SetUtils.INVENTORYALERTS_OFF.remove(p.getUniqueId()),plugin.config().getInt("InventoryAlerts.Alert-Delay"));
        }
    }
}