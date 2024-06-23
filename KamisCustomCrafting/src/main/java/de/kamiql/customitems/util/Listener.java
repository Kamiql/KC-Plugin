package de.kamiql.customitems.util;

import de.kamiql.customitems.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public record Listener(Main plugin) implements org.bukkit.event.Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ItemStack[] armor = player.getInventory().getArmorContents();
        boolean shouldDisableFlight = true;

        for (ItemStack piece : armor) {
            if (piece != null) {
                ItemMeta meta = piece.getItemMeta();
                if (meta != null) {
                    PersistentDataContainer data = meta.getPersistentDataContainer();
                    NamespacedKey key = new NamespacedKey(plugin, "fly_boots");
                    if (data.has(key, PersistentDataType.STRING) && data.get(key, PersistentDataType.STRING).equals("00001")) {
                        shouldDisableFlight = false;
                        break;
                    }
                }
            }
        }

        if (shouldDisableFlight) {
            disableFlight(player);
        } else {
            player.setAllowFlight(true);
        }
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        disableFlight(event.getPlayer());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Ensure flight is disabled when players join (in case of reloads, etc.)
        disableFlight(event.getPlayer());
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        ItemStack result = event.getResult();
        if (result != null && result.hasItemMeta()) {
            PersistentDataContainer data = result.getItemMeta().getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(plugin, "lockEntchant");
            if (data.has(key, PersistentDataType.STRING)) {
                List<Player> viewersToNotify = new ArrayList<>();

                ItemStack resultItem = new ItemStack(Material.BARRIER);
                ItemMeta resultItemM = resultItem.getItemMeta();
                resultItem.setAmount(1);
                resultItemM.setDisplayName("§c§lFehler§7:");
                List<String> lore = new ArrayList<>();
                lore.add("§7Du kannst dieses Item nicht verzaubern!");
                resultItemM.setLore(lore);
                resultItemM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                resultItemM.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                resultItemM.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                resultItemM.addItemFlags(ItemFlag.HIDE_DESTROYS);
                resultItemM.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                resultItemM.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                createNBT("AMBUSS_ERROR", "1", resultItemM);
                resultItem.setItemMeta(resultItemM);

                event.setResult(resultItem);
                event.getViewers().forEach(viewer -> {
                    if (viewer instanceof Player) {
                        viewersToNotify.add((Player) viewer);
                    }
                });

                // Notify viewers
                viewersToNotify.forEach(viewer ->
                        viewer.sendMessage(Main.PREFIX + "§cDieses Item kann nicht bearbeitet werden§7!")
                );
                viewersToNotify.forEach(viewer ->
                        viewer.playSound(viewer.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f)
                );
            }
        }
    }

    @EventHandler
    public void onPrepareItemEnchant(EnchantItemEvent event) {
        ItemStack item = event.getItem();
        if (item != null && item.hasItemMeta()) {
            PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(plugin, "lockEntchant");
            Player player = event.getEnchanter();
            if (data.has(key, PersistentDataType.STRING)) {
                event.setCancelled(true);
                event.getEnchanter().closeInventory();
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null) {
            Player player = (Player) event.getClickedInventory().getHolder();
            ItemMeta meta = event.getCurrentItem().getItemMeta();
            if (event.getView().getType().equals(InventoryType.ANVIL)) {
                if (meta != null) {
                    PersistentDataContainer data = meta.getPersistentDataContainer();
                    NamespacedKey key = new NamespacedKey(plugin, "AMBUSS_ERROR");
                    if (data.has(key, PersistentDataType.STRING) && data.get(key, PersistentDataType.STRING).equals("1")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item != null) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                // Hier sicher weiterarbeiten, da item und meta nicht null sind
                PersistentDataContainer data = meta.getPersistentDataContainer();
                NamespacedKey key = new NamespacedKey(plugin, "rocket");
                if (data.has(key, PersistentDataType.STRING) && data.get(key, PersistentDataType.STRING).equals("00002")) {
                    if (item.getType() == Material.FIREWORK_ROCKET) {
                        if (player.getCooldown(item.getType()) != 0) {
                            event.setCancelled(true);
                        } else {
                            if (player.isGliding()) {
                                event.setCancelled(true);
                                player.launchProjectile(org.bukkit.entity.Firework.class);
                                player.setCooldown(item.getType(), 40);
                                meta.setCustomModelData(2);
                                item.setItemMeta(meta);
                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                    ItemMeta updatedMeta = item.getItemMeta();
                                    if (updatedMeta != null) {
                                        updatedMeta.setCustomModelData(1);
                                        item.setItemMeta(updatedMeta);
                                    }
                                }, 40L);
                            } else {
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }



    private void disableFlight(Player player) {
        if (player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
            player.setAllowFlight(false);
            player.setFlying(false);
        }
    }

    public void createNBT(String key_name,String DataString, ItemMeta item) {
        NamespacedKey key = new NamespacedKey(plugin, key_name);
        PersistentDataContainer data2 = item.getPersistentDataContainer();
        data2.set(key, PersistentDataType.STRING, DataString);
    }
}
