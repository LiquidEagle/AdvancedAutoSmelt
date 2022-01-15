package me.pulsi_.advancedautosmelt.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MapUtils {
    public static final Map<UUID, Boolean> isAutoPickupEnabled = new HashMap<>();
    public static final Map<UUID, Boolean> isAutoSmeltEnabled = new HashMap<>();
    public static final Map<UUID, Boolean> isInventoryAlerts = new HashMap<>();
}