package me.pulsi_.advancedautosmelt.coreSystem;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.utils.AASChat;
import me.pulsi_.advancedautosmelt.utils.AASLogger;
import me.pulsi_.advancedautosmelt.utils.AASPermissions;
import me.pulsi_.advancedautosmelt.utils.AASUtils;
import me.pulsi_.advancedautosmelt.values.ConfigValues;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ExtraFeatures {

    private static final HashMap<Material, Material> inventoryIngotToBlockMap = new HashMap<>(), inventorySmelterMap = new HashMap<>();
    private static final Set<UUID> inventoryAlertsCooldown = new HashSet<>();

    public static void containerPickup(Player p, Block block) {
        BlockState state = block.getState();
        if (!AdvancedAutoSmeltDropSystem.canAutoPickup(p, block) || !(state instanceof Container)) return;

        World world = block.getWorld();
        Location loc = p.getLocation();
        Container container = ((Container) state);

        for (ItemStack item : container.getInventory().getContents()) {
            if (item == null) continue;

            Collection<ItemStack> dropsLeft = p.getInventory().addItem(item).values();
            if (!dropsLeft.isEmpty()) for (ItemStack dropLeft : dropsLeft) world.dropItem(loc, dropLeft);
        }

        container.getInventory().clear();
    }

    public static void inventoryIngotToBlock(Player p) {
        if (!ConfigValues.isIngotToBlockEnabled() || !p.hasPermission(AASPermissions.ingotToBlock) || p.getInventory().firstEmpty() < 0) return;

        Inventory inv = p.getInventory();
        for (ItemStack item : inv.getContents()) {
            if (item == null) continue;

            Material ingotMaterial = item.getType();
            if (!inventoryIngotToBlockMap.containsKey(ingotMaterial)) continue;

            int amount = item.getAmount();
            if (amount < 9) continue;

            int blockAmount = amount / 9, newAmount = amount - (9 * blockAmount);
            inv.addItem(new ItemStack(inventoryIngotToBlockMap.get(ingotMaterial), blockAmount));
            item.setAmount(newAmount);
        }
    }

    public static void inventoryAlerts(Player p) {
        if (!ConfigValues.isInventoryAlertsEnabled() || p.getInventory().firstEmpty() > -1) return;

        UUID uuid = p.getUniqueId();
        if (inventoryAlertsCooldown.contains(uuid)) return;

        if (ConfigValues.isInventoryAlertsTitleEnabled()) AASUtils.sendTitle(ConfigValues.getInventoryAlertsTitle(), p);

        if (ConfigValues.isInventoryAlertsActionbarEnabled()) AASUtils.sendActionBar(p, ConfigValues.getInventoryAlertsActionbar());

        if (ConfigValues.isInventoryAlertsMessageEnabled())
            for (String messages : ConfigValues.getInventoryAlertsMessage())
                p.sendMessage(AASChat.color(messages));

        if (ConfigValues.isInventoryAlertsSoundEnabled()) AASUtils.playSound(p, ConfigValues.getInventoryAlertsSound());

        if (ConfigValues.getInventoryAlertsDelay() != 0) {
            inventoryAlertsCooldown.add(uuid);
            Bukkit.getScheduler().runTaskLater(AdvancedAutoSmelt.INSTANCE(), () -> inventoryAlertsCooldown.remove(uuid), ConfigValues.getInventoryAlertsDelay());
        }
    }

    public static void smeltInventory(Player p) {
        if (!ConfigValues.isInventorySmelterEnabled() || !p.hasPermission(AASPermissions.inventorySmelter)) return;

        Inventory inv = p.getInventory();
        for (ItemStack item : inv.getContents()) {
            if (item == null) continue;

            Material prevMaterial = item.getType();
            if (inventorySmelterMap.containsKey(prevMaterial)) item.setType(inventorySmelterMap.get(prevMaterial));
        }
    }

    public static void loadExtraFeatures() {
        inventoryIngotToBlockMap.clear();
        for (String line : ConfigValues.getIngotToBlockList()) {
            if (!line.contains(";")) {
                AASLogger.warn("The ingot to block list format must contains a \";\" separator and 2 materials, skipping line... (Wrong line: " + line + ")");
                continue;
            }

            String[] split = line.split(";");
            String ingotMaterialName = split[0], blockMaterialName = split[1];

            Material ingotMaterial, blockMaterial;
            try {
                ingotMaterial = Material.valueOf(ingotMaterialName);
                blockMaterial = Material.valueOf(blockMaterialName);
            } catch (IllegalArgumentException e) {
                AASLogger.warn("The ingot to block list contains an invalid material, skipping line... (Wrong line: " + line + ")");
                continue;
            }

            inventoryIngotToBlockMap.put(ingotMaterial, blockMaterial);
        }

        inventorySmelterMap.clear();
        for (String line : ConfigValues.getInventorySmelterList()) {
            if (!line.contains(";")) {
                AASLogger.warn("The inventory smelter list format must contains a \";\" separator and 2 materials, skipping line... (Wrong line: " + line + ")");
                continue;
            }

            String[] split = line.split(";");
            String prevMaterialName = split[0], newMaterialName = split[1];

            Material prevMaterial, newMaterial;
            try {
                prevMaterial = Material.valueOf(prevMaterialName);
                newMaterial = Material.valueOf(newMaterialName);
            } catch (IllegalArgumentException e) {
                AASLogger.warn("The inventory smelter list contains an invalid material, skipping line... (Wrong line: " + line + ")");
                continue;
            }

            inventorySmelterMap.put(prevMaterial, newMaterial);
        }
    }
}