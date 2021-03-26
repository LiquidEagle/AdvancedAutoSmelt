package me.pulsi_.advancedautosmelt.pickupexp;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Map;

public class PickupExpIron implements Listener {

    int exp = AdvancedAutoSmelt.getInstance().getConfig().getInt("AutoSmelt.iron_exp");

    Map autoPickupOFF = Commands.toggleAutoPickup;

    @EventHandler
    public void pickupExp(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!(AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.give_exp_iron"))) return;
        if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.disable-creative-mode")) {
            if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoPickup.autopickup_experience")) {

                if (!(autoPickupOFF.containsKey(p.getName()))) {
                    if (p.getGameMode().equals(GameMode.CREATIVE)) return;
                    if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
                    p.giveExp(exp);
                } else {
                    if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
                    e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(exp);
                }
            } else {

                if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
                e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(exp);
            }
        } else {

            if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoPickup.autopickup_experience")) {

                if (!(autoPickupOFF.containsKey(p.getName()))) {
                    if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
                    p.giveExp(exp);
                } else {
                    if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
                    e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(exp);
                }
            } else {

                if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
                e.getBlock().getLocation().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(exp);
            }
        }
    }
}