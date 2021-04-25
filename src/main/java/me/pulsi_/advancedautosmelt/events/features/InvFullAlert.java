package me.pulsi_.advancedautosmelt.events.features;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import me.pulsi_.advancedautosmelt.utils.ChatUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class InvFullAlert implements Listener {

    private AdvancedAutoSmelt plugin;
    private Set<String> noAlert;
    public InvFullAlert(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
        this.noAlert = Commands.inventoryFullOFF;
    }

    @EventHandler
    public void invFullAlert(BlockBreakEvent e) {

        FileConfiguration config = plugin.getConfiguration();

        Player p = e.getPlayer();

        if (p.getInventory().firstEmpty() >= 0) return;
        if (!config.getBoolean("InventoryFull.Inventory-Full-Alert")) return;
        if (noAlert.contains(p.getName())) return;

        //Title
        if (config.getBoolean("InventoryFull.Title.Use-Title")) {
            try {
                p.sendTitle(ChatUtils.c(config.getString("InventoryFull.Title.Title")), ChatUtils.c(config.getString("InventoryFull.Title.Sub-Title")),
                config.getInt("InventoryFull.Title.Fadein-Delay"), config.getInt("InventoryFull.Title.Stay-Delay"), config.getInt("InventoryFull.Title.Fadeout-Delay"));
            } catch (NoSuchMethodError err) {
                p.sendTitle(ChatUtils.c(config.getString("InventoryFull.Title.Title")), ChatUtils.c(config.getString("InventoryFull.Title.Sub-Title")));
            }
        }
        //Title

        //ActionBar
        if (config.getBoolean("InventoryFull.Actionbar.Use-Actionbar")) {
            try {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatUtils.c(config.getString("InventoryFull.Actionbar.Message"))));
            } catch (NoSuchMethodError err) {
                Bukkit.getConsoleSender().sendMessage(ChatUtils.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &cSorry! But the ActionBar doesn't work in " + plugin.getServer().getVersion()));
            }
        }
        //ActionBar

        //Messages
        if (config.getBoolean("InventoryFull.Messages.Use-Messages")) {
            for (String messages : config.getStringList("InventoryFull.Messages.Messages"))
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages));
        }
        //Messages

        //Sound
        if (config.getBoolean("InventoryFull.Sound.Use-Sound")) {
            try {
                p.playSound(p.getLocation(), Sound.valueOf(config.getString("InventoryFull.Sound.Sound-Type")), config.getInt("InventoryFull.Sound.Volume"),
                        config.getInt("InventoryFull.Sound.Pitch"));
            } catch (Exception exc) {
                Bukkit.getConsoleSender().sendMessage(ChatUtils.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &cThe sound for the InventoryFull Sound is invalid!"));
            }
        }
        //Sound

        if (config.getInt("InventoryFull.Alert-Delay") != 0) {
            noAlert.add(p.getName());
            new BukkitRunnable() {
                public void run() {
                    noAlert.remove(p.getName());
                }
            }.runTaskLater(plugin, config.getInt("InventoryFull.Alert-Delay"));
        }
    }
}