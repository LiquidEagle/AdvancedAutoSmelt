package me.pulsi_.advancedautosmelt.mechanics;

import me.pulsi_.advancedautosmelt.utils.Methods;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ContainersPickup {

    public static void pickupContainer(Player p, BlockBreakEvent e) {
        Block block = e.getBlock();
        Container container = ((Container) block.getState());
        for (ItemStack item : container.getInventory().getContents())
            Methods.giveDrops(p, block, item);
        container.getInventory().clear();
        Methods.giveDrops(p, block, new ItemStack(Material.valueOf(block.getType().toString())));
        Methods.removeDrops(e);
    }
}