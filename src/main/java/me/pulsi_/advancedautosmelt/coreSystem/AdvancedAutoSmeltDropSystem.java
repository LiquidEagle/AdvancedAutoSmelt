package me.pulsi_.advancedautosmelt.coreSystem;

import me.pulsi_.advancedautosmelt.players.AASPlayer;
import me.pulsi_.advancedautosmelt.players.PlayerRegistry;
import me.pulsi_.advancedautosmelt.utils.AASLogger;
import me.pulsi_.advancedautosmelt.utils.AASPermissions;
import me.pulsi_.advancedautosmelt.utils.AASUtils;
import me.pulsi_.advancedautosmelt.values.ConfigValues;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

/**
 * All the different methods are divided to load the best case
 * for each situation, doing the minimum necessary checks and
 * highly improving the performance.
 */
public abstract class AdvancedAutoSmeltDropSystem {

    private static DropGetter dropGetter;
    private static DropSmelter dropSmelter;
    private static FortuneApplier fortuneApplier;
    private static DropGiver dropGiver;

    /**
     * Give the drops the player, smelt, multiply and give exp, and process all the features that this plugin has.
     *
     * @param p            The player.
     * @param block        The block where to get all the drops.
     * @param itemUsed     The item used to break the block.
     * @param fortuneLevel The level of fortune that the item has.
     */
    public static void giveDrops(Player p, Block block, ItemStack itemUsed, int fortuneLevel) {
        Collection<ItemStack> drops = dropGetter.getDrops(itemUsed, block);
        if (drops.isEmpty()) return;

        dropSmelter.smeltDrops(p, drops);
        fortuneApplier.applyFortune(p, block, drops, fortuneLevel);

        if (ConfigValues.isAutoSellEnabled()) {
            if (ConfigValues.isAutoSellInstantSell()) AutoSell.sellItems(p, drops);
            else if (ConfigValues.isAutoSellOnInventoryFull() && AASUtils.isInventoryFull(p))
                AutoSell.sellInventory(p);
        }
        if (ConfigValues.isIsCustomExpEnabled()) giveExp(p, block);
        dropGiver.giveDrops(p, block, drops);
    }

    /**
     * Get the exp that you should receive from the specified block.
     *
     * @param p     The player to check if he can obtain custom exp.
     * @param block The block to check.
     * @return The amount of exp received.
     */
    public static int getBlockExp(Player p, Block block) {
        if (!ConfigValues.isIsCustomExpEnabled()) return 0;

        Material type = block.getType();
        if (!CoreLoader.expMap.containsKey(type)) return 0;

        CoreLoader.Pair values = CoreLoader.expMap.get(type);

        boolean needAutoSmelt = (boolean) values.v;
        if (needAutoSmelt && !AdvancedAutoSmeltDropSystem.canAutoSmelt(p)) return 0;

        return (int) values.k; // Exp amount
    }

    /**
     * Give the custom exp of the specified block to the player, spawning an orb on the ground if he can't auto pickup.
     *
     * @param p     The player that will receive the exp.
     * @param block The block where to get the exp.
     */
    public static void giveExp(Player p, Block block) {
        int exp = getBlockExp(p, block);

        if (AdvancedAutoSmeltDropSystem.canAutoPickup(p, block)) p.giveExp(exp);
        else block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(exp);
    }

    /**
     * Load the drop system and all its modules.
     */
    public static void loadDropSystem() {
        DropGetter.loadDropGetter();
        DropSmelter.loadDropSmelter();
        FortuneApplier.loadFortuneApplier();
        DropGiver.loadDropGiver();
    }

    /**
     * Check if player has permission to use the autopickup feature.
     * @param p     Player to check.
     * @return true if you have permission, false otherwise.
     * Will also disable the autopickup if the player had it enabled but loses permission.
     */
    public static boolean checkPickupPermission(Player p) {
        if(p == null) return false;

        AASPlayer player = PlayerRegistry.getPlayer(p);
        if (!p.hasPermission(AASPermissions.autoPickupTogglePermission)) {
            // ~ Check if player had AutoPickup enabled before losing permission & disable if true.
            if (player.isAutoPickupEnabled()) {player.setAutoPickupEnabled(false);}
            return false;
        } else return p.hasPermission(AASPermissions.autoPickupTogglePermission);
    }

