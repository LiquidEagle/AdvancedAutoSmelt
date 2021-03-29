package me.pulsi_.advancedautosmelt.autopickup;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Set;

public class AutoPickupExpCustom implements Listener {

    private int goldExp;
    private int ironExp;
    private Set autoPickupOFF;
    private boolean isAutoPickupExp;
    private boolean isGivingGoldExp;
    private boolean isGivingIronExp;
    private boolean isAutoSmeltDCM;

    public AutoPickupExpCustom(AdvancedAutoSmelt plugin) {
        this.isAutoSmeltDCM = plugin.isDCM();
        this.goldExp = plugin.getGoldExp();
        this.ironExp = plugin.getIronExp();
        this.autoPickupOFF = Commands.autoPickupOFF;
        this.isAutoPickupExp = plugin.isAutoPickupExp();
        this.isGivingGoldExp = plugin.isGivingGoldExp();
        this.isGivingIronExp = plugin.isGivingIronExp();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void pickupGoldExp(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (!(e.getBlock().getType() == Material.GOLD_ORE)) return;
        if (!isGivingGoldExp) return;
        if (isAutoSmeltDCM) {
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;
        }
        if (isAutoPickupExp) {
            if (!autoPickupOFF.contains(p.getName())) {
                p.giveExp(goldExp);
            } else {
                e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(goldExp);
            }
        } else {
            e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(goldExp);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void pickupIronExp(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
        if (!isGivingIronExp) return;
        if (isAutoSmeltDCM) { if (p.getGameMode().equals(GameMode.CREATIVE)) return; }
        if (isAutoPickupExp) {
            if (!autoPickupOFF.contains(p.getName())) {
                p.giveExp(ironExp);
            } else {
                e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(ironExp);
            }
        } else {
            e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(ironExp);
        }
    }
}