package me.pulsi_.advancedautosmelt.autopickup;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import org.bukkit.GameMode;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.Set;

public class AutoPickupExp implements Listener {

    private final List<String> worldsBlackList;
    private final Set<String> autoPickupOFF;
    private final boolean isDCM;
    private final boolean isAutoPickupExp;

    public AutoPickupExp(AdvancedAutoSmelt plugin) {
        this.isDCM = plugin.isDCM();
        this.isAutoPickupExp = plugin.isAutoPickupExp();
        this.worldsBlackList = plugin.getWorldsBlackList();
        this.autoPickupOFF = Commands.autoPickupOFF;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockEXP(BlockBreakEvent e) {

        Player p = e.getPlayer();

        for (String disabledWorlds : worldsBlackList)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (e.isCancelled()) return;
        int exp = e.getExpToDrop();
        if (exp == 0) return;

        if (isDCM) { if (p.getGameMode().equals(GameMode.CREATIVE)) return; }
        if (isAutoPickupExp) {
            if (!autoPickupOFF.contains(p.getName())) {
                e.setExpToDrop(0);
                p.giveExp(exp);
            } else {
                e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(exp);
            }
        } else {
            e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(exp);
        }
    }
}