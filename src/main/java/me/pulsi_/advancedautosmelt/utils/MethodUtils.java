package me.pulsi_.advancedautosmelt.utils;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MethodUtils {

    public static void playSound(String path, Player p) {

        String soundType = path.split(" ")[0];
        int volume = Integer.parseInt(path.split(" ")[1]);
        int pitch = Integer.parseInt(path.split(" ")[2]);

        try {
            p.playSound(p.getLocation(), Sound.valueOf(soundType), volume, pitch);
        } catch (IllegalArgumentException exception) {
            ChatUtils.consoleMessage("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &cInvalid SoundType at: &f" + path);
        }
    }

    public static void sendTitle(String path, Player p) {
        try {
            String title = path.split(",")[0];
            String subTitle = path.split(",")[1];
            p.sendTitle(ChatUtils.color(title), ChatUtils.color(subTitle));
        } catch (NullPointerException | IllegalArgumentException e) {
            ChatUtils.consoleMessage("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &cAn error occurred while sending a title at: &f" + path);
        }
    }

    public static boolean isWorldBlacklist(Player p) {
        boolean isBlacklist = false;
        for (String disabledWorlds : JavaPlugin.getPlugin(AdvancedAutoSmelt.class).config().getStringList("Disabled-Worlds"))
            if (disabledWorlds.contains(p.getWorld().getName())) isBlacklist = true;
        return isBlacklist;
    }

    public static boolean isCreativeDisabled(Player p) {
        boolean isCreativeDisabled = false;
        if (JavaPlugin.getPlugin(AdvancedAutoSmelt.class).config().getBoolean("Disable-Creative-Mode"))
            if (p.getGameMode() == GameMode.CREATIVE) isCreativeDisabled = true;
        return isCreativeDisabled;
    }

    public static void removeDrops(BlockBreakEvent e) {
        if (JavaPlugin.getPlugin(AdvancedAutoSmelt.class).config().getBoolean("Enable-Legacy-Support")) {
            e.getBlock().setType(Material.AIR);
        } else {
            e.setDropItems(false);
        }
    }

    public static void dropsItems(Player p, ItemStack i) {
        if (!JavaPlugin.getPlugin(AdvancedAutoSmelt.class).config().getBoolean("AutoPickup.Process-Pickup-Event")) {
            try {
                if (!p.getInventory().addItem(i).isEmpty())
                    if (JavaPlugin.getPlugin(AdvancedAutoSmelt.class).config().getBoolean("AutoPickup.Inv-Full-Drop-Items"))
                        p.getWorld().dropItem(p.getLocation(), i);
            } catch (IllegalArgumentException ignored) {}
        } else {
            p.getWorld().dropItem(p.getLocation(), i).setPickupDelay(0);
        }
    }

    public static boolean canPickup(Player p) {
        return JavaPlugin.getPlugin(AdvancedAutoSmelt.class).config().getBoolean("AutoPickup.Enabled")
                & p.hasPermission("advancedautosmelt.autopickup") & !SetUtils.AUTOPICKUP_OFF.contains(p.getUniqueId());
    }

    public static boolean canSmelt(Player p) {
        return JavaPlugin.getPlugin(AdvancedAutoSmelt.class).config().getBoolean("AutoSmelt.Enabled")
                & p.hasPermission("advancedautosmelt.autosmelt") & !SetUtils.AUTOSMELT_OFF.contains(p.getUniqueId());
    }

    public static void sendActionBar(Player player, String message) {
        String v = Bukkit.getServer().getClass().getPackage().getName();
        v = v.substring(v.lastIndexOf(".") + 1);
        try {
            if (v.startsWith("1_13") || v.startsWith("1_14")) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(ChatUtils.color(message)).create());
            } else if (!(v.equalsIgnoreCase("v1_8_R1") || v.contains("v1_7_"))) {
                Class<?> c1 = Class.forName("org.bukkit.craftbukkit." + v + ".entity.CraftPlayer");
                Class<?> c2 = Class.forName("net.minecraft.server." + v + ".ChatComponentText");
                Class<?> c3 = Class.forName("net.minecraft.server." + v + ".IChatBaseComponent");
                Class<?> c4 = Class.forName("net.minecraft.server." + v + ".PacketPlayOutChat");
                Class<?> c5 = Class.forName("net.minecraft.server." + v + ".Packet");

                Object p = c1.cast(player);
                Object o = c2.getConstructor(new Class<?>[]{String.class}).newInstance(ChatUtils.color(message));
                Object poc = c4.getConstructor(new Class<?>[]{c3, byte.class}).newInstance(o, (byte) 2);
                Object handle = c1.getDeclaredMethod("getHandle").invoke(p);
                Object playerConnection = handle.getClass().getDeclaredField("playerConnection").get(handle);

                playerConnection.getClass().getDeclaredMethod("sendPacket", c5).invoke(playerConnection, poc);
            } else {
                Class<?> c1 = Class.forName("org.bukkit.craftbukkit." + v + ".entity.CraftPlayer");
                Class<?> c2 = Class.forName("net.minecraft.server." + v + ".ChatSerializer");
                Class<?> c3 = Class.forName("net.minecraft.server." + v + ".IChatBaseComponent");
                Class<?> c4 = Class.forName("net.minecraft.server." + v + ".PacketPlayOutChat");
                Class<?> c5 = Class.forName("net.minecraft.server." + v + ".Packet");

                Object p = c1.cast(player);
                Object cbc = c3.cast(c2.getDeclaredMethod("a", String.class).invoke(c2, "{\"text\": \"" + ChatUtils.color(message) + "\"}"));
                Object poc = c4.getConstructor(new Class<?>[]{c3, byte.class}).newInstance(cbc, (byte) 2);
                Object handle = c1.getDeclaredMethod("getHandle").invoke(p);
                Object playerConnection = handle.getClass().getDeclaredField("playerConnection").get(handle);

                playerConnection.getClass().getDeclaredMethod("sendPacket", c5).invoke(playerConnection, poc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}