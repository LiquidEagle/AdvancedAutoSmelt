package me.pulsi_.advancedautosmelt.managers;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Consumer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker implements Listener {

    private AdvancedAutoSmelt plugin;
    private int resourceId;
    public UpdateChecker(AdvancedAutoSmelt plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
                 Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                plugin.getServer().getConsoleSender().sendMessage("");
                plugin.getServer().getConsoleSender().sendMessage(Translator.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &cI can't look for updates: &f" + exception.getMessage()));
                plugin.getServer().getConsoleSender().sendMessage("");
            }
        });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!plugin.getConfig().getBoolean("update_checker")) return;
        Player p = e.getPlayer();
        if (p.isOp() || p.hasPermission("advancedautosmelt.notify")) {
            new UpdateChecker(plugin, 90587).getVersion(version -> {
                if (plugin.getDescription().getVersion().equalsIgnoreCase(version)) {

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.sendMessage("");
                            p.sendMessage(Translator.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &2There are no updates available!"));
                            p.sendMessage("");
                        }
                    }.runTaskLater(plugin, 60);
                } else {

                    TextComponent update = new TextComponent(Translator.c("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &2There is a new update available!"));
                    update.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/advancedautosmelt-smelt-ores-autopickup-items-and-exp.90587/"));
                    update.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click here to download it!").color(ChatColor.LIGHT_PURPLE).create()));

                    new BukkitRunnable() {
                        @Override
                        public void run() {

                            p.sendMessage("");
                            p.spigot().sendMessage(update);
                            p.sendMessage("");

                        }
                    }.runTaskLater(plugin, 60);
                }
            });
        }
    }
}