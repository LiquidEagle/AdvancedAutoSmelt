package me.pulsi_.advancedautosmelt.listeners.blockBreakListener;

import me.pulsi_.advancedautosmelt.coreSystem.AdvancedAutoSmeltDropSystem;
import me.pulsi_.advancedautosmelt.coreSystem.ExtraFeatures;
import me.pulsi_.advancedautosmelt.listeners.BlockBreakMethod;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakListenerLowest implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent e) {
        BlockBreakMethod.onBlockBreak(e);
    }
}