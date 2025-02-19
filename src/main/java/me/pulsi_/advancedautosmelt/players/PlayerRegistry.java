package me.pulsi_.advancedautosmelt.players;

import me.pulsi_.advancedautosmelt.values.ConfigValues;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerRegistry {

    private static final HashMap<UUID, AASPlayer> players = new HashMap<>();

    public static AASPlayer getPlayer(Player p) {
        UUID uuid = p.getUniqueId();
        if (!players.containsKey(uuid)) registerPlayer(p);

        return players.get(uuid);
    }

    public static void registerPlayer(Player p) {
        UUID uuid = p.getUniqueId();
        if (players.containsKey(uuid)) return;

        AASPlayer player = new AASPlayer();

        if (ConfigValues.isAutoPickupEnabled() && ConfigValues.isAutoPickupEnabledOnJoin()) {
            if (!ConfigValues.isAutoPickupEnabledOnJoinNeedPerm()) player.setAutoPickupEnabled(true);
            else if (p.hasPermission(ConfigValues.getAutoPickupJoinPermission())) player.setAutoPickupEnabled(true);
        }

        if (ConfigValues.isAutoSmeltEnabled() && ConfigValues.isAutoSmeltEnabledOnJoin()) {
            if (!ConfigValues.isAutoSmeltEnabledOnJoinNeedPerm()) player.setAutoSmeltEnabled(true);
            else if (p.hasPermission(ConfigValues.getAutoSmeltJoinPermission())) player.setAutoSmeltEnabled(true);
        }

        if (ConfigValues.isAutoSellEnabled() && ConfigValues.isAutoSellEnabledOnJoin()) {
            if (!ConfigValues.isAutoSellEnabledOnJoinNeedPerm()) player.setAutoSellEnabled(true);
            else if (p.hasPermission(ConfigValues.getAutoSellJoinPermission())) player.setAutoSellEnabled(true);
        }

        players.put(uuid, player);
    }

    public static void unregisterPlayer(Player p) {
        players.remove(p.getUniqueId());
    }
}