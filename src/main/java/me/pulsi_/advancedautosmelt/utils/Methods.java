package me.pulsi_.advancedautosmelt.utils;

import me.pulsi_.advancedautosmelt.values.Values;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Methods {

    public static void giveCustomExp(Player p, Block block) {
        if (!Values.getConfig().isIsCustomExpEnabled()) return;
        for (String line : Values.getConfig().getCustomExpList()) {
            if (!line.contains(";")) continue;

            String blockName = line.split(";")[0];
            if (!blockName.equals(block.getType().toString())) continue;

            int amount;
            try {
                amount = Integer.parseInt(line.split(";")[1]);
            } catch (NumberFormatException ex) {
                AASLogger.error("Invalid number for the custom exp amount! " + ex.getMessage());
                return;
            }

            if (AASApi.canAutoPickup(p)) {
                p.giveExp(amount);
            } else {
                ExperienceOrb orb = block.getWorld().spawn(block.getLocation(), ExperienceOrb.class);
                orb.setExperience(amount);
            }
        }
    }

    public static void giveDrops(Player p, Block block, ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return;

        ItemStack playerItem = p.getItemInHand();
        if (AASApi.canUseFortune(p) && playerItem.hasItemMeta() && playerItem.getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_BLOCKS)) {

            int level = playerItem.getItemMeta().getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS);
            int random = new Random().nextInt(level) + 1;

            if (Values.getConfig().isFortuneWhitelistEnabled()) {
                if (Values.getConfig().isFortuneWhitelistIsBlacklist()) {
                    if (!Values.getConfig().getFortuneWhitelist().contains(block.getType().toString()))
                        item.setAmount(random);
                } else {
                    if (Values.getConfig().getFortuneWhitelist().contains(block.getType().toString()))
                        item.setAmount(random);
                }
            } else {
                item.setAmount(random);
            }
        }

        if (AASApi.canAutoPickup(p) && !Methods.isAutoPickupBlockBlacklisted(block)) {
            if (Values.getConfig().isProcessPlayerPickupEvent()) {
                p.getWorld().dropItem(p.getLocation(), item).setPickupDelay(0);
                return;
            }
            if (!p.getInventory().addItem(item).isEmpty() && Values.getConfig().isDropItemsOnInventoryFull())
                p.getWorld().dropItem(p.getLocation(), item);
        } else {
            p.getWorld().dropItem(block.getLocation(), item);
        }
    }

    public static void removeDrops(BlockBreakEvent e) {
        if (Values.getConfig().isEnableLegacySupport()) {
            e.getBlock().setType(Material.AIR);
        } else {
            e.setDropItems(false);
        }
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
            p.sendTitle(ChatUtils.color(title), ChatUtils.color(subTitle));
        } else {
            p.sendTitle(ChatUtils.color(path), ChatUtils.color("&f"));
        }
    }

    public static void sendActionBar(Player player, String message) {
        String v = Bukkit.getServer().getClass().getPackage().getName();
        v = v.substring(v.lastIndexOf(".") + 1);

        if (v.contains("1_13") || v.contains("1_14") || v.contains("1_15") || v.contains("1_16") || v.contains("1_17") || v.contains("1_18")) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(ChatUtils.color(message)).create());
            return;
        }

        try {
            if (v.contains("v1_7_") || v.equals("v1_8_R1")) {
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
                return;
            }

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
        } catch (Exception e) {
            AASLogger.error("Please report this error to the developer: " + e.getMessage());
        }
    }

    public static boolean blockDoesDamage(Block block) {
        String blockName = block.getType().toString();
        return !blockName.contains("LEAVES") && !blockName.contains("GRASS");
    }

    public static boolean isAutoPickupWorldBlacklisted(Player p) {
        return Values.getConfig().getAutoPickupWorldBlacklist().contains(p.getWorld().getName());
    }

    public static boolean isAutoSmeltWorldBlacklisted(Player p) {
        return Values.getConfig().getAutoSmeltWorldBlacklist().contains(p.getWorld().getName());
    }

    public static boolean isFortuneWorldBlacklisted(Player p) {
        return Values.getConfig().getFortuneWorldBlacklist().contains(p.getWorld().getName());
    }

    public static boolean isAutoPickupBlockBlacklisted(Block block) {
        return Values.getConfig().getAutoPickupBlockBlacklist().contains(block.getType().toString());
    }

    public static void destroyItem(Player p) {
        p.setItemInHand(null);
        try {
            p.getWorld().playSound(p.getLocation(), Sound.valueOf("ENTITY_ITEM_BREAK"), 5, 1);
        } catch (IllegalArgumentException e) {
            p.getWorld().playSound(p.getLocation(), Sound.valueOf("ITEM_BREAK"), 5, 1);
        }
    }
}