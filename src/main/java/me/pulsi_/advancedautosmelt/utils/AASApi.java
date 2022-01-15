package me.pulsi_.advancedautosmelt.utils;

import me.pulsi_.advancedautosmelt.values.Values;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class AASApi {

    public static boolean canAutoPickup(Player p, Block block) {
        boolean canAutoPickup = MapUtils.isAutoPickupEnabled.get(p.getUniqueId());
        boolean hasPermission = p.hasPermission("advancedautosmelt.autopickup");
        boolean isBlockBlacklist = Values.getConfig().getAutoPickupBlockBlacklist().contains(block.getType().toString());

        return canAutoPickup && hasPermission && !isBlockBlacklist && !Methods.isAutoPickupWorldBlacklisted(p);
    }

    public static boolean canAutoSmelt(Player p) {
        boolean canAutoSmelt = MapUtils.isAutoSmeltEnabled.get(p.getUniqueId());
        boolean hasPermission = p.hasPermission("advancedautosmelt.autosmelt");

        return canAutoSmelt && hasPermission && !Methods.isAutoSmeltWorldBlacklisted(p);
    }

    public static boolean canUseFortune(Player p) {
        if (!Values.getConfig().isFortuneEnabled() || Methods.isFortuneWorldBlacklisted(p)) return false;
        if (Values.getConfig().isFortuneRequirePermission())
            return p.hasPermission(Values.getConfig().getFortunePermission());
        return true;
    }
}