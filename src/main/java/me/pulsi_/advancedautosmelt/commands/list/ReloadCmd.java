package me.pulsi_.advancedautosmelt.commands.list;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.AASCommand;
import me.pulsi_.advancedautosmelt.utils.AASMessages;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadCmd extends AASCommand {

    public ReloadCmd(String... aliases) {
        super(aliases);
    }

    @Override
    public boolean needConfirm() {
        return false;
    }

    @Override
    public boolean hasCooldown() {
        return false;
    }

    @Override
    public int getCooldown() {
        return 0;
    }

    @Override
    public int getConfirmCooldown() {
        return 0;
    }

    @Override
    public String getUsage() {
        return "/aas reload";
    }

    @Override
    public String getDescription() {
        return "Reload the plugin to apply all changes from the configs.";
    }

    @Override
    public String getConfirmMessage() {
        return null;
    }

    @Override
    public String getCooldownMessage() {
        return null;
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public boolean skipUsageWarn() {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender s, String[] args) {
        if (confirm(s)) return false;

        long start = System.currentTimeMillis();
        AASMessages.send(s, "%prefix% &7Initializing reload task...");

        if (AdvancedAutoSmelt.INSTANCE().getDataManager().reloadPlugin()) AASMessages.send(s, "%prefix% &2Successfully reloaded the plugin! &8(&bTook " + (System.currentTimeMillis() - start) + "ms&8)");
        else AASMessages.send(s, "%prefix% &cSomething went wrong while reloading the plugin, check the console for more info.");

        return true;
    }

    @Override
    public List<String> tabCompletion(CommandSender s, String[] args) {
        return null;
    }
}