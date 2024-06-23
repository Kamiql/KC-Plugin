package de.kamiql.customitems.util;

import de.kamiql.customitems.Main;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public record Listener2(Main plugin) implements Listener {
    private static HashSet<UUID> lock = new HashSet<>();
    private static final HashMap<UUID, Long> abilityActiveUntil = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

            if (item != null && item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null) {

                NamespacedKey key = new NamespacedKey(plugin, "endstonesword");
                NamespacedKey key2 = new NamespacedKey(plugin, "MagmaSword");
                NamespacedKey key3 = new NamespacedKey(plugin, "EnderSword");
                NamespacedKey key4 = new NamespacedKey(plugin, "3x3");
                NamespacedKey key5 = new NamespacedKey(plugin, "multiTool");

                if (meta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
                    String dataString = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
                    if ("slot1".equals(dataString)) {
                        if (!player.hasCooldown(item.getType())) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 400, 1));
                            player.playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 2, 2);
                            player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL,
                                    player.getLocation().add(0, +1, 0),
                                    100, 1, 1, 1, 0.5);
                            player.setCooldown(item.getType(), 3000);
                        }
                    }
                } else if (meta.getPersistentDataContainer().has(key2, PersistentDataType.STRING)) {
                    if (!player.hasCooldown(item.getType())) {
                        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                            player.launchProjectile(org.bukkit.entity.Fireball.class);
                            player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 2, 2);
                            player.getWorld().spawnParticle(Particle.CRIT_MAGIC,
                                    player.getLocation().add(0, +1, 0),
                                    100, 1, 1, 1, 0.5);
                            player.setCooldown(item.getType(), 1000);
                        }
                    }
                } else if (meta.getPersistentDataContainer().has(key3, PersistentDataType.STRING)) {
                    if (!player.hasCooldown(item.getType())) {
                        player.launchProjectile(org.bukkit.entity.EnderPearl.class);
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_PEARL_THROW, 2, 2);
                        player.getWorld().spawnParticle(Particle.CRIT_MAGIC,
                                player.getLocation().add(0, +1, 0),
                                100, 1, 1, 1, 0.5);
                        player.setCooldown(item.getType(), 250);
                    }
                } else if (meta.getPersistentDataContainer().has(key4, PersistentDataType.STRING)) {
                    if (!player.hasCooldown(item.getType())) {
                        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                            UUID playerId = player.getUniqueId();
                            abilityActiveUntil.put(playerId, System.currentTimeMillis() + 20000);
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 2, 2);
                            player.getWorld().spawnParticle(Particle.END_ROD,
                                    player.getLocation().add(0, 1, 0),
                                    100, 1, 1, 1, 0.5);
                            player.setCooldown(event.getItem().getType(), 10000);
                        }
                    } else {
                        player.sendMessage(Main.PREFIX + "Du musst noch §a" + convertTicksToTime(player.getCooldown(item.getType())) + " §7warten!");
                    }

                } else if (meta.getPersistentDataContainer().has(key5, PersistentDataType.STRING)) {
                    if(event.getAction() == Action.LEFT_CLICK_BLOCK) {

                        Block clickedBlock = event.getClickedBlock();
                        ItemStack usedItem = player.getInventory().getItemInMainHand();

                        if (clickedBlock != null && usedItem != null) {

                            if (Tag.MINEABLE_AXE.isTagged(clickedBlock.getType()) && !Tag.ITEMS_AXES.isTagged(usedItem.getType())) {

                                usedItem.setType(Material.NETHERITE_AXE);
                                event.setCancelled(false);
                                return;

                            } else if (Tag.MINEABLE_PICKAXE.isTagged(clickedBlock.getType()) && !Tag.ITEMS_PICKAXES.isTagged(usedItem.getType())) {

                                usedItem.setType(Material.NETHERITE_PICKAXE);
                                event.setCancelled(false);
                                return;

                            } else if (Tag.MINEABLE_SHOVEL.isTagged(clickedBlock.getType()) && !Tag.ITEMS_SHOVELS.isTagged(usedItem.getType())) {

                                usedItem.setType(Material.NETHERITE_SHOVEL);
                                event.setCancelled(false);
                                return;

                            } else if (Tag.MINEABLE_HOE.isTagged(clickedBlock.getType()) && !Tag.ITEMS_SHOVELS.isTagged(usedItem.getType())) {

                                usedItem.setType(Material.NETHERITE_HOE);
                                event.setCancelled(false);
                                return;

                            }
                        }
                        
                        clickedBlock.breakNaturally(usedItem);

                    }
                }
            }
        }
    }


    public static String convertTicksToTime(int ticks) {
        int seconds = ticks / 20;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (abilityActiveUntil.containsKey(playerId) && System.currentTimeMillis() < abilityActiveUntil.get(playerId)) {
            Block baseBlock = event.getBlock();

            BlockBreak3x3Logic blockBreak3x3Logic = new BlockBreak3x3Logic(player, baseBlock);
            blockBreak3x3Logic.run();

            event.setCancelled(true);
        }
    }
}
