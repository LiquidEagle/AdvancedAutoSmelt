package me.pulsi_.advancedautosmelt.listeners.blockBreakListener;

import me.pulsi_.advancedautosmelt.listeners.BlockBreakMethod;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListenerLow implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent e) {
        BlockBreakMethod.onBlockBreak(e);
    }
}