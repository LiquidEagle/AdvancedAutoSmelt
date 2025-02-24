package me.pulsi_.advancedautosmelt.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AASUtils {

    /**
     * Check if the specified player has the inventory full.
     *
     * @param p The player to check.
     * @return true if full, false otherwise.
     */
    public static boolean isInventoryFull(Player p) {
        return p.getInventory().firstEmpty() == -1;
    }

    public static void playSound(Player p, String sound) {
        String soundType = sound.split(" ")[0];
        int volume = Integer.parseInt(sound.split(" ")[1]);
        int pitch = Integer.parseInt(sound.split(" ")[2]);

        try {
            p.playSound(p.getLocation(), Sound.valueOf(soundType), volume, pitch);
        } catch (IllegalArgumentException e) {
            AASLogger.warn("Unknown sound type: &f\"" + e.getMessage() + "\"");
        }
    }

    public static void sendTitle(String path, Player p) {
        if (path.contains(",")) {
            String title = path.split(",")[0];
            String subTitle = path.split(",")[1];
            p.sendTitle(AASChat.color(title), AASChat.color(subTitle));
        } else {
            p.sendTitle(AASChat.color(path), AASChat.color("&f"));
        }
    }

    public static void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(AASChat.color(message)).create());
    }

    public static boolean isPlayer(CommandSender s) {
        if (s instanceof Player) return true;
        AASMessages.send(s, "Not-Player");
        return false;
    }

    public static boolean hasPermission(CommandSender s, String permission) {
        if (s.hasPermission(permission)) return true;
        AASMessages.send(s, "No-Permission", "%permission%$" + permission);
        return false;
    }
}