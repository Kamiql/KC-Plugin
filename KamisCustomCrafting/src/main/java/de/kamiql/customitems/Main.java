package de.kamiql.customitems;

import de.kamiql.customitems.commands.CustomItems;
import de.kamiql.customitems.commands.ReloadCommand;
import de.kamiql.customitems.util.Listener;
import de.kamiql.customitems.util.Listener2;
import de.kamiql.customitems.util.particle;
import de.kamiql.customitems.util.rainbow;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            AttributeInstance maxHealthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (maxHealthAttribute != null) {
                for (org.bukkit.attribute.AttributeModifier modifier : maxHealthAttribute.getModifiers()) {
                    maxHealthAttribute.removeModifier(modifier);
                }
            }
            maxHealthAttribute.setBaseValue(20.0);
            player.setHealth(20.0);
            player.setCooldown(Material.NETHERITE_PICKAXE, 1);
        }

        getServer().getPluginManager().registerEvents(new Listener(this), this);
        getServer().getPluginManager().registerEvents(new Listener2(this), this);
        getCommand("reload").setExecutor(new ReloadCommand(this.getServer(), this));
        getCommand("item").setExecutor(new CustomItems(this));
        new particle(this);
        new rainbow(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static final String PREFIX = "§7[§e§lK§b§lC§r§7] >> ";

}
