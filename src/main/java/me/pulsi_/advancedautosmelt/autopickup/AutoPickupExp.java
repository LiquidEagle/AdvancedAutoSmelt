package me.pulsi_.advancedautosmelt.autopickup;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Set;

public class AutoPickupExp implements Listener {

    Set<String> autoPickupOFF = Commands.autoPickupOFF;

    private final boolean isDCM;
    private final boolean isAutoPickupExp;

    public AutoPickupExp(AdvancedAutoSmelt plugin) {
        this.isDCM = plugin.isDCM();
        this.isAutoPickupExp = plugin.isAutoPickupExp();
    }

    @EventHandler
    public void blockEXP(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (isDCM) { if (p.getGameMode().equals(GameMode.CREATIVE)) return; }
        if (!isAutoPickupExp) return;
        if (autoPickupOFF.contains(p.getName())) return;
        int exp = e.getExpToDrop();
        e.setExpToDrop(0);
        p.giveExp(exp);
    }
}