package me.pulsi_.advancedautosmelt.commands;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.managers.Translator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Commands implements CommandExecutor {

    public static Set<String> autoPickupOFF = new HashSet<>();
    public static Set<String> autoSmeltOFF = new HashSet<>();

    private AdvancedAutoSmelt plugin;
    public Commands(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

        if (args.length == 0) {
            s.sendMessage(Translator.c(""));
            s.sendMessage(Translator.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &bv%v%, By Pulsi_").replace("%v%",plugin.getVersion()));
            s.sendMessage(Translator.c(""));

        } else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            if (s.hasPermission("advancedautosmelt.help")) {
                s.sendMessage(Translator.c(""));
                s.sendMessage(Translator.c("&d  &lAdvanced&a&lAuto&c&lSmelt &aHelp"));
                s.sendMessage(Translator.c(""));
                s.sendMessage(Translator.c("&8- &c/AdvancedAutoSmelt Reload &7Reload the Plugin"));
                s.sendMessage(Translator.c("&8- &c/AdvancedAutoSmelt Help &7Send this help message"));
                s.sendMessage(Translator.c("&8- &c/AdvancedAutoSmelt List &7See the options of the config"));
                s.sendMessage(Translator.c("&8- &c/AdvancedAutoSmelt Toggle <Option> &7Toggle an option"));
                s.sendMessage(Translator.c("&8- &c/AdvancedAutoSmelt Info &7See the Info of the plugin"));
                s.sendMessage(Translator.c(""));
            } else {
                s.sendMessage(Translator.c(plugin.getNoPerm()));
            }

        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (s.hasPermission("advancedautosmelt.reload")) {
                s.sendMessage(Translator.c(plugin.getReload()));
                plugin.reloadConfig();
            } else {
                s.sendMessage(Translator.c(plugin.getNoPerm()));
            }

        } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            if (s.hasPermission("advancedautosmelt.list")) {
                s.sendMessage(Translator.c(""));
                s.sendMessage(Translator.c("&d   &lAdvanced&a&lAuto&c&lSmelt &aList"));
                s.sendMessage(Translator.c(""));
                s.sendMessage(Translator.c("&8- &7AutoSmelt Iron: " + plugin.getConfig().getBoolean("AutoSmelt.smelt_iron"))
                        .replace("true", Translator.c("&2Enabled"))
                        .replace("false", Translator.c("&cDisabled")));
                s.sendMessage(Translator.c("&8- &7AutoSmelt Gold: " + plugin.getConfig().getBoolean("AutoSmelt.smelt_gold"))
                        .replace("true", Translator.c("&2Enabled"))
                        .replace("false", Translator.c("&cDisabled")));
                s.sendMessage(Translator.c("&8- &7AutoSmelt Stone: " + plugin.getConfig().getBoolean("AutoSmelt.smelt_stone"))
                        .replace("true", Translator.c("&2Enabled"))
                        .replace("false", Translator.c("&cDisabled")));
                s.sendMessage(Translator.c("&8- &7AutoPickup: " + plugin.getConfig().getBoolean("AutoPickup.enable_autopickup"))
                        .replace("true", Translator.c("&2Enabled"))
                        .replace("false", Translator.c("&cDisabled")));
                s.sendMessage(Translator.c("&8- &7AutoPickupExp " + plugin.getConfig().getBoolean("AutoPickup.autopickup_experience"))
                        .replace("true", Translator.c("&2Enabled"))
                        .replace("false", Translator.c("&cDisabled")));
                s.sendMessage(Translator.c(""));
            } else {
                s.sendMessage(Translator.c(plugin.getNoPerm()));
            }

        } else if (args.length == 1 && args[0].equalsIgnoreCase("info")) {
            if (s.hasPermission("advancedautosmelt.info")) {
                s.sendMessage(Translator.c(""));
                s.sendMessage(Translator.c("&d    &lAdvanced&a&lAuto&c&lSmelt &aInfo"));
                s.sendMessage(Translator.c(""));
                s.sendMessage(Translator.c("&8- &cAuthor: &aPulsi_"));
                s.sendMessage(Translator.c("&8- &cVersion: &av%v%").replace("%v%", plugin.getDescription().getVersion()));
                s.sendMessage(Translator.c("&8- &cPermissions: &a/AdvancedAutoSmelt Info Permissions"));
                s.sendMessage(Translator.c("&8- &cAliases: &aaautosmelt, autosmelt, asmelt, smelt, as"));
                s.sendMessage(Translator.c(""));
            } else {
                s.sendMessage(Translator.c(plugin.getNoPerm()));
            }

        } else if (args.length == 2 && args[0].equalsIgnoreCase("info") && args[1].equalsIgnoreCase("permissions")) {
            if (s.hasPermission("advancedautosmelt.info")) {
                s.sendMessage(Translator.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &7Here you have all permissions list:"));
                s.sendMessage(Translator.c("&7advancedautosmelt.smelt.stone / advancedautosmelt.help"));
                s.sendMessage(Translator.c("&7advancedautosmelt.smelt.iron / advancedautosmelt.list"));
                s.sendMessage(Translator.c("&7advancedautosmelt.smelt.gold / advancedautosmelt.reload"));
                s.sendMessage(Translator.c("&7advancedautosmelt.pickup / advancedautosmelt.toggle.autopickup"));
                s.sendMessage(Translator.c("&7advancedautosmelt.pickupexp / advancedautosmelt.toggle.autosmelt"));
                s.sendMessage(Translator.c("&7advancedautosmelt.notify / advancedautosmelt.smeltinv"));
            } else {
                s.sendMessage(Translator.c(plugin.getNoPerm()));
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("toggle")) {
            s.sendMessage(Translator.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &aAvailable options are: &7AutoPickup, AutoSmelt"));

        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("autopickup")) {
            Player p = (Player) s;
            if (p.hasPermission("advancedautosmelt.toggle.autopickup")) {
                if (s instanceof Player) {
                    if (!autoPickupOFF.contains(p.getName())) {
                        autoPickupOFF.add(p.getName());
                        s.sendMessage(Translator.c(plugin.getToggleOff())
                        .replace("%toggled_ability%","AutoPickup"));
                    } else {
                        autoPickupOFF.remove(p.getName());
                        s.sendMessage(Translator.c(plugin.getToggleOn())
                        .replace("%toggled_ability%","AutoPickup"));
                    }
                } else {
                    s.sendMessage(Translator.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &cOnly players can execute this command!"));
                }
            } else {
                s.sendMessage(Translator.c(plugin.getNoPerm()));
            }

        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("autosmelt")) {
            Player p = (Player) s;
            if (p.hasPermission("advancedautosmelt.toggle.autosmelt")) {
                if (s instanceof Player) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        autoSmeltOFF.add(p.getName());
                        s.sendMessage(Translator.c(plugin.getToggleOff())
                        .replace("%toggled_ability%","AutoSmelt"));
                    } else {
                        autoSmeltOFF.remove(p.getName());
                        s.sendMessage(Translator.c(plugin.getToggleOn())
                        .replace("%toggled_ability%","AutoSmelt"));
                    }
                } else {
                    s.sendMessage(Translator.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &cOnly players can execute this command!"));
                }
            } else {
                s.sendMessage(Translator.c(plugin.getNoPerm()));
            }

        } else {
            s.sendMessage(Translator.c(plugin.getUnknownCommand()));
        }
        return true;
    }
}