package me.pulsi_.advancedautosmelt.coreSystem;

import me.pulsi_.advancedautosmelt.players.PlayerRegistry;
import me.pulsi_.advancedautosmelt.utils.AASLogger;
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
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public abstract class AdvancedAutoSmeltDropSystem {

    private static DropGetter dropGetter;
    private static DropSmelter dropSmelter;
    private static FortuneApplier fortuneApplier;
    private static ExpGiver expGiver;
    private static DropGiver dropGiver;

    public static void giveDrops(Player p, Block block, ItemStack itemUsed, int fortuneLevel) {
        Collection<ItemStack> drops = dropGetter.getDrops(itemUsed, block);
        if (drops.isEmpty()) return;

        dropSmelter.smeltDrops(p, drops);
        fortuneApplier.applyFortune(p, block, drops, fortuneLevel);

        expGiver.giveExp(p, block);
        dropGiver.giveDrops(p, block, drops);
    }

    public static void loadDropSystem() {
        DropGetter.loadDropGetter();
        DropSmelter.loadDropSmelter();
        FortuneApplier.loadFortuneApplier();
        ExpGiver.loadExpGiver();
        DropGiver.loadDropGiver();
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
        boolean isBlockBlacklist = ConfigValues.getAutoPickupBlockBlacklist().contains(block.getType().toString());
        boolean isWorldBlacklist = ConfigValues.getAutoPickupWorldBlacklist().contains(p.getWorld().getName());

        return canAutoPickup && !isBlockBlacklist && !isWorldBlacklist;
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

    private abstract static class ExpGiver {
        private static final HashMap<Material, AdvancedAutoSmeltDropSystem.Pair> expMap = new HashMap<>();

        public abstract void giveExp(Player p, Block block);

        public static void loadExpGiver() {
            expMap.clear();
            for (String line : ConfigValues.getCustomExpList()) {
                if (!line.contains(";")) {
                    AASLogger.warn("The custom exp list format must contains a \";\" separator, 1 material and 1 number, skipping line... (Wrong line: " + line + ")");
                    continue;
                }

                String[] split = line.split(";");
                String blockMaterialName = split[0], expAmountString = split[1];

                boolean needAutoSmelt = false;
                if (expAmountString.contains("[NEED_AUTOSMELT]")) {
                    expAmountString = expAmountString.replace("[NEED_AUTOSMELT]", "");
                    needAutoSmelt = true;
                }

                Material blockMaterial;
                int expAmount;
                try {
                    blockMaterial = Material.valueOf(blockMaterialName);
                    expAmount = Integer.parseInt(expAmountString);
                } catch (NumberFormatException e) {
                    AASLogger.warn("The custom exp list contains an invalid number, skipping line... (Wrong line: " + line + ")");
                    continue;
                } catch (IllegalArgumentException e) {
                    AASLogger.warn("The custom exp list contains an invalid material, skipping line... (Wrong line: " + line + ")");
                    continue;
                }

                AdvancedAutoSmeltDropSystem.Pair pair = new AdvancedAutoSmeltDropSystem.Pair();
                pair.k = expAmount;
                pair.v = needAutoSmelt;
                expMap.put(blockMaterial, pair);
            }

            if (ConfigValues.isIsCustomExpEnabled()) {
                if (ConfigValues.isAutoPickupEnabled()) {
                    expGiver = new ExpGiver() {
                        @Override
                        public void giveExp(Player p, Block block) {
                            if (!canMineCustomExp(p)) return;

                            Material dropMaterial = block.getType();
                            if (!expMap.containsKey(dropMaterial)) return;

                            int exp = (int) expMap.get(dropMaterial).k;
                            boolean needAutoSmelt = (boolean) expMap.get(dropMaterial).v;
                            if (needAutoSmelt && !canAutoSmelt(p)) return;

                            if (canAutoPickup(p, block)) p.giveExp(exp);
                            else block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(exp);
                        }
                    };
                } else {
                    expGiver = new ExpGiver() {
                        @Override
                        public void giveExp(Player p, Block block) {
                            if (!canMineCustomExp(p)) return;

                            Material dropMaterial = block.getType();
                            if (!expMap.containsKey(dropMaterial)) return;

                            int exp = (int) expMap.get(dropMaterial).k;
                            boolean needAutoSmelt = (boolean) expMap.get(dropMaterial).v;
                            if (!needAutoSmelt || canAutoSmelt(p))
                                block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(exp);
                        }
                    };
                }
            } else {
                expGiver = new ExpGiver() {
                    @Override
                    public void giveExp(Player p, Block block) {
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

    private static class Pair {
        Object k, v;
    }
}