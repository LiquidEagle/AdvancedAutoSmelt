package me.pulsi_.advancedautosmelt.autopickup;

import me.pulsi_.advancedautosmelt.commands.Commands;
import me.pulsi_.advancedautosmelt.managers.DataManager;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.Set;

public class AutoPickupExpCustom implements Listener {

    private final List<String> worldsBlackList;
    private final int goldExp;
    private final int ironExp;
    private final Set<String> autoPickupOFF;
    private final boolean isAutoPickupExp;
    private final boolean isGivingGoldExp;
    private final boolean isGivingIronExp;
    private final boolean isAutoSmeltDCM;

    public AutoPickupExpCustom(DataManager dm) {
        this.isAutoSmeltDCM = dm.isDCM();
        this.goldExp = dm.getGoldExp();
        this.ironExp = dm.getIronExp();
        this.autoPickupOFF = Commands.autoPickupOFF;
        this.isAutoPickupExp = dm.isAutoPickupExp();
        this.isGivingGoldExp = dm.isGivingGoldExp();
        this.isGivingIronExp = dm.isGivingIronExp();
        this.worldsBlackList = dm.getWorldsBlackList();
    }

    @EventHandler
    public void pickupGoldExp(BlockBreakEvent e) {

        Player p = e.getPlayer();

        for (String disabledWorlds : worldsBlackList)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (e.isCancelled()) return;
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

    @EventHandler
    public void pickupIronExp(BlockBreakEvent e) {

        Player p = e.getPlayer();

        for (String disabledWorlds : worldsBlackList)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (e.isCancelled()) return;
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