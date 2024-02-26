package me.pulsi_.advancedautosmelt.commands;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.utils.AASMessages;
import me.pulsi_.advancedautosmelt.utils.AASUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AASCommand {

    private final String identifier, permission;

    private final String[] aliases;

    public AASCommand(String... aliases) {
        this.identifier = aliases[0];
        this.permission = "advancedautosmelt." + identifier;

        this.aliases = new String[aliases.length - 1];
        System.arraycopy(aliases, 1, this.aliases, 0, aliases.length - 1);
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getPermission() {
        return permission;
    }

    public String[] getAliases() {
        return aliases;
    }

    private final HashMap<String, Long> cooldownMap = new HashMap<>();

    private final List<String> confirm = new ArrayList<>();

    public abstract boolean needConfirm();

    public abstract boolean hasCooldown();

    public abstract int getCooldown();

    public abstract int getConfirmCooldown();

    public abstract String getUsage();

    public abstract String getDescription();

    public abstract String getConfirmMessage();

    public abstract String getCooldownMessage();

    public boolean confirm(CommandSender s) {
        if (needConfirm()) {
            if (!confirm.contains(s.getName())) {
                Bukkit.getScheduler().runTaskLater(AdvancedAutoSmelt.INSTANCE(), () -> confirm.remove(s.getName()), getConfirmCooldown() * 20L);
                if (getConfirmMessage() != null && !getConfirmMessage().isEmpty()) sendConfirm(s);
                confirm.add(s.getName());
                return true;
            }
            confirm.remove(s.getName());
        }
        return false;
    }

    public void execute(CommandSender s, String[] args) {
        if (!AASUtils.hasPermission(s, getPermission()) || (playerOnly() && !AASUtils.isPlayer(s))) return;

        if (!skipUsageWarn() && args.length == 1) {
            if (getUsage() != null && !getUsage().isEmpty()) sendUsage(s);
            return;
        }

        if (!onCommand(s, args)) return;

        if (hasCooldown() && getCooldown() > 0 && !(s instanceof ConsoleCommandSender)) {
            if (cooldownMap.containsKey(s.getName()) && cooldownMap.get(s.getName()) > System.currentTimeMillis()) {
                if (getCooldownMessage() != null && !getCooldownMessage().isEmpty())
                    AASMessages.send(s, getCooldownMessage(), true);
                return;
            }
            cooldownMap.put(s.getName(), System.currentTimeMillis() + (getCooldown() * 1000L));
            Bukkit.getScheduler().runTaskLater(AdvancedAutoSmelt.INSTANCE(), () -> cooldownMap.remove(s.getName()), getCooldown() * 20L);
        }
    }

    public abstract boolean playerOnly();

    public abstract boolean skipUsageWarn();

    public abstract boolean onCommand(CommandSender s, String[] args);

    public abstract List<String> tabCompletion(CommandSender s, String[] args);

    public String getUsageWithColors(String usage) {
        return usage.replace("[", "&8[&b")
                .replace("]", "&8]")
                .replace("<", "&8<&3")
                .replace(">", "&8>");
    }

    private void sendUsage(CommandSender s) {
        AASMessages.send(s, "%prefix% &7Command usage: &3" + getUsageWithColors(getUsage()), true);
    }

    private void sendConfirm(CommandSender s) {
        String preConfirm = "%prefix% &c", confirm = getConfirmMessage();
        AASMessages.send(s, preConfirm + confirm, true);
    }
}