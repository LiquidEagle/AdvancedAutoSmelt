package me.pulsi_.advancedautosmelt.managers;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.utils.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageManager {

    private final AdvancedAutoSmelt plugin;
    public MessageManager(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    public void notPlayer(CommandSender s) {
        if (plugin.messages().getString("Not-Player") == null) return;
        s.sendMessage(addPref(plugin.messages().getString("Not-Player")));
    }

    public String off() {
        if (plugin.messages().getString("Off") == null) return ChatUtils.color("&cOff");
        return ChatUtils.color(plugin.messages().getString("Off"));
    }

    public String autoPickup() {
        if (plugin.messages().getString("AutoPickup") == null) return ChatUtils.color("&aAutoPickup");
        return ChatUtils.color(plugin.messages().getString("AutoPickup"));
    }

    public String autoSmelt() {
        if (plugin.messages().getString("AutoSmelt") == null) return ChatUtils.color("&aAutoSmelt");
        return ChatUtils.color(plugin.messages().getString("AutoSmelt"));
    }

    public String inventoryAlerts() {
        if (plugin.messages().getString("InventoryAlerts") == null) return ChatUtils.color("&aInventoryAlerts");
        return ChatUtils.color(plugin.messages().getString("InventoryAlerts"));
    }

    public String on() {
        if (plugin.messages().getString("On") == null) return ChatUtils.color("&2On");
        return ChatUtils.color(plugin.messages().getString("On"));
    }

    public void forceToggle(Player p, String status, String option) {
        if (plugin.messages().getString("Force-Toggled") == null) return;
        p.sendMessage(addPref(plugin.messages().getString("Force-Toggled")
                .replace("%status%", status).replace("%option%", option)));
    }

    public void optionToggledOthers(CommandSender s, String status, String option, Player p) {
        if (plugin.messages().getString("Option-Toggled-Others") == null) return;
        s.sendMessage(addPref(plugin.messages().getString("Option-Toggled-Others")
                .replace("%status%", status).replace("%option%", option).replace("%player%", p.getName())));
    }

    public void optionToggled(CommandSender s, String status, String option) {
        if (plugin.messages().getString("Option-Toggled") == null) return;
        s.sendMessage(addPref(plugin.messages().getString("Option-Toggled")
                .replace("%status%", status).replace("%option%", option)));
    }

    public void availableOptions(CommandSender s) {
        if (plugin.messages().getString("Available-Options") == null) return;
        s.sendMessage(addPref(plugin.messages().getString("Available-Options")));
    }

    public void cannotFindPlayer(CommandSender s) {
        if (plugin.messages().getString("Cannot-Find-Player") == null) return;
        s.sendMessage(addPref(plugin.messages().getString("Cannot-Find-Player")));
    }

    public void unknownCommand(CommandSender s) {
        if (plugin.messages().getString("Unknown-Command") == null) return;
        s.sendMessage(addPref(plugin.messages().getString("Unknown-Command")));
    }

    public void helpMessage(CommandSender s) {
        for (String message : plugin.messages().getStringList("Help-Message"))
            s.sendMessage(ChatUtils.color(message));
    }

    public void noPermission(CommandSender s, String missingPermission) {
        if (plugin.messages().getString("No-Permission") == null) return;
        s.sendMessage(addPref(plugin.messages().getString("No-Permission").replace("%permission%", missingPermission)));
    }

    public void reloadMessage(CommandSender s) {
        if (plugin.messages().getString("Reload-Message") == null) return;
        s.sendMessage(addPref(plugin.messages().getString("Reload-Message")));
    }

    private String addPref(String message) {
        if (plugin.config().getString("Prefix") == null) return ChatUtils.color(message);
        return ChatUtils.color(plugin.config().getString("Prefix") + message);
    }
}