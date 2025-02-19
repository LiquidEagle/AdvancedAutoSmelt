package me.pulsi_.advancedautosmelt.coreSystem;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class ContainerPickup {

    /**
     * Method to process the pickup of containers, instead of removing
     * the block and loose/drops all the items inside, pickup them first
     * and then remove the block. (This method only pickup inside items)
     *
     * @param p     The player that is breaking the block.
     * @param block The container block.
     */
    public static void containerPickup(Player p, Block block) {
        BlockState state = block.getState();
        if (!AdvancedAutoSmeltDropSystem.canAutoPickup(p, block) || !(state instanceof Container)) return;

        World world = block.getWorld();
        Location loc = p.getLocation();
        Container container = ((Container) state);

        for (ItemStack item : container.getInventory().getContents()) {
            if (item == null) continue;

            Collection<ItemStack> dropsLeft = p.getInventory().addItem(item).values();
            if (!dropsLeft.isEmpty()) for (ItemStack dropLeft : dropsLeft) world.dropItem(loc, dropLeft);
        }

        container.getInventory().clear();
    }
}