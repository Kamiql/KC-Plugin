package de.kamiql.customitems.util;

import de.kamiql.customitems.Main;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class rainbow {

    private final Main plugin;
    private int tickCounter = 0;
    private final Material[] leatherArmorTypes = {
            Material.LEATHER_BOOTS,
            Material.LEATHER_LEGGINGS,
            Material.LEATHER_CHESTPLATE,
            Material.LEATHER_HELMET
    };

    public rainbow(Main plugin) {
        this.plugin = plugin;
        new BukkitRunnable() {
            @Override
            public void run() {
                changeArmorColor();
            }
        }.runTaskTimer(plugin, 0L, 2L);
    }

    private void changeArmorColor() {
        float hue = (tickCounter % 200) / 200.0f;
        int rgb = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        Color color = Color.fromRGB(red, green, blue);

        for (Player player : Bukkit.getOnlinePlayers()) {
            ItemStack mainHandItem = player.getInventory().getItemInMainHand();

            // Skip if player has one of the items in hand
            if (mainHandItem != null && isLeatherArmor(mainHandItem.getType())) {
                continue;
            }

            updateArmorColor(player.getInventory().getContents(), color);
            updateArmorColor(player.getInventory().getArmorContents(), color);
        }

        for (Player viewer : Bukkit.getServer().getOnlinePlayers()) {
            Inventory inventory = viewer.getOpenInventory().getTopInventory();
            updateArmorColor(inventory.getContents(), color);
        }

        tickCounter++;
    }

    private void updateArmorColor(ItemStack[] items, Color color) {
        for (ItemStack item : items) {
            if (item != null && isLeatherArmor(item.getType())) {
                LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                if (meta != null) {
                    PersistentDataContainer data = meta.getPersistentDataContainer();
                    NamespacedKey key = new NamespacedKey(plugin, "rainbow");
                    if (data.has(key, PersistentDataType.STRING)) {
                        meta.setColor(color);
                        item.setItemMeta(meta);
                    }
                }
            }
        }
    }

    private boolean isLeatherArmor(Material material) {
        for (Material leatherArmor : leatherArmorTypes) {
            if (material == leatherArmor) {
                return true;
            }
        }
        return false;
    }
}
