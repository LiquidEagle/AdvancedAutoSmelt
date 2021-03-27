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

    private AdvancedAutoSmelt plugin;
    private int goldExp;
    private int ironExp;
    private Set autoPickupOFF;

    public AutoPickupExpCustom(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
        this.goldExp = plugin.getGoldExp();
        this.ironExp = plugin.getIronExp();
        this.autoPickupOFF = Commands.autoPickupOFF;
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void pickupGoldExp(BlockBreakEvent e) {

        if (!(e.getBlock().getType() == Material.GOLD_ORE)) return;

        Player p = e.getPlayer();

        if (!(plugin.getConfig().getBoolean("AutoSmelt.give-exp-gold"))) return;
        if (plugin.getConfig().getBoolean("AutoSmelt.disable-creative-mode")) {
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;
            if (plugin.getConfig().getBoolean("AutoPickup.autopickup-experience")) {

                if (!autoPickupOFF.contains(p.getName())) {
                    p.giveExp(goldExp);
                } else {
                    e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(goldExp);
                }

            } else {
                e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(goldExp);
            }

        } else {

            if (plugin.getConfig().getBoolean("AutoPickup.autopickup-experience")) {

                if (!autoPickupOFF.contains(p.getName())) {
                    p.giveExp(goldExp);

                } else {

                    e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(goldExp);
                }
            }
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void pickupIronExp(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (!(plugin.getConfig().getBoolean("AutoSmelt.give-exp-iron"))) return;
        if (plugin.getConfig().getBoolean("AutoSmelt.disable-creative-mode")) {
            if (plugin.getConfig().getBoolean("AutoPickup.autopickup-experience")) {

                if (!autoPickupOFF.contains(p.getName())) {
                    if (p.getGameMode().equals(GameMode.CREATIVE)) return;
                    if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
                    p.giveExp(ironExp);
                } else {
                    if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
                    e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(ironExp);
                }
            } else {

                if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
                e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(ironExp);
            }
        } else {

            if (plugin.getConfig().getBoolean("AutoPickup.autopickup-experience")) {

                if (!autoPickupOFF.contains(p.getName())) {
                    if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
                    p.giveExp(ironExp);
                } else {
                    if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
                    e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(ironExp);
                }
            } else {

                if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
                e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(ironExp);
            }
        }
    }
}