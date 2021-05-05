package me.pulsi_.advancedautosmelt.events.blocks;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import me.pulsi_.advancedautosmelt.utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class FurnaceBreak implements Listener {

    private AdvancedAutoSmelt plugin;
    private Set<String> autoPickupOFF;
    public FurnaceBreak(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
        this.autoPickupOFF = Commands.autoPickupOFF;
    }

    private final ItemStack furnace = new ItemStack(Material.FURNACE, 1);

    public void dropsItems(Player p, ItemStack i) {

        FileConfiguration config = plugin.getConfiguration();

        if (!p.getInventory().addItem(i).isEmpty()) {
            if (config.getBoolean("AutoPickup.Inv-Full-Drop-Items")) {
                p.getWorld().dropItem(p.getLocation(), i);
            }
        }
    }

    public void removeDrops(BlockBreakEvent e) {

        FileConfiguration config = plugin.getConfiguration();

        if (config.getBoolean("Enable-Legacy-Support")) {
            e.getBlock().setType(Material.AIR);
        } else {
            e.setDropItems(false);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void furnaceBreak(BlockBreakEvent e) {

        FileConfiguration config = plugin.getConfiguration();

        Player p = e.getPlayer();

        for (String disabledWorlds : config.getStringList("Disabled-Worlds"))
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (e.isCancelled()) return;
        if (!(e.getBlock().getType() == Material.FURNACE)) return;
        if (!config.getBoolean("AutoPickup.Enable-Autopickup")) return;
        if (autoPickupOFF.contains(p.getName())) return;
        if (e.getBlock().getState() instanceof Furnace) {
            Furnace furnaceBlock = ((Furnace) e.getBlock().getState());
            for (ItemStack itemsInTheFurnace : furnaceBlock.getInventory().getContents()) {
                if (itemsInTheFurnace != null) {
                    dropsItems(p, itemsInTheFurnace);
                    furnaceBlock.getInventory().clear();
                }
            }
            p.getInventory().addItem(furnace);
            removeDrops(e);
        }
    }
}