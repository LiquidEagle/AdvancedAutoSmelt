package me.pulsi_.advancedautosmelt.gui;

import me.pulsi_.advancedautosmelt.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class GuiHolder implements InventoryHolder {

    private final Inventory gui;

    public GuiHolder(int lines, String title) {
        this.gui = Bukkit.createInventory(this, guiLines(lines), ChatUtils.color(title));
    }

    public void openEnchanter(Player p) {
        GuiHolder gui = getEnchanterHolder();
        gui.buildEnchanter();
        p.openInventory(gui.getInventory());
    }

    private void buildEnchanter() {
        ItemStack reloadButton = GuiItemCreator.createItemStack(
                Material.LEVER,
                "&2&lRELOAD",
                1, true, (short) 0,
                "&7Click here to reload the plugin.",
                "&7This will apply all changes",
                "&7saved in the config files.");

        ItemStack guiFiller;
        try {
            guiFiller = GuiItemCreator.createItemStack(
                    Material.LIGHT_GRAY_STAINED_GLASS_PANE,
                    "&r",
                    1, false, (short) 0);
        } catch (NoSuchFieldError e) {
            guiFiller = GuiItemCreator.createItemStack(
                    Material.valueOf("STAINED_GLASS_PANE"),
                    "&r",
                    1, false, (short) 8);
        }

        gui.setItem(13, reloadButton);

        for (int i = 0; i < guiLines(3); i++)
            if (gui.getItem(i) == null)
                gui.setItem(i, guiFiller);
    }

    private int guiLines(int number) {
        switch (number) {
            case 1:
                return 9;
            case 2:
                return 18;
            default:
                return 27;
            case 4:
                return 36;
            case 5:
                return 45;
            case 6:
                return 54;
        }
    }

    public static GuiHolder getEnchanterHolder() {
        return new GuiHolder(3, ChatUtils.color("&a&lA&9&lA&c&lS &7Control Panel."));
    }

    @Override
    public Inventory getInventory() {
        return gui;
    }
}