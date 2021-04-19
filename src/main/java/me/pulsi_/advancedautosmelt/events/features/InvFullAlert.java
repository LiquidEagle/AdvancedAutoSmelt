package me.pulsi_.advancedautosmelt.events.features;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.managers.DataManager;
import me.pulsi_.advancedautosmelt.managers.Translator;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InvFullAlert implements Listener {

    private final AdvancedAutoSmelt plugin;
    private final boolean useInventoryFullAlert;
    private final boolean useTitle;
    private final boolean useActionBar;
    private final boolean useMessages;
    private final boolean useSound;
    private final String title;
    private final String subTitle;
    private final String actionbarMessage;
    private final String sound;
    private final List<String> invFullMessages;
    private final int fadein;
    private final int stay;
    private final int fadeout;
    private final int volume;
    private final int pitch;
    private final int alertDelay;
    private final Set<String> noAlert = new HashSet<>();

    public InvFullAlert(AdvancedAutoSmelt plugin, DataManager dm) {
        this.plugin = plugin;
        useInventoryFullAlert = dm.isUseInvAlert();
        useTitle = dm.isUseTitle();
        useActionBar = dm.isUseActionBar();
        useMessages = dm.isUseMessages();
        useSound = dm.isUseSound();
        title = dm.getTitleTitle();
        subTitle = dm.getTitleSubTitle();
        fadein = dm.getTitleFadeIn();
        stay = dm.getTitleStay();
        fadeout = dm.getTitleFadeOut();
        actionbarMessage = dm.getActionbarMessage();
        invFullMessages = dm.getInvFullMessages();
        sound = dm.getSound();
        volume = dm.getVolume();
        pitch = dm.getPitch();
        alertDelay = dm.getAlertDelay();
    }

    @EventHandler
    public void invFullAlert(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (!useInventoryFullAlert) return;
        if (noAlert.contains(p.getName())) return;
        if (p.getInventory().firstEmpty() >= 0) return;

        //Title
        if (useTitle) {
            try {
                p.sendTitle(Translator.c(title), Translator.c(subTitle), fadein, stay, fadeout);
            } catch (NoSuchMethodError err) {
                p.sendTitle(Translator.c(title), Translator.c(subTitle));
            }
            //Title

            //ActionBar
            if (useActionBar) {
                try {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Translator.c(actionbarMessage)));
                } catch (NoSuchMethodError err) {
                    Bukkit.getConsoleSender().sendMessage(Translator.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &cSorry! But the ActionBar doesn't work in " + plugin.getServer().getVersion()));
                }
            }
            //ActionBar

            //Messages
            if (useMessages) {
                for (String messages : invFullMessages)
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages));
            }
            //Messages

            //Sound
            if (useSound) {
                try {
                    p.playSound(p.getLocation(), Sound.valueOf(sound), volume, pitch);
                } catch (Exception exc) {
                    Bukkit.getConsoleSender().sendMessage(Translator.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &cThe sound for the InventoryFull Sound is invalid!"));
                }
            }
            //Sound

            if (alertDelay != 0) {
                noAlert.add(p.getName());
                new BukkitRunnable() {
                    public void run() {
                        noAlert.remove(p.getName());
                    }
                }.runTaskLater(plugin, alertDelay);
            }
        }
    }
}