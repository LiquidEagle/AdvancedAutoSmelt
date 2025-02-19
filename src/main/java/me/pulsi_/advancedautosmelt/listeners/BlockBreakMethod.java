package me.pulsi_.advancedautosmelt.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.coreSystem.*;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakMethod {

    /**
     * The main method to share in different listener priorities.
     *
     * @param e The block break event.
     */
    public static void onBlockBreak(BlockBreakEvent e) {
        if (e.isCancelled()) return;

        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE) return;

        Block block = e.getBlock();
        if (!checkWorldGuard(p, block)) return; // Avoid managing blocks that shouldn't be broken.

        ContainerPickup.containerPickup(p, block);

        ItemStack itemUsed = p.getItemInHand();
        int fortune = itemUsed.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
        AdvancedAutoSmeltDropSystem.giveDrops(p, block, itemUsed, fortune);
        e.setDropItems(false);

        InventorySmelter.smeltInventory(p);
        InventorySmelter.inventoryIngotToBlock(p);
        InventoryAlerts.inventoryAlerts(p);
    }

    /**
     * Check if the broken block can be broken from the selected player.
     *
     * @param p     The player.
     * @param block The block.
     * @return true if the player can break it, false otherwise.
     */
    public static boolean checkWorldGuard(Player p, Block block) {
        if (!AdvancedAutoSmelt.INSTANCE().isWorldGuardHooked()) return true;

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(block.getLocation());
        return container.createQuery().testState(loc, WorldGuardPlugin.inst().wrapPlayer(p), Flags.BLOCK_BREAK);
    }
}