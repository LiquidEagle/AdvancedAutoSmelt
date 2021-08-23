package me.pulsi_.advancedautosmelt.commands;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.managers.MessageManager;
import me.pulsi_.advancedautosmelt.utils.ChatUtils;
import me.pulsi_.advancedautosmelt.utils.SetUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    private final AdvancedAutoSmelt plugin;
    public Commands(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {

        MessageManager messMan = new MessageManager(plugin);
        String on = messMan.on();
        String off = messMan.off();

        String autoPickup = messMan.autoPickup();
        String autoSmelt = messMan.autoSmelt();
        String inventoryAlerts = messMan.inventoryAlerts();

        if (args.length == 0) {
            s.sendMessage("");
            s.sendMessage(ChatUtils.color("&fRunning &8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l>"));
            s.sendMessage(ChatUtils.color("&fVersion: &a" + plugin.getDescription().getVersion()));
            s.sendMessage(ChatUtils.color("&fMade by Pulsi_"));
            s.sendMessage("");
        }

        if (args.length == 1) {
            switch (args[0]) {
                case "reload":
                    if (!s.hasPermission("advancedautosmelt.autopickup")) {
                        messMan.noPermission(s, "advancedautosmelt.autopickup");
                        return false;
                    }
                    plugin.reloadConfigs();
                    messMan.reloadMessage(s);
                    break;

                case "help":
                    if (!s.hasPermission("advancedautosmelt.help")) {
                        messMan.noPermission(s, "advancedautosmelt.help");
                        return false;
                    }
                    messMan.helpMessage(s);
                    break;

                case "toggle":
                    if (!s.hasPermission("advancedautosmelt.toggle")) {
                        messMan.noPermission(s, "advancedautosmelt.toggle");
                        return false;
                    }
                    messMan.availableOptions(s);
                    break;

                default:
                    messMan.unknownCommand(s);
                    break;
            }
        }

        if (args.length == 2) {
            switch (args[0]) {
                case "toggle":
                    switch (args[1]) {
                        case "autopickup":
                            if (!s.hasPermission("advancedautosmelt.toggle.autopickup")) {
                                messMan.noPermission(s, "advancedautosmelt.toggle.autopickup");
                                return false;
                            }
                            if (!(s instanceof Player)) {
                                messMan.notPlayer(s);
                                return false;
                            }
                            if (!SetUtils.AUTOPICKUP_OFF.contains(((Player) s).getUniqueId())) {
                                SetUtils.AUTOPICKUP_OFF.add(((Player) s).getUniqueId());
                                messMan.optionToggled(s, off, autoPickup);
                            } else {
                                SetUtils.AUTOPICKUP_OFF.remove(((Player) s).getUniqueId());
                                messMan.optionToggled(s, on, autoPickup);
                            }
                            break;

                        case "autosmelt":
                            if (!s.hasPermission("advancedautosmelt.toggle.autosmelt")) {
                                messMan.noPermission(s, "advancedautosmelt.toggle.autosmelt");
                                return false;
                            }
                            if (!(s instanceof Player)) {
                                messMan.notPlayer(s);
                                return false;
                            }
                            if (!SetUtils.AUTOSMELT_OFF.contains(((Player) s).getUniqueId())) {
                                SetUtils.AUTOSMELT_OFF.add(((Player) s).getUniqueId());
                                messMan.optionToggled(s, off, autoSmelt);
                            } else {
                                SetUtils.AUTOSMELT_OFF.remove(((Player) s).getUniqueId());
                                messMan.optionToggled(s, on, autoSmelt);
                            }
                            break;

                        case "inventoryalerts":
                            if (!s.hasPermission("advancedautosmelt.toggle.inventoryalerts")) {
                                messMan.noPermission(s, "advancedautosmelt.toggle.inventoryalerts");
                                return false;
                            }
                            if (!(s instanceof Player)) {
                                messMan.notPlayer(s);
                                return false;
                            }
                            if (!SetUtils.INVENTORYALERTS_OFF.contains(((Player) s).getUniqueId())) {
                                SetUtils.INVENTORYALERTS_OFF.add(((Player) s).getUniqueId());
                                messMan.optionToggled(s, off, inventoryAlerts);
                            } else {
                                SetUtils.INVENTORYALERTS_OFF.remove(((Player) s).getUniqueId());
                                messMan.optionToggled(s, on, inventoryAlerts);
                            }
                            break;

                        default:
                            messMan.unknownCommand(s);
                            break;
                    }
                    break;
            }
        }

        if (args.length == 3) {
            switch (args[0]) {
                case "toggle":
                    if (!s.hasPermission("advancedautosmelt.toggle.others")) {
                        messMan.noPermission(s, "advancedautosmelt.toggle.others");
                        return false;
                    }
                    if (Bukkit.getPlayerExact(args[2]) == null) {
                        messMan.cannotFindPlayer(s);
                        return false;
                    }
                    Player p = Bukkit.getPlayerExact(args[2]);
                    switch (args[1]) {
                        case "autopickup":
                            if (!SetUtils.AUTOPICKUP_OFF.contains(Bukkit.getPlayerExact(args[2]).getUniqueId())) {
                                SetUtils.AUTOPICKUP_OFF.add(Bukkit.getPlayerExact(args[2]).getUniqueId());
                                messMan.optionToggledOthers(s, off, autoPickup, p);
                                messMan.forceToggle(p, off, autoPickup);
                            } else {
                                SetUtils.AUTOPICKUP_OFF.remove(Bukkit.getPlayerExact(args[2]).getUniqueId());
                                messMan.optionToggledOthers(s, on, autoPickup, p);
                                messMan.forceToggle(p, on, autoPickup);
                            }
                            break;

                        case "autosmelt":
                            if (!SetUtils.AUTOSMELT_OFF.contains(Bukkit.getPlayerExact(args[2]).getUniqueId())) {
                                SetUtils.AUTOSMELT_OFF.add(Bukkit.getPlayerExact(args[2]).getUniqueId());
                                messMan.optionToggledOthers(s, off, autoSmelt, p);
                                messMan.forceToggle(p, off, autoSmelt);
                            } else {
                                SetUtils.AUTOSMELT_OFF.remove(Bukkit.getPlayerExact(args[2]).getUniqueId());
                                messMan.optionToggledOthers(s, on, autoSmelt, p);
                                messMan.forceToggle(p, on, autoSmelt);
                            }
                            break;

                        case "inventoryalerts":
                            if (!SetUtils.INVENTORYALERTS_OFF.contains(Bukkit.getPlayerExact(args[2]).getUniqueId())) {
                                SetUtils.INVENTORYALERTS_OFF.add(Bukkit.getPlayerExact(args[2]).getUniqueId());
                                messMan.optionToggledOthers(s, off, inventoryAlerts, p);
                                messMan.forceToggle(p, off, inventoryAlerts);
                            } else {
                                SetUtils.INVENTORYALERTS_OFF.remove(Bukkit.getPlayerExact(args[2]).getUniqueId());
                                messMan.optionToggledOthers(s, on, inventoryAlerts, p);
                                messMan.forceToggle(p, on, inventoryAlerts);
                            }
                            break;

                        default:
                            messMan.unknownCommand(s);
                            break;
                    }
                    break;
            }
        }

        return true;
    }
}