package me.pulsi_.advancedautosmelt.mechanics;

import me.pulsi_.advancedautosmelt.utils.AASApi;
import me.pulsi_.advancedautosmelt.utils.AASLogger;
import me.pulsi_.advancedautosmelt.utils.Methods;
import me.pulsi_.advancedautosmelt.values.Values;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class AutoPickup {

    public static void processAutoPickup(BlockBreakEvent e) {
        Block block = e.getBlock();
        Player p = e.getPlayer();
        damageItem(e, p);

        if (block.getState() instanceof Container) {
            ContainersPickup.pickupContainer(p, e);
            return;
        }

        Collection<ItemStack> blockDrops;
        if (Values.getConfig().isDropNeedCorrectItem()) {
            blockDrops = block.getDrops(p.getItemInHand());
        } else {
            blockDrops = block.getDrops();
        }

        if (AASApi.canAutoSmelt(p)) {
            for (String line : Values.getConfig().getAutoSmeltList()) {
                if (!line.contains(";")) continue;

                String brokenBlock = line.split(";")[0];
                if (!block.getType().toString().equals(brokenBlock)) continue;

                String newDropIdentifier = line.split(";")[1];

                ItemStack newDrop;
                try {
                    newDrop = new ItemStack(Material.valueOf(newDropIdentifier));
                } catch (IllegalArgumentException ex) {
                    AASLogger.error("Unknown input for &a\"" + newDropIdentifier + "\"&c: " + ex.getMessage());
                    return;
                }

                Methods.giveDrops(p, block, newDrop);
                Methods.removeDrops(e);
                return;
            }
        }
        for (ItemStack drops : blockDrops) Methods.giveDrops(p, block, drops);
        Methods.removeDrops(e);
    }

    private static void damageItem(BlockBreakEvent e, Player p) {
        if (!Values.getConfig().isEnableLegacySupport() || !Methods.blockDoesDamage(e.getBlock())) return;

        ItemStack item = p.getInventory().getItemInHand();
        int maxDurability = item.getType().getMaxDurability();
        if (maxDurability <= 0) return;

        int damage = item.getDurability();

        int level = item.getItemMeta().getEnchantLevel(Enchantment.DURABILITY);
        boolean shouldDamage = (Math.random() < (1 / (level + 1)));

        if (shouldDamage) item.setDurability((short) (damage + 1));
        if ((damage + 1 ) >= maxDurability) Methods.destroyItem(p);
    }
}