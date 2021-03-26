package me.pulsi_.advancedautosmelt.commands;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.managers.ConfigManager;
import me.pulsi_.advancedautosmelt.managers.Translator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Commands implements CommandExecutor {

    public static Map<String, Boolean> toggleAutoPickup = new HashMap<>();
    public static Map<String, Boolean> toggleAutoSmelt = new HashMap<>();

    ConfigManager messages = new ConfigManager(AdvancedAutoSmelt.getInstance(), "messages.yml");

    String version = AdvancedAutoSmelt.getInstance().getDescription().getVersion();

    String noPerm = messages.getConfig().getString("no_permission_message");
    String reload = messages.getConfig().getString("reload_message");
    String unknownCommand = messages.getConfig().getString("unknown_command");
    String toggleOn = messages.getConfig().getString("toggled_on_message");
    String toggleOff = messages.getConfig().getString("toggled_off_message");

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

        if (args.length == 0) {
            s.sendMessage(Translator.Colors(""));
            s.sendMessage(Translator.Colors("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &bv%v%, By Pulsi_").replace("%v%",version));
            s.sendMessage(Translator.Colors(""));

        } else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            if (s.hasPermission("advancedautosmelt.help")) {
                s.sendMessage(Translator.Colors(""));
                s.sendMessage(Translator.Colors("&d  &lAdvanced&a&lAuto&c&lSmelt &aHelp"));
                s.sendMessage(Translator.Colors(""));
                s.sendMessage(Translator.Colors("&8- &c/AdvancedAutoSmelt Reload &7Reload the Plugin"));
                s.sendMessage(Translator.Colors("&8- &c/AdvancedAutoSmelt Help &7Send this help message"));
                s.sendMessage(Translator.Colors("&8- &c/AdvancedAutoSmelt List &7See the options of the config"));
                s.sendMessage(Translator.Colors("&8- &c/AdvancedAutoSmelt Toggle <Option> &7Toggle an option"));
                s.sendMessage(Translator.Colors("&8- &c/AdvancedAutoSmelt Info &7See the Info of the plugin"));
                s.sendMessage(Translator.Colors(""));
            } else {
                s.sendMessage(Translator.Colors(noPerm));
            }

        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (s.hasPermission("advancedautosmelt.reload")) {
                s.sendMessage(Translator.Colors(reload));
                messages.reloadConfig();
                AdvancedAutoSmelt.getInstance().reloadConfig();
            } else {
                s.sendMessage(Translator.Colors(noPerm));
            }

        } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            if (s.hasPermission("advancedautosmelt.list")) {
                s.sendMessage(Translator.Colors(""));
                s.sendMessage(Translator.Colors("&d   &lAdvanced&a&lAuto&c&lSmelt &aList"));
                s.sendMessage(Translator.Colors(""));
                s.sendMessage(Translator.Colors("&8- &7AutoSmelt Iron: " + AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.smelt_iron"))
                        .replace("true", Translator.Colors("&2Enabled"))
                        .replace("false", Translator.Colors("&cDisabled")));
                s.sendMessage(Translator.Colors("&8- &7AutoSmelt Gold: " + AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.smelt_gold"))
                        .replace("true", Translator.Colors("&2Enabled"))
                        .replace("false", Translator.Colors("&cDisabled")));
                s.sendMessage(Translator.Colors("&8- &7AutoSmelt Stone: " + AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.smelt_stone"))
                        .replace("true", Translator.Colors("&2Enabled"))
                        .replace("false", Translator.Colors("&cDisabled")));
                s.sendMessage(Translator.Colors("&8- &7AutoPickup: " + AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoPickup.enable_autopickup"))
                        .replace("true", Translator.Colors("&2Enabled"))
                        .replace("false", Translator.Colors("&cDisabled")));
                s.sendMessage(Translator.Colors("&8- &7AutoPickupExp " + AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoPickup.autopickup_experience"))
                        .replace("true", Translator.Colors("&2Enabled"))
                        .replace("false", Translator.Colors("&cDisabled")));
                s.sendMessage(Translator.Colors(""));
            } else {
                s.sendMessage(Translator.Colors(noPerm));
            }

        } else if (args.length == 1 && args[0].equalsIgnoreCase("info")) {
            if (s.hasPermission("advancedautosmelt.info")) {
                s.sendMessage(Translator.Colors(""));
                s.sendMessage(Translator.Colors("&d    &lAdvanced&a&lAuto&c&lSmelt &aInfo"));
                s.sendMessage(Translator.Colors(""));
                s.sendMessage(Translator.Colors("&8- &cAuthor: &aPulsi_"));
                s.sendMessage(Translator.Colors("&8- &cVersion: &av%version%").replace("%version%", AdvancedAutoSmelt.getInstance().getDescription().getVersion()));
                s.sendMessage(Translator.Colors("&8- &cPermissions: &a/AdvancedAutoSmelt Info Permissions"));
                s.sendMessage(Translator.Colors("&8- &cAliases: &aaautosmelt, autosmelt, asmelt, smelt, as"));
                s.sendMessage(Translator.Colors(""));
            } else {
                s.sendMessage(Translator.Colors(noPerm));
            }

        } else if (args.length == 2 && args[0].equalsIgnoreCase("info") && args[1].equalsIgnoreCase("permissions")) {
            if (s.hasPermission("advancedautosmelt.info")) {
                s.sendMessage(Translator.Colors("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &7Here you have all permissions list:"));
                s.sendMessage(Translator.Colors("&7advancedautosmelt.smelt.stone / advancedautosmelt.help"));
                s.sendMessage(Translator.Colors("&7advancedautosmelt.smelt.iron / advancedautosmelt.list"));
                s.sendMessage(Translator.Colors("&7advancedautosmelt.smelt.gold / advancedautosmelt.reload"));
                s.sendMessage(Translator.Colors("&7advancedautosmelt.pickup / advancedautosmelt.toggle.autopickup"));
                s.sendMessage(Translator.Colors("&7advancedautosmelt.pickupexp / advancedautosmelt.toggle.autosmelt"));
                s.sendMessage(Translator.Colors("&7advancedautosmelt.notify / advancedautosmelt.smeltinv"));
            } else {
                s.sendMessage(Translator.Colors(noPerm));
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("toggle")) {
            s.sendMessage(Translator.Colors("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &aAvailable options are: &7AutoPickup, AutoSmelt"));

        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("autopickup")) {
            Player p = (Player) s;
            if (p.hasPermission("advancedautosmelt.toggle.autopickup")) {
                if (s instanceof Player) {
                    if (!toggleAutoPickup.containsKey(p.getName())) {
                        toggleAutoPickup.put(p.getName(), true);
                        s.sendMessage(Translator.Colors(toggleOff)
                        .replace("%toggled_ability%","AutoPickup"));
                    } else {
                        toggleAutoPickup.remove(p.getName());
                        s.sendMessage(Translator.Colors(toggleOn)
                        .replace("%toggled_ability%","AutoPickup"));
                    }
                } else {
                    s.sendMessage(Translator.Colors("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &cOnly players can execute this command!"));
                }
            } else {
                s.sendMessage(Translator.Colors(noPerm));
            }

        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("autosmelt")) {
            Player p = (Player) s;
            if (p.hasPermission("advancedautosmelt.toggle.autosmelt")) {
                if (s instanceof Player) {
                    if (!toggleAutoSmelt.containsKey(p.getName())) {
                        toggleAutoSmelt.put(p.getName(), true);
                        s.sendMessage(Translator.Colors(toggleOff)
                        .replace("%toggled_ability%","AutoSmelt"));
                    } else {
                        toggleAutoSmelt.remove(p.getName());
                        s.sendMessage(Translator.Colors(toggleOn)
                        .replace("%toggled_ability%","AutoSmelt"));
                    }
                } else {
                    s.sendMessage(Translator.Colors("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &cOnly players can execute this command!"));
                }
            } else {
                s.sendMessage(Translator.Colors(noPerm));
            }

        } else {
            s.sendMessage(Translator.Colors(unknownCommand));
        }
        return true;
    }
}