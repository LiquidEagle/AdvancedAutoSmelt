package me.pulsi_.advancedautosmelt.autopickup;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import me.pulsi_.advancedautosmelt.utils.MethodUtils;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Set;

public class AutoPickupExp implements Listener {

    private MethodUtils methodUtils;
    private AdvancedAutoSmelt plugin;
    private Set<String> autoPickupOFF;
    public AutoPickupExp(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
        this.autoPickupOFF = Commands.autoPickupOFF;
        this.methodUtils = new MethodUtils(plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockEXP(BlockBreakEvent e) {

        FileConfiguration config = plugin.getConfiguration();

        Player p = e.getPlayer();

        for (String disabledWorlds : config.getStringList("Disabled-Worlds"))
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        methodUtils.checkPickaxe(p);

        if (e.isCancelled()) return;
        int exp = e.getExpToDrop();
        if (exp == 0) return;

        if (config.getBoolean("AutoSmelt.Disable-Creative-Mode")) { if (p.getGameMode().equals(GameMode.CREATIVE)) return; }
        if (config.getBoolean("AutoPickup.Autopickup-Experience")) {
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