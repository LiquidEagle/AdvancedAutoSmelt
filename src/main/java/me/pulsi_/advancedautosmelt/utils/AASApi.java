package me.pulsi_.advancedautosmelt.utils;

import me.pulsi_.advancedautosmelt.values.Values;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class AASApi {

    /**
     * Check if a player is able to pick up the specified block.
     * @param p The player.
     * @param block The block.
     * @return true if he can, false if he can't.
     */
    public static boolean canAutoPickup(Player p, Block block) {
        boolean canAutoPickup = MapUtils.isAutoPickupEnabled.get(p);
        boolean hasPermission = p.hasPermission("advancedautosmelt.autopickup");
        boolean isBlockBlacklist = Values.getConfig().getAutoPickupBlockBlacklist().contains(block.getType().toString());

        return canAutoPickup && hasPermission && !isBlockBlacklist && !Methods.isAutoPickupWorldBlacklisted(p);
    }

    /**
     * Check if a player is able to generally pick up blocks.
     * @param p The player.
     * @return true if he can, false if he can't.
     */
    public static boolean canAutoPickup(Player p) {
        boolean canAutoPickup = MapUtils.isAutoPickupEnabled.get(p);
        boolean hasPermission = p.hasPermission("advancedautosmelt.autopickup");

        return canAutoPickup && hasPermission && !Methods.isAutoPickupWorldBlacklisted(p);
    }

    /**
     * Check if a player is able to autosmelt blocks.
     * @param p The player.
     * @return true if he can, false if he can't.
     */
    public static boolean canAutoSmelt(Player p) {
        boolean canAutoSmelt = MapUtils.isAutoSmeltEnabled.get(p);
        boolean hasPermission = p.hasPermission("advancedautosmelt.autosmelt");

        return canAutoSmelt && hasPermission && !Methods.isAutoSmeltWorldBlacklisted(p);
    }

    /**
     * Check if a player is able to use the advancedautosmelt fortune.
     * @param p The player.
     * @return true if he can, false if he can't.
     */
    public static boolean canUseFortune(Player p) {
        if (!Values.getConfig().isFortuneEnabled() || Methods.isFortuneWorldBlacklisted(p)) return false;
        if (Values.getConfig().isFortuneRequirePermission())
            return p.hasPermission(Values.getConfig().getFortunePermission());
        return true;
    }

    /**
     * Returns if a player has the autopickup enabled.
     * @param p The player.
     * @return true if it is, false if it isn't.
     */
    public static boolean isAutoPickupEnabled(Player p) {
        return MapUtils.isAutoPickupEnabled.get(p);
    }

    /**
     * Returns if a player has the autosmelt enabled.
     * @param p The player.
     * @return true if it is, false if it isn't.
     */
    public static boolean isAutoSmeltEnabled(Player p) {
        return MapUtils.isAutoSmeltEnabled.get(p);
    }

    /**
     * Returns if a player has the inventoryalerts enabled.
     * @param p The player.
     * @return true if it is, false if it isn't.
     */
    public static boolean isInventoryAlertsEnabled(Player p) {
        return MapUtils.isInventoryAlerts.get(p);
    }
}