package me.pulsi_.advancedautosmelt.commands;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Commands implements CommandExecutor {

    private AdvancedAutoSmelt plugin;
    private String version;
    public Commands(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
        this.version = plugin.getDescription().getVersion();
    }

    public static Set<String> autoPickupOFF = new HashSet<>();
    public static Set<String> autoSmeltOFF = new HashSet<>();
    public static Set<String> inventoryFullOFF = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

        FileConfiguration config = plugin.getConfiguration();
        FileConfiguration messages = plugin.getMessages();

        String noPerm = messages.getString("No-Permission");

        if (args.length == 0) {
            s.sendMessage("");
            s.sendMessage(ChatUtils.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &bv%v%, By Pulsi_").replace("%v%",version));
            s.sendMessage("");

        } else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            if (s.hasPermission("advancedautosmelt.help")) {
                s.sendMessage("");
                s.sendMessage(ChatUtils.c("&d  &lAdvanced&a&lAuto&c&lSmelt &aHelp"));
                s.sendMessage("");
                s.sendMessage(ChatUtils.c("&8- &f/as reload &7Reload the Plugin"));
                s.sendMessage(ChatUtils.c("&8- &f/as help &7Send this help message"));
                s.sendMessage(ChatUtils.c("&8- &f/as list &7See the options of the config"));
                s.sendMessage(ChatUtils.c("&8- &f/as toggle <option> &7Toggle an option"));
                s.sendMessage(ChatUtils.c("&8- &f/as info &7See the Info of the plugin"));
                s.sendMessage(ChatUtils.c("&8- &f/as restart &7Restart the plugin (&c&lDon't use it as a reload command!&7)"));
                s.sendMessage("");
            } else {
                s.sendMessage(ChatUtils.c(noPerm).replace("%permission%", "advancedautosmelt.help"));
            }

        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (s.hasPermission("advancedautosmelt.reload")) {
                if (s instanceof Player) {
                    s.sendMessage("");
                    s.sendMessage(ChatUtils.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &2Reloading Files.."));
                    s.sendMessage(ChatUtils.c("&8[&2&lSUCCESS&8] &fMessages.yml loaded!"));
                    s.sendMessage(ChatUtils.c("&8[&2&lSUCCESS&8] &fConfig.yml loaded!"));
                    s.sendMessage(ChatUtils.c("&8[&2&lSUCCESS&8] &fSuccessfully reloaded the plugin!"));
                    s.sendMessage("");
                    plugin.getServer().getConsoleSender().sendMessage("");
                    plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &2Reloading Files.."));
                    plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&8[&2&lSUCCESS&8] &fMessages.yml loaded!"));
                    plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&8[&2&lSUCCESS&8] &fConfig.yml loaded!"));
                    plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&8[&2&lSUCCESS&8] &fSuccessfully reloaded the plugin!"));
                    plugin.getServer().getConsoleSender().sendMessage("");
                } else {
                    s.sendMessage("");
                    s.sendMessage(ChatUtils.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &2Reloading Files.."));
                    s.sendMessage(ChatUtils.c("&8[&2&lSUCCESS&8] &fMessages.yml loaded!"));
                    s.sendMessage(ChatUtils.c("&8[&2&lSUCCESS&8] &fConfig.yml loaded!"));
                    s.sendMessage(ChatUtils.c("&8[&2&lSUCCESS&8] &fSuccessfully reloaded the plugin!"));
                    s.sendMessage("");
                }
                plugin.reloadConfigs();
            } else {
                s.sendMessage(ChatUtils.c(noPerm).replace("%permission%", "advancedautosmelt.reload"));
            }

        } else if (args.length == 1 && args[0].equalsIgnoreCase("restart")) {
            if (s.hasPermission("advancedautosmelt.restart")) {
                s.sendMessage("");
                s.sendMessage(ChatUtils.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &2Restarting the plugin..."));
                Bukkit.getPluginManager().disablePlugin(plugin);
                Bukkit.getPluginManager().enablePlugin(plugin);
                s.sendMessage(ChatUtils.c("&8[&2&lSUCCESS&8] &fNow everything should work!"));
                s.sendMessage("");
            } else {
                s.sendMessage(ChatUtils.c(noPerm).replace("%permission%", "advancedautosmelt.restart"));
            }

        } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            if (s.hasPermission("advancedautosmelt.list")) {
                s.sendMessage("");
                s.sendMessage(ChatUtils.c("&d   &lAdvanced&a&lAuto&c&lSmelt &aList"));
                s.sendMessage("");
                s.sendMessage(ChatUtils.c("&8- &7AutoSmelt Iron: " + config.getBoolean("AutoSmelt.Smelt-Iron")).replace("true", ChatUtils.c("&2Enabled"))
                        .replace("false", ChatUtils.c("&cDisabled")));
                s.sendMessage(ChatUtils.c("&8- &7AutoSmelt Gold: " + config.getBoolean("AutoSmelt.Smelt-Gold")).replace("true", ChatUtils.c("&2Enabled"))
                        .replace("false", ChatUtils.c("&cDisabled")));
                s.sendMessage(ChatUtils.c("&8- &7AutoSmelt Stone: " + config.getBoolean("AutoSmelt.Smelt-Stone")).replace("true", ChatUtils.c("&2Enabled"))
                        .replace("false", ChatUtils.c("&cDisabled")));
                s.sendMessage(ChatUtils.c("&8- &7AutoPickup: " + config.getBoolean("AutoPickup.Enable-Autopickup")).replace("true", ChatUtils.c("&2Enabled"))
                        .replace("false", ChatUtils.c("&cDisabled")));
                s.sendMessage(ChatUtils.c("&8- &7AutoPickupExp " + config.getBoolean("AutoPickup.Autopickup-Experience")).replace("true", ChatUtils.c("&2Enabled"))
                        .replace("false", ChatUtils.c("&cDisabled")));
                s.sendMessage(ChatUtils.c(""));
            } else {
                s.sendMessage(ChatUtils.c(noPerm).replace("%permission%", "advancedautosmelt.list"));
            }

        } else if (args.length == 1 && args[0].equalsIgnoreCase("info")) {
            if (s.hasPermission("advancedautosmelt.info")) {
                s.sendMessage("");
                s.sendMessage(ChatUtils.c("&d    &lAdvanced&a&lAuto&c&lSmelt &aInfo"));
                s.sendMessage("");
                s.sendMessage(ChatUtils.c("&8- &fAuthor: &aPulsi_"));
                s.sendMessage(ChatUtils.c("&8- &fVersion: &av%v%").replace("%v%", plugin.getDescription().getVersion()));
                s.sendMessage(ChatUtils.c("&8- &fPermissions: &a/as info permissions <number>"));
                s.sendMessage(ChatUtils.c("&8- &fAliases: &aaautosmelt, autosmelt, asmelt, smelt, as"));
                s.sendMessage("");
            } else {
                s.sendMessage(ChatUtils.c(noPerm).replace("%permission%", "advancedautosmelt.info"));
            }

        } else if (args.length == 2 && args[0].equalsIgnoreCase("info") && args[1].equalsIgnoreCase("permissions") ||
                args.length == 3 && args[0].equalsIgnoreCase("info") && args[1].equalsIgnoreCase("permissions")
                        && args[2].equalsIgnoreCase("1")) {
            if (s.hasPermission("advancedautosmelt.info")) {
                s.sendMessage("");
                s.sendMessage(ChatUtils.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &7Permissions List &8(&a1/3&8)"));
                s.sendMessage(ChatUtils.c("&aSmelt Stone: &fadvancedautosmelt.smelt.stone"));
                s.sendMessage(ChatUtils.c("&aSmelt Iron: &fadvancedautosmelt.smelt.iron"));
                s.sendMessage(ChatUtils.c("&aSmelt Gold: &fadvancedautosmelt.smelt.gold"));
                s.sendMessage(ChatUtils.c("&aSmeltInv: &fadvancedautosmelt.smeltinv"));
                s.sendMessage(ChatUtils.c("&aAutopickup: &fadvancedautosmelt.autopickup"));
                s.sendMessage("");
            } else {
                s.sendMessage(ChatUtils.c(noPerm).replace("%permission%", "advancedautosmelt.info"));
            }

        } else if (args.length == 3 && args[0].equalsIgnoreCase("info") && args[1].equalsIgnoreCase("permissions") && args[2].equalsIgnoreCase("2")) {
            if (s.hasPermission("advancedautosmelt.info")) {
                s.sendMessage("");
                s.sendMessage(ChatUtils.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &7Permissions List &8(&a2/3&8)"));
                s.sendMessage(ChatUtils.c("&aAutopickupExp: &fadvancedautosmelt.autopickupexp"));
                s.sendMessage(ChatUtils.c("&aUpdate Notify: &fadvancedautosmelt.notify"));
                s.sendMessage(ChatUtils.c("&aUse Fortune: &fadvancedautosmelt.fortune"));
                s.sendMessage(ChatUtils.c("&a/as help: &fadvancedautosmelt.help"));
                s.sendMessage(ChatUtils.c("&a/as list: &fadvancedautosmelt.list"));
                s.sendMessage("");
            } else {
                s.sendMessage(ChatUtils.c(noPerm).replace("%permission%", "advancedautosmelt.info"));
            }

        } else if (args.length == 3 && args[0].equalsIgnoreCase("info") && args[1].equalsIgnoreCase("permissions") && args[2].equalsIgnoreCase("3")) {
            if (s.hasPermission("advancedautosmelt.info")) {
                s.sendMessage("");
                s.sendMessage(ChatUtils.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &7Permissions List &8(&a3/3&8)"));
                s.sendMessage(ChatUtils.c("&a/as reload: &fadvancedautosmelt.reload"));
                s.sendMessage(ChatUtils.c("&a/as toggle autopickup: &fadvancedautosmelt.toggle.autopickup"));
                s.sendMessage(ChatUtils.c("&a/as toggle autosmelt: &fadvancedautosmelt.toggle.autosmelt"));
                s.sendMessage(ChatUtils.c("&a/as toggle inventoryfull: &fadvancedautosmelt.toggle.invfull"));
                s.sendMessage("");
            } else {
                s.sendMessage(ChatUtils.c(noPerm).replace("%permission%", "advancedautosmelt.info"));
            }

        } else if (args.length == 1 && args[0].equalsIgnoreCase("toggle")) {
            s.sendMessage(ChatUtils.c(messages.getString("Available-Options")));

        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("autopickup")) {
            Player p = (Player) s;
            if (p.hasPermission("advancedautosmelt.toggle.autopickup")) {
                if (s instanceof Player) {
                    if (!autoPickupOFF.contains(p.getName())) {
                        autoPickupOFF.add(p.getName());
                        s.sendMessage(ChatUtils.c(messages.getString("Toggled-Off-Message")).replace("%toggled_ability%", "AutoPickup"));
                    } else {
                        autoPickupOFF.remove(p.getName());
                        s.sendMessage(ChatUtils.c(messages.getString("Toggled-On-Message")).replace("%toggled_ability%", "AutoPickup"));
                    }
                } else {
                    s.sendMessage(ChatUtils.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &cOnly players can execute this command!"));
                }
            } else {
                s.sendMessage(ChatUtils.c(noPerm).replace("%permission%", "advancedautosmelt.toggle.autopickup"));
            }

        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("autosmelt")) {
            Player p = (Player) s;
            if (p.hasPermission("advancedautosmelt.toggle.autosmelt")) {
                if (s instanceof Player) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        autoSmeltOFF.add(p.getName());
                        s.sendMessage(ChatUtils.c(messages.getString("Toggled-Off-Message")).replace("%toggled_ability%", "AutoSmelt"));
                    } else {
                        autoSmeltOFF.remove(p.getName());
                        s.sendMessage(ChatUtils.c(messages.getString("Toggled-On-Message")).replace("%toggled_ability%", "AutoSmelt"));
                    }
                } else {
                    s.sendMessage(ChatUtils.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &cOnly players can execute this command!"));
                }
            } else {
                s.sendMessage(ChatUtils.c(noPerm).replace("%permission%", "advancedautosmelt.toggle.autosmelt"));
            }

        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("inventoryfull")) {
            Player p = (Player) s;
            if (p.hasPermission("advancedautosmelt.toggle.inventoryfull")) {
                if (s instanceof Player) {
                    if (!inventoryFullOFF.contains(p.getName())) {
                        inventoryFullOFF.add(p.getName());
                        s.sendMessage(ChatUtils.c(messages.getString("Toggled-Off-Message")).replace("%toggled_ability%", "InventoryFull"));
                    } else {
                        inventoryFullOFF.remove(p.getName());
                        s.sendMessage(ChatUtils.c(messages.getString("Toggled-On-Message")).replace("%toggled_ability%", "InventoryFull"));
                    }
                } else {
                    s.sendMessage(ChatUtils.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &cOnly players can execute this command!"));
                }
            } else {
                s.sendMessage(ChatUtils.c(noPerm).replace("%permission%", "advancedautosmelt.toggle.inventoryfull"));
            }

        } else {
            s.sendMessage(ChatUtils.c(messages.getString("Unknown-Command")));
        }
        return true;
    }
}