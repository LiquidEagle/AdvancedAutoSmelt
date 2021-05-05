package me.pulsi_.advancedautosmelt.items;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SmelterPickaxe {

    private AdvancedAutoSmelt plugin;
    public SmelterPickaxe(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    public ItemStack smelterPickaxe() {

        FileConfiguration config = plugin.getConfiguration();

        String materialType = config.getString("Custom-Pickaxe.Pickaxe.Material-Type");
        String displayName = config.getString("Custom-Pickaxe.Pickaxe.Display-Name");
        List<String> lore = new ArrayList<>();

        ItemStack pickaxe = new ItemStack(Material.valueOf(materialType));

        ItemMeta pickaxeMeta = pickaxe.getItemMeta();

        pickaxeMeta.setDisplayName(ChatUtils.c(displayName));

        if (config.getBoolean("Custom-Pickaxe.Pickaxe.Use-Lore")) {
            for (String configLore : config.getStringList("Custom-Pickaxe.Pickaxe.Lore")) {
                lore.add(ChatUtils.c(configLore));
            }
            pickaxeMeta.setLore(lore);
        }

        if (config.getBoolean("Custom-Pickaxe.Pickaxe.Glowing")) {
            pickaxeMeta.addEnchant(Enchantment.DAMAGE_ALL, 0, true);
            pickaxeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        pickaxe.setItemMeta(pickaxeMeta);
        return pickaxe;
    }
}