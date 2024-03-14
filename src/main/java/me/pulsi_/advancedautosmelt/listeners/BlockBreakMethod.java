package me.pulsi_.advancedautosmelt.listeners;

import me.pulsi_.advancedautosmelt.coreSystem.AdvancedAutoSmeltDropSystem;
import me.pulsi_.advancedautosmelt.coreSystem.ExtraFeatures;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakMethod {

    public static void onBlockBreak(BlockBreakEvent e) {
        if (e.isCancelled()) return;

        Player p = e.getPlayer();
        if (p.getGameMode().equals(GameMode.CREATIVE)) return;

        Block block = e.getBlock();
        ExtraFeatures.containerPickup(p, block);

        ItemStack itemUsed = p.getItemInHand();
        int fortune = itemUsed.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
        AdvancedAutoSmeltDropSystem.giveDrops(p, block, itemUsed, fortune);
        e.setDropItems(false);

        ExtraFeatures.smeltInventory(p);
        ExtraFeatures.inventoryIngotToBlock(p);
        ExtraFeatures.inventoryAlerts(p);
    }
}