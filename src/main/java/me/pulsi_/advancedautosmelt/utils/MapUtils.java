package me.pulsi_.advancedautosmelt.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {

    public static final Map<Player, Boolean> isAutoPickupEnabled = new HashMap<>();
    public static final Map<Player, Boolean> isAutoSmeltEnabled = new HashMap<>();
    public static final Map<Player, Boolean> isInventoryAlerts = new HashMap<>();
}