    /**
     * Check if the player can autopickup that block. (Depending on the player permissions, block location or blacklist)
     *
     * @param p     The player.
     * @param block The block.
     * @return true if you can autopickup that block, false otherwise.
     */
    public static boolean canAutoPickup(Player p, Block block) {
        boolean canAutoPickup = PlayerRegistry.getPlayer(p).isAutoPickupEnabled();
        boolean hasPermission = checkPickupPermission(p);
        boolean isBlockBlacklist = ConfigValues.getAutoPickupBlockBlacklist().contains(block.getType().toString());
        boolean isWorldBlacklist = ConfigValues.getAutoPickupWorldBlacklist().contains(p.getWorld().getName());

        return hasPermission && canAutoPickup && !isBlockBlacklist && !isWorldBlacklist;
    }

    /**
     * Check if the player can autosmelt.
     *
     * @param p The player.
     * @return true if can autosmelt, false otherwise.
     */
    public static boolean canAutoSmelt(Player p) {
        boolean canAutoPickup = PlayerRegistry.getPlayer(p).isAutoSmeltEnabled();
        boolean isWorldBlacklist = ConfigValues.getAutoSmeltWorldBlacklist().contains(p.getWorld().getName());

        return canAutoPickup && !isWorldBlacklist;
    }

    /**
     * Check if the player can use fortune on that block. (Depending on the player permissions, block location or blacklist)
     *
     * @param p     The player.
     * @param block The block.
     * @return true if you can use fortune on that block, false otherwise.
     */
    public static boolean canUseFortune(Player p, Block block) {
        if (ConfigValues.getFortuneWorldBlacklist().contains(p.getWorld().getName())) return false;

        String type = block.getType().toString();
        if (ConfigValues.isFortuneWhitelistIsBlacklist()) {
            if (ConfigValues.getFortuneWhitelist().contains(type)) return false;
        } else {
            if (!ConfigValues.getFortuneWhitelist().contains(type)) return false;
        }

        return !ConfigValues.isFortuneRequirePermission() || p.hasPermission(ConfigValues.getFortunePermission());
    }

    /**
     * Check if the player can mine custom exp.
     *
     * @param p The player.
     * @return true if can mine custom exp, false otherwise.
     */
    public static boolean canMineCustomExp(Player p) {
        return !ConfigValues.getCustomExpWorldBlacklist().contains(p.getWorld().getName());
    }

    private abstract static class DropGetter {

        public abstract Collection<ItemStack> getDrops(ItemStack itemUsed, Block block);

        public static void loadDropGetter() {
            if (ConfigValues.isCheckForItemInHand()) {
                dropGetter = new DropGetter() {
                    @Override
                    public Collection<ItemStack> getDrops(ItemStack itemUsed, Block block) {
                        return block.getDrops(itemUsed);
                    }
                };
            } else {
                dropGetter = new DropGetter() {
                    @Override
                    public Collection<ItemStack> getDrops(ItemStack itemUsed, Block block) {
                        return block.getDrops();
                    }
                };
            }
        }
    }

    private abstract static class DropSmelter {
        private static final HashMap<Material, Material> smeltMap = new HashMap<>();

        public abstract void smeltDrops(Player p, Collection<ItemStack> drops);

        public static void loadDropSmelter() {
            smeltMap.clear();
            for (String line : ConfigValues.getAutoSmeltList()) {
                if (!line.contains(";")) {
                    AASLogger.warn("The autosmelt list format must contains a \";\" separator and 2 materials, skipping line... (Wrong line: " + line + ")");
                    continue;
                }

                String[] split = line.split(";");
                String prevMaterialName = split[0], nextMaterialName = split[1];

                Material prevMaterial, nextMaterial;
                try {
                    prevMaterial = Material.valueOf(prevMaterialName);
                    nextMaterial = Material.valueOf(nextMaterialName);
                } catch (IllegalArgumentException e) {
                    AASLogger.warn("The autosmelt list contains an invalid material, skipping line... (Wrong line: " + line + ")");
                    continue;
                }

                smeltMap.put(prevMaterial, nextMaterial);
            }

            if (ConfigValues.isAutoSmeltEnabled()) {
                dropSmelter = new DropSmelter() {
                    @Override
                    public void smeltDrops(Player p, Collection<ItemStack> drops) {
                        if (canAutoSmelt(p))
                            for (ItemStack drop : drops) {
                                Material dropMaterial = drop.getType();
                                if (smeltMap.containsKey(dropMaterial)) drop.setType(smeltMap.get(dropMaterial));
                            }
                    }
                };
            } else {
                dropSmelter = new DropSmelter() {
                    @Override
                    public void smeltDrops(Player p, Collection<ItemStack> drops) {
                    }
                };
            }
        }
    }

    private abstract static class FortuneApplier {

        public abstract void applyFortune(Player p, Block block, Collection<ItemStack> drops, int fortuneLevel);

