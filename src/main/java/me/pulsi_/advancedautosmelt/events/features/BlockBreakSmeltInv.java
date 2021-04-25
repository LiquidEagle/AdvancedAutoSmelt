package me.pulsi_.advancedautosmelt.events.features;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakSmeltInv implements Listener {

    private FileConfiguration config;
    private final boolean isSmeltInv;
    private final boolean isSmeltStoneInv;
    private final boolean isSmeltIronInv;
    private final boolean isSmeltGoldInv;

    public BlockBreakSmeltInv(AdvancedAutoSmelt plugin) {
        this.config = plugin.getConfiguration();
        this.isSmeltInv = config.getBoolean("AutoSmelt.Smelt-Ores-In-Inventory");
        this.isSmeltStoneInv = config.getBoolean("AutoSmelt.Inv-Smelt.Cobblestone");
        this.isSmeltIronInv = config.getBoolean("AutoSmelt.Inv-Smelt.Iron-Ore");
        this.isSmeltGoldInv = config.getBoolean("AutoSmelt.Inv-Smelt.Gold-Ore");
    }

    public static int getAmount(Player p, ItemStack items) {
        if (items == null)
            return 0;
        int amount = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack slot = p.getInventory().getItem(i);
            if (slot == null || !slot.isSimilar(items))
                continue;
            amount += slot.getAmount();
        }
        return amount;
    }

    @EventHandler
    public void smeltStoneInv(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (!isSmeltInv) return;
        if (!isSmeltStoneInv) return;
        if (!(p.hasPermission("advancedautosmelt.smeltinv"))) return;

        if (p.getInventory().containsAtLeast(new ItemStack(Material.COBBLESTONE), 1)) {
            int ironOreAmount = getAmount(p, new ItemStack(Material.COBBLESTONE));

            ItemStack stoneToSmelt = new ItemStack(Material.COBBLESTONE, ironOreAmount);
            ItemStack stoneToGive = new ItemStack(Material.STONE, ironOreAmount);

            p.getInventory().removeItem(stoneToSmelt);
            p.getInventory().addItem(stoneToGive);
        }
    }

    @EventHandler
    public void smeltIronInv(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (!isSmeltInv) return;
        if (!isSmeltIronInv) return;
        if (!(p.hasPermission("advancedautosmelt.smeltinv"))) return;

        if (p.getInventory().containsAtLeast(new ItemStack(Material.IRON_ORE), 1)) {
            int ironOreAmount = getAmount(p, new ItemStack(Material.IRON_ORE));

            ItemStack ironToSmelt = new ItemStack(Material.IRON_ORE, ironOreAmount);
            ItemStack ironToGive = new ItemStack(Material.IRON_INGOT, ironOreAmount);

            p.getInventory().removeItem(ironToSmelt);
            p.getInventory().addItem(ironToGive);
        }
    }

    @EventHandler
    public void smeltGoldInv(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (!isSmeltInv) return;
        if (!isSmeltGoldInv) return;
        if (!(p.hasPermission("advancedautosmelt.smeltinv"))) return;

        if (p.getInventory().containsAtLeast(new ItemStack(Material.GOLD_ORE), 1)) {
            int goldOreAmount = getAmount(p, new ItemStack(Material.GOLD_ORE));

            ItemStack goldToSmelt = new ItemStack(Material.GOLD_ORE, goldOreAmount);
            ItemStack goldToGive = new ItemStack(Material.GOLD_INGOT, goldOreAmount);

            p.getInventory().removeItem(goldToSmelt);
            p.getInventory().addItem(goldToGive);
        }
    }
}