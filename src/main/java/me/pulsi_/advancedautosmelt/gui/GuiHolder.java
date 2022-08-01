package me.pulsi_.advancedautosmelt.gui;

import me.pulsi_.advancedautosmelt.utils.AASChat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class GuiHolder implements InventoryHolder {

    private final Inventory gui;

    public GuiHolder(String title) {
        this.gui = Bukkit.createInventory(this, InventoryType.HOPPER, AASChat.color(title));
    }

    public void openGui(Player p) {
        GuiHolder gui = getGuiHolder();
        gui.buildGui();
        p.openInventory(gui.getInventory());
    }

    private void buildGui() {
        ItemStack reloadButton = GuiItemCreator.createItemStack(Material.LEVER, "&2&lRELOAD", 1, false, (short) 0,
                "&7Click here to reload the plugin.");

        Material material;
        try {
            material = Material.LIGHT_GRAY_STAINED_GLASS_PANE;
        } catch (NoSuchFieldError e) {
            material = Material.valueOf("STAINED_GLASS_PANE");
        }
        ItemStack guiFiller = GuiItemCreator.createItemStack(material, "&r", 1, false, (short) 0);
        gui.setItem(2, reloadButton);

        for (int i = 0; i < 5; i++) if (gui.getItem(i) == null) gui.setItem(i, guiFiller);
    }

    public static GuiHolder getGuiHolder() {
        return new GuiHolder(AASChat.color("&a&lA&9&lA&c&lS &aControl Panel"));
    }

    @Override
    public Inventory getInventory() {
        return gui;
    }
}