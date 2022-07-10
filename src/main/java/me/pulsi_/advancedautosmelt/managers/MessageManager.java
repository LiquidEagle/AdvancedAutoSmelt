package me.pulsi_.advancedautosmelt.managers;

import me.pulsi_.advancedautosmelt.utils.AASChat;
import me.pulsi_.advancedautosmelt.values.Values;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageManager {

    public static void noPermission(CommandSender s) {
        String mess = Values.getMessages().getNoPermission();
        if (mess != null) s.sendMessage(addPrefix(mess));
    }

    public static void pluginReloaded(CommandSender s) {
        String mess = Values.getMessages().getPluginReloaded();
        if (mess != null) s.sendMessage(addPrefix(mess));
    }

    public static void autoPickupActivated(Player p) {
        String mess = Values.getMessages().getAutoPickupActivated();
        if (mess != null) p.sendMessage(addPrefix(mess));
    }

    public static void autoPickupDisabled(Player p) {
        String mess = Values.getMessages().getAutoPickupDisabled();
        if (mess != null) p.sendMessage(addPrefix(mess));
    }

    public static void autoPickupDeactivated(Player p) {
        String mess = Values.getMessages().getAutoPickupDeactivated();
        if (mess != null) p.sendMessage(addPrefix(mess));
    }

    public static void autoSmeltActivated(Player p) {
        String mess = Values.getMessages().getAutoSmeltActivated();
        if (mess != null) p.sendMessage(addPrefix(mess));
    }

    public static void autoSmeltDisabled(Player p) {
        String mess = Values.getMessages().getAutoSmeltDisabled();
        if (mess != null) p.sendMessage(addPrefix(mess));
    }

    public static void autoSmeltDeactivated(Player p) {
        String mess = Values.getMessages().getAutoSmeltDeactivated();
        if (mess != null) p.sendMessage(addPrefix(mess));
    }

    public static void inventoryAlertsActivated(Player p) {
        String mess = Values.getMessages().getInventoryAlertsActivated();
        if (mess != null) p.sendMessage(addPrefix(mess));
    }

    public static void inventoryAlertsDisabled(Player p) {
        String mess = Values.getMessages().getInventoryAlertsDisabled();
        if (mess != null) p.sendMessage(addPrefix(mess));
    }

    public static void inventoryAlertsDeactivated(Player p) {
        String mess = Values.getMessages().getInventoryAlertsDeactivated();
        if (mess != null) p.sendMessage(addPrefix(mess));
    }

    public static void notPlayer(CommandSender s) {
        String mess = Values.getMessages().getNotPlayer();
        if (mess != null) s.sendMessage(addPrefix(mess));
    }

    private static String addPrefix(String message) {
        String prefix = Values.getConfig().getPrefix();
        if (prefix == null) return AASChat.color(message);
        return AASChat.color(prefix + message);
    }
}