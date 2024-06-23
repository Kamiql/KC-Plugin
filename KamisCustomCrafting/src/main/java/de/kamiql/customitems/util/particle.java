package de.kamiql.customitems.util;

import de.kamiql.customitems.Main;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class particle {
    public BukkitTask particleTask;
    public final Main plugin;

    public particle(Main plugin) {
        this.plugin = plugin;
        run_particle();
    }
    public void run_particle() {
        particleTask = new BukkitRunnable() {
        @Override
        public void run() {
            for (Player player : Bukkit.getOnlinePlayers()) {
                ItemStack boots = player.getInventory().getBoots();
                if (boots != null) {
                    ItemMeta meta = boots.getItemMeta();
                    if (meta != null) {
                        PersistentDataContainer data = meta.getPersistentDataContainer();
                        NamespacedKey key = new NamespacedKey(plugin, "fly_boots");
                        if (data.has(key, PersistentDataType.STRING) && data.get(key, PersistentDataType.STRING).equals("00001")) {
                            if (!player.isOnGround() && player.isFlying()) {
                                double offsetY = -0.3;
                                player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL,
                                        player.getLocation().add(0, offsetY, 0),
                                        15, 0.3, 0.3, 0.3, 0.03);
                            }
                        }
                    }
                }
            }
        }
    }.runTaskTimer(plugin, 2L, 2L);}
}