        public static void loadFortuneApplier() {
            if (ConfigValues.isFortuneEnabled()) {
                Random rand = new Random();
                fortuneApplier = new FortuneApplier() {
                    @Override
                    public void applyFortune(Player p, Block block, Collection<ItemStack> drops, int fortuneLevel) {
                        if (canUseFortune(p, block) && fortuneLevel > 0)
                            for (ItemStack drop : drops)
                                drop.setAmount(drop.getAmount() + (rand.nextInt(fortuneLevel) + 1));
                    }
                };
            } else {
                fortuneApplier = new FortuneApplier() {
                    @Override
                    public void applyFortune(Player p, Block block, Collection<ItemStack> drops, int fortuneLevel) {
                    }
                };
            }
        }
    }

    private abstract static class DropGiver {
        public abstract void giveDrops(Player p, Block block, Collection<ItemStack> drops);

        public static void loadDropGiver() {
            if (ConfigValues.isAutoPickupEnabled()) {
                if (ConfigValues.isProcessPlayerPickupEvent()) {
                    if (ConfigValues.isDropItemsOnInventoryFull()) {
                        dropGiver = new DropGiver() {
                            @Override
                            public void giveDrops(Player p, Block block, Collection<ItemStack> drops) {
                                World world = block.getWorld();
                                if (!canAutoPickup(p, block)) {
                                    Location location = block.getLocation();
                                    for (ItemStack drop : drops) world.dropItemNaturally(location, drop);
                                } else {
                                    Location pLoc = p.getLocation();
                                    for (ItemStack drop : drops) {
                                        Collection<ItemStack> dropsLeft = p.getInventory().addItem(drop).values();

                                        // Process EntityPickupItemEvent
                                        Item item = world.dropItem(pLoc, drop);
                                        item.setPickupDelay(9999);
                                        Bukkit.getServer().getPluginManager().callEvent(new EntityPickupItemEvent(p, item, 2));
                                        item.remove();

                                        if (dropsLeft.isEmpty()) continue;

                                        Location loc = p.getLocation();
                                        for (ItemStack dropLeft : dropsLeft) world.dropItem(loc, dropLeft);
                                    }
                                }
                            }
                        };
                    } else {
                        dropGiver = new DropGiver() {
                            @Override
                            public void giveDrops(Player p, Block block, Collection<ItemStack> drops) {
                                World world = block.getWorld();

                                if (!canAutoPickup(p, block)) {
                                    Location location = block.getLocation();
                                    for (ItemStack drop : drops) world.dropItemNaturally(location, drop);
                                } else {
                                    Location pLoc = p.getLocation();
                                    for (ItemStack drop : drops) {

                                        // Process EntityPickupItemEvent
                                        Item item = world.dropItem(pLoc, drop);
                                        item.setPickupDelay(9999);
                                        Bukkit.getServer().getPluginManager().callEvent(new EntityPickupItemEvent(p, item, 2));
                                        item.remove();

                                        p.getInventory().addItem(drop);
                                    }
                                }
                            }
                        };
                    }
                } else {
                    if (ConfigValues.isDropItemsOnInventoryFull()) {
                        dropGiver = new DropGiver() {
                            @Override
                            public void giveDrops(Player p, Block block, Collection<ItemStack> drops) {
                                World world = block.getWorld();
                                if (canAutoPickup(p, block)) {
                                    for (ItemStack drop : drops) {
                                        Collection<ItemStack> dropsLeft = p.getInventory().addItem(drop).values();
                                        if (dropsLeft.isEmpty()) continue;

                                        Location loc = p.getLocation();
                                        for (ItemStack dropLeft : dropsLeft) world.dropItem(loc, dropLeft);
                                    }
                                } else {
                                    Location location = block.getLocation();
                                    for (ItemStack drop : drops) world.dropItem(location, drop);
                                }
                            }
                        };
                    } else {
                        dropGiver = new DropGiver() {
                            @Override
                            public void giveDrops(Player p, Block block, Collection<ItemStack> drops) {
                                if (canAutoPickup(p, block))
                                    for (ItemStack drop : drops) p.getInventory().addItem(drop);
                                else {
                                    World world = block.getWorld();
                                    Location location = block.getLocation();
                                    for (ItemStack drop : drops) world.dropItem(location, drop);
                                }
                            }
                        };
                    }
                }
            } else {
                dropGiver = new DropGiver() {
                    @Override
                    public void giveDrops(Player p, Block block, Collection<ItemStack> drops) {
                        World world = block.getWorld();
                        Location location = block.getLocation();
                        for (ItemStack drop : drops) world.dropItemNaturally(location, drop);
                    }
                };
            }
        }
    }
}