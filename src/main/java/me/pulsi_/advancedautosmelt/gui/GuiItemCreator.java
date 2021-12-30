package me.pulsi_.advancedautosmelt.gui;

import me.pulsi_.advancedautosmelt.utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiItemCreator {

    public static ItemStack createItemStack(Material material, String displayName, int amount, boolean glowing, short damage, String... lore) {
        ItemStack item = new ItemStack(material, amount, damage);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatUtils.color(displayName));

        List<String> list = new ArrayList<>();
        for (String line : lore) list.add(ChatUtils.color(line));
        meta.setLore(list);

        if (glowing) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        item.setItemMeta(meta);

        return item;
    }
}