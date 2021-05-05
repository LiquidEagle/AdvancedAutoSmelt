package me.pulsi_.advancedautosmelt.autopickup;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import me.pulsi_.advancedautosmelt.utils.ChatUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Set;

public class AutoPickupExpCustom implements Listener {

    private Set<String> autoPickupOFF;
    private AdvancedAutoSmelt plugin;
    public AutoPickupExpCustom(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
        this.autoPickupOFF = Commands.autoPickupOFF;
    }

    @EventHandler
    public void pickupGoldExp(BlockBreakEvent e) {

        FileConfiguration config = plugin.getConfiguration();
        int goldExp = config.getInt("AutoSmelt.Gold-Exp");

        Player p = e.getPlayer();

        for (String disabledWorlds : config.getStringList("Disabled-Worlds"))
            if (disabledWorlds.contains(p.getWorld().getName())) return;
        for (String blockBlacklist : config.getStringList("Disabled-Blocks"))
            if (blockBlacklist.contains(e.getBlock().getType().toString())) return;

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (e.isCancelled()) return;
        if (!(e.getBlock().getType() == Material.GOLD_ORE)) return;
        if (!config.getBoolean("AutoSmelt.Give-Exp-Gold")) return;
        if (config.getBoolean("AutoSmelt.Disable-Creative-Mode")) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}
        if (config.getBoolean("AutoPickup.Autopickup-Experience")) {
            if (!autoPickupOFF.contains(p.getName())) {
                p.giveExp(goldExp);
            } else {
                e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(goldExp);
            }
        } else {
            e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(goldExp);
        }
    }

    @EventHandler
    public void pickupIronExp(BlockBreakEvent e) {

        FileConfiguration config = plugin.getConfiguration();
        int ironExp = config.getInt("AutoSmelt.Iron-Exp");

        Player p = e.getPlayer();

        for (String disabledWorlds : config.getStringList("Disabled-Worlds"))
            if (disabledWorlds.contains(p.getWorld().getName())) return;
        for (String blockBlacklist : config.getStringList("Disabled-Blocks"))
            if (blockBlacklist.contains(e.getBlock().getType().toString())) return;

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (e.isCancelled()) return;
        if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
        if (!config.getBoolean("AutoSmelt.Give-Exp-Iron")) return;
        if (config.getBoolean("AutoSmelt.Disable-Creative-Mode")) { if (p.getGameMode().equals(GameMode.CREATIVE)) return;}
        if (config.getBoolean("AutoPickup.Autopickup-Experience")) {
            if (!autoPickupOFF.contains(p.getName())) {
                p.giveExp(ironExp);
            } else {
                e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(ironExp);
            }
        } else {
            e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(ironExp);
        }
    }
}