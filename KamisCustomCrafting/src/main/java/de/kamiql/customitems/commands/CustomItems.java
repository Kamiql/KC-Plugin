package de.kamiql.customitems.commands;

import de.kamiql.customitems.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Banner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class CustomItems implements CommandExecutor {

    public final Main plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }
        if (!sender.hasPermission("KC.commands.custom_items")) {
            sender.sendMessage(Main.PREFIX + "Du hast nicht genügend Berechtigungen, um diesen Befehl zu verwenden!");
            return true;
        }

        String item_key = args[0];
        ItemStack item_result = getitem(item_key.toString());
        System.out.println(item_key);

        if (args.length == 1 && !args[0].equalsIgnoreCase("list")) {
            if (item_result == null) {
                sender.sendMessage(Main.PREFIX + "§cUngültiger Key! §7Gib einen gültigen key an. §7(§a§o/item list§r§7)");
            } else {
                player.getInventory().addItem(item_result);
                player.sendMessage(Main.PREFIX + "§7Du hast das Item " + item_result.getItemMeta().getDisplayName() + " §7erhalten!");
                return true;
            }
        } else if (args.length == 1 && item_key.equalsIgnoreCase("list")) {
            sender.sendMessage(Main.PREFIX + "§f§lCustom Item list:");
            sender.sendMessage(Main.PREFIX + "§7-------------------");
            sender.sendMessage(Main.PREFIX + "§a00001 §7= " + getitem("00001").getItemMeta().getDisplayName());
            sender.sendMessage(Main.PREFIX + "§a00002 §7= " + getitem("00002").getItemMeta().getDisplayName());
            sender.sendMessage(Main.PREFIX + "§a00003 §7= " + getitem("00003").getItemMeta().getDisplayName());
            sender.sendMessage(Main.PREFIX + "§a00004 §7= " + getitem("00004").getItemMeta().getDisplayName());
            sender.sendMessage(Main.PREFIX + "§a00005 §7= " + getitem("00005").getItemMeta().getDisplayName());
            sender.sendMessage(Main.PREFIX + "§a00006 §7= " + getitem("00006").getItemMeta().getDisplayName());
            sender.sendMessage(Main.PREFIX + "§a00007 §7= " + getitem("00007").getItemMeta().getDisplayName());
            sender.sendMessage(Main.PREFIX + "§a00008 §7= " + getitem("00008").getItemMeta().getDisplayName());
            sender.sendMessage(Main.PREFIX + "§a00009 §7= " + getitem("00009").getItemMeta().getDisplayName());
            sender.sendMessage(Main.PREFIX + "§a00010 §7= " + getitem("00010").getItemMeta().getDisplayName());
            sender.sendMessage(Main.PREFIX + "§a00011 §7= " + getitem("00011").getItemMeta().getDisplayName());
            sender.sendMessage(Main.PREFIX + "§a00012 §7= " + getitem("00012").getItemMeta().getDisplayName());
            sender.sendMessage(Main.PREFIX + "§a00013 §7= " + getitem("00013").getItemMeta().getDisplayName());

            return true;
        }
        return true;
    }

    public CustomItems(Main plugin) {
        this.plugin = plugin;
    }
    public ItemStack getitem(String item) {
        ItemStack result_item = null;

        if (item.equals("00001")) {
            ItemStack flyboots = new ItemStack(Material.LEATHER_BOOTS);
            LeatherArmorMeta flybootsMeta = (LeatherArmorMeta) flyboots.getItemMeta();
            // Setting custom name
            flybootsMeta.setDisplayName("§f§lEngel Boots");

            // Setting custom lore
            List<String> lore = new ArrayList<>();
            lore.add("§7Während du die Boots an hast, ");
            lore.add("§7erhälst du die Kraft zum §a§ofliegen.");
            lore.add("§7»»————-　★　————-««");
            lore.add("§8Standort: §fCloudville");
            lore.add("§8Extra: §fKeinen §r§bFallschaden");
            lore.add("§7»»————-　★　————-««");
            lore.add("§cVerzauberungs Sperre");
            lore.add("§7»»————-　★　————-««");

            lore.add("§e★★★★★");

            flybootsMeta.setLore(lore);

            // Adding enchantment
            flybootsMeta.addEnchant(Enchantment.PROTECTION_FALL, 255, true);
            flybootsMeta.setColor(Color.fromRGB(255, 255, 255));

            // Hiding unwanted tooltip information (if necessary)
            flybootsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            flybootsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            flybootsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            flybootsMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            flybootsMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            flybootsMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            flybootsMeta.addItemFlags(ItemFlag.HIDE_DYE);
            flybootsMeta.setCustomModelData(1);

            // Adding custom NBT data
            createNBT("fly_boots", "00001", flybootsMeta);
            createNBT("lockEntchant", "001", flybootsMeta);

            // Finalizing the meta
            flyboots.setItemMeta(flybootsMeta);
            flyboots.setAmount(1);

            result_item = flyboots;
        } else if (item.equals("00002")) {
            ItemStack rocket = new ItemStack(Material.FIREWORK_ROCKET);
            FireworkMeta rocketMeta = (FireworkMeta) rocket.getItemMeta();

            rocketMeta.setDisplayName("§c§lInfinity Rocket");
            List<String> lore = new ArrayList<>();
            lore.add("§7Diese Rakete hält §aEWIG§7!");
            lore.add("§7»»————-　★　————-««");
            lore.add("§8Standort: §fCloudville");
            lore.add("§7»»————-　★　————-««");
            lore.add("§e★★★");
            rocketMeta.setLore(lore);

            rocketMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            rocketMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            rocketMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            rocketMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            rocketMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            rocketMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            rocketMeta.setCustomModelData(1);

            createNBT("rocket", "00002", rocketMeta);

            rocket.setItemMeta(rocketMeta);
            rocket.setAmount(1);

            result_item = rocket;
        } else if (item.equals("00003")) {
            ItemStack rainbow_armor = new ItemStack(Material.LEATHER_HELMET);
            LeatherArmorMeta rainbow_armor_meta = (LeatherArmorMeta) rainbow_armor.getItemMeta();

            rainbow_armor_meta.setDisplayName("§c§lR§6§la§e§li§a§ln§3§lb§9§lo§5§lw §6§lH§e§le§a§ll§3§lm§9§le§5§lt");
            List<String> lore = new ArrayList<>();
            lore.add("§aRainbow Collection§7: §71/4");
            lore.add("§7»»————-　★　————-««");
            lore.add("§8Standort: §cC§6a§en§ad§3y §5L§ca§6n§ed");
            lore.add("§7»»————-　★　————-««");
            lore.add("§e★★★★");
            rainbow_armor_meta.setLore(lore);

            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_DYE);

            createNBT("rainbow", "00003", rainbow_armor_meta);

            rainbow_armor.setItemMeta(rainbow_armor_meta);
            rainbow_armor.setAmount(1);

            result_item = rainbow_armor;
        } else if (item.equals("00004")) {
            ItemStack rainbow_armor = new ItemStack(Material.LEATHER_CHESTPLATE);
            LeatherArmorMeta rainbow_armor_meta = (LeatherArmorMeta) rainbow_armor.getItemMeta();

            rainbow_armor_meta.setDisplayName("§c§lR§6§la§e§li§a§ln§3§lb§9§lo§5§lw §6§lC§e§lh§a§le§3§ls§9§lt§5§lp§c§ll§6§la§e§lt§a§le");
            List<String> lore = new ArrayList<>();
            lore.add("§aRainbow Collection§7: §72/4");
            lore.add("§7»»————-　★　————-««");
            lore.add("§8Standort: §cC§6a§en§ad§3y §5L§ca§6n§ed");
            lore.add("§7»»————-　★　————-««");
            lore.add("§e★★★★");
            rainbow_armor_meta.setLore(lore);

            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_DYE);

            createNBT("rainbow", "00004", rainbow_armor_meta);

            rainbow_armor.setItemMeta(rainbow_armor_meta);
            rainbow_armor.setAmount(1);

            result_item = rainbow_armor;
        } else if (item.equals("00005")) {
            ItemStack rainbow_armor = new ItemStack(Material.LEATHER_LEGGINGS);
            LeatherArmorMeta rainbow_armor_meta = (LeatherArmorMeta) rainbow_armor.getItemMeta();

            rainbow_armor_meta.setDisplayName("§c§lR§6§la§e§li§a§ln§3§lb§9§lo§5§lw §6§lL§e§le§a§lg§3§lg§9§li§5§ln§c§lg§6§ls");
            List<String> lore = new ArrayList<>();
            lore.add("§aRainbow Collection§7: §73/4");
            lore.add("§7»»————-　★　————-««");
            lore.add("§8Standort: §cC§6a§en§ad§3y §5L§ca§6n§ed");
            lore.add("§7»»————-　★　————-««");
            lore.add("§e★★★★");
            rainbow_armor_meta.setLore(lore);

            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_DYE);

            createNBT("rainbow", "00005", rainbow_armor_meta);

            rainbow_armor.setItemMeta(rainbow_armor_meta);
            rainbow_armor.setAmount(1);

            result_item = rainbow_armor;
        } else if (item.equals("00006")) {
            ItemStack rainbow_armor = new ItemStack(Material.LEATHER_BOOTS);
            LeatherArmorMeta rainbow_armor_meta = (LeatherArmorMeta) rainbow_armor.getItemMeta();

            rainbow_armor_meta.setDisplayName("§c§lR§6§la§e§li§a§ln§3§lb§9§lo§5§lw §6§lB§e§lo§a§lo§3§lt§9§ls");
            List<String> lore = new ArrayList<>();
            lore.add("§aRainbow Collection§7: §74/4");
            lore.add("§7»»————-　★　————-««");
            lore.add("§8Standort: §cC§6a§en§ad§3y §5L§ca§6n§ed");
            lore.add("§7»»————-　★　————-««");
            lore.add("§e★★★★");
            rainbow_armor_meta.setLore(lore);

            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            rainbow_armor_meta.addItemFlags(ItemFlag.HIDE_DYE);

            createNBT("rainbow", "00006", rainbow_armor_meta);

            rainbow_armor.setItemMeta(rainbow_armor_meta);
            rainbow_armor.setAmount(1);

            result_item = rainbow_armor;
        } else if (item.equals("00007")) {
            ItemStack EndStoneSword = new ItemStack(Material.GOLDEN_SWORD);
            ItemMeta meta = EndStoneSword.getItemMeta();

            meta.setDisplayName("§9End Stone Sword");
            List<String> lore = new ArrayList<>();
            lore.add("§7Strenght: §46 §8(+Sharpness: 8.5)");
            lore.add("§7Gemstone Slot: §8(♨)");
            lore.add("§7»»————-　★　————-««");
            lore.add("§7Sharpness IV, Looting II,");
            lore.add("§7Unbreaking X");
            lore.add("");
            lore.add("§6Fähigkeit: §aEndstone Healing");
            lore.add("§e<< Right Click >>");
            lore.add("§8Cooldown: (3:00)");
            lore.add("§7»»————-　★　————-««");
            String standortLine = "§8Standort: §#" + ChatColor.of("#e9d392") + "Endisland";
            lore.add(standortLine);
            lore.add("§7»»————-　★　————-««");
            lore.add("§cVerzauberungs Sperre");
            lore.add("§7-----------------");
            lore.add("§9§lRARE SWORD");
            meta.setLore(lore);

            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            meta.addItemFlags(ItemFlag.HIDE_DYE);

            meta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 2, true);
            meta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
            meta.addEnchant(Enchantment.DURABILITY, 10, true);

            meta.setCustomModelData(2);

            AttributeModifier damageModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 6.0, AttributeModifier.Operation.ADD_NUMBER);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);

            createNBT("endstonesword", "slot1", meta);
            createNBT("lockEntchant", "001", meta);

            EndStoneSword.setDurability((short) 300);
            EndStoneSword.setItemMeta(meta);
            EndStoneSword.setAmount(1);

            result_item = EndStoneSword;
        } else if (item.equals("00008")) {
            ItemStack Engel_Flugel = new ItemStack(Material.LEATHER_HORSE_ARMOR);
            ItemMeta meta = Engel_Flugel.getItemMeta();

            meta.setDisplayName("§f§lEngels Flügel");
            List<String> lore = new ArrayList<>();
            lore.add("§7Diese Flügel wurden von, ");
            lore.add("§7einem §aEngel §7verlohren!.");
            lore.add("§7»»————-　★　————-««");
            lore.add("§8Standort: §fCloudville");
            lore.add("§8Extra: ");
            lore.add("§8-> §a+§c♡♡♡♡");
            lore.add("§8-> §a+§9\uD83D\uDEE1\uD83D\uDEE1\uD83D\uDEE1\uD83D\uDEE1");
            lore.add("§7»»————-　★　————-««");
            lore.add("§e★★★");
            meta.setLore(lore);

            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            meta.setCustomModelData(1);

            AttributeModifier health = new AttributeModifier(UUID.randomUUID(), "generic.maxhealth", +8.0, AttributeModifier.Operation.ADD_NUMBER);
            meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, health);

            AttributeModifier armor = new AttributeModifier(UUID.randomUUID(),"generic.armor", +8.0, AttributeModifier.Operation.ADD_NUMBER);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armor);

            createNBT("engel_flugel", "00008", meta);

            Engel_Flugel.setItemMeta(meta);
            Engel_Flugel.setAmount(1);
            result_item = Engel_Flugel;
        } else if (item.equals("00009")) {
            ItemStack BannHammer = new ItemStack(Material.NETHERITE_PICKAXE);
            ItemMeta meta = BannHammer.getItemMeta();
            BannHammer.setAmount(1);
            meta.setDisplayName("§c§lBann §f§lHammer");
            List<String> lore = new ArrayList<>();
            lore.add("§7Ein wertvolles §cAdmin Item§7, welches");
            lore.add("§7nicht in die Falschen Hände gelangen sollte!");
            lore.add("§7»»————-　★　————-««");
            lore.add("§7Efficiency IX");
            lore.add("§7Mending II");
            lore.add("§7Unbreaking V");
            lore.add("§7Sharpness VII");
            lore.add("");
            lore.add("§7Gemstone Slot 1: §8(⚶) §7<- +§81 Efficiency");
            lore.add("§7Gemstone Slot 2: §8(⚶) §7<- +§81 Efficiency");
            lore.add("§7Gemstone Slot 3: §8(⚶) §7<- +§81 Efficiency §8/ §8Entchantbar");
            lore.add("");
            lore.add("§8Extra: ");
            lore.add("§8-> §a+§c♡♡♡♡♡♡♡♡♡");
            lore.add("§8-> §a+§9\uD83D\uDEE1\uD83D\uDEE1\uD83D\uDEE1\uD83D\uDEE1\uD83D\uDEE1");
            lore.add("§7»»————-　★　————-««");
            lore.add("§c★★★★★★");
            meta.setLore(lore);

            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            meta.setCustomModelData(1);

            AttributeModifier health = new AttributeModifier(UUID.randomUUID(), "generic.maxhealth", +20.0, AttributeModifier.Operation.ADD_NUMBER);
            meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, health);

            AttributeModifier armor = new AttributeModifier(UUID.randomUUID(),"generic.armor", +10.0, AttributeModifier.Operation.ADD_NUMBER);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armor);

            AttributeModifier damage = new AttributeModifier(UUID.randomUUID(), "generic.attack.damage", 10.0, AttributeModifier.Operation.ADD_NUMBER);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damage);

            meta.addEnchant(Enchantment.DURABILITY, 5, true);
            meta.addEnchant(Enchantment.DIG_SPEED, 9,true);
            meta.addEnchant(Enchantment.MENDING, 2,true);
            meta.addEnchant(Enchantment.DAMAGE_ALL, 7,true);

            createNBT("BannHammer", "00009", meta);
            createNBT("lockEntchant", "001", meta);

            BannHammer.setItemMeta(meta);
            result_item = BannHammer;
        } else if(item.equals("00010")) {
            ItemStack BannHammer = new ItemStack(Material.NETHERITE_PICKAXE);
            ItemMeta meta = BannHammer.getItemMeta();
            BannHammer.setAmount(1);
            meta.setDisplayName("§c§lBann §f§lHammer");
            List<String> lore = new ArrayList<>();
            lore.add("§7Ein wertvolles §cAdmin Item§7, welches");
            lore.add("§7nicht in die Falschen Hände gelangen sollte!");
            lore.add("§7»»————-　★　————-««");
            lore.add("§7Efficiency X");
            lore.add("§7Mending II");
            lore.add("§7Unbreaking V");
            lore.add("§7Sharpness VII");
            lore.add("");
            lore.add("§7Gemstone Slot 1: §8(§a✔§8) §7<- §a+§71 Efficiency");
            lore.add("§7Gemstone Slot 2: §8(⚶) §7<- +§81 Efficiency");
            lore.add("§7Gemstone Slot 3: §8(⚶) §7<- +§81 Efficiency §8/ §8Entchantbar");
            lore.add("");
            lore.add("§8Extra: ");
            lore.add("§8-> §a+§c♡♡♡♡♡♡♡♡♡");
            lore.add("§8-> §a+§9\uD83D\uDEE1\uD83D\uDEE1\uD83D\uDEE1\uD83D\uDEE1\uD83D\uDEE1");
            lore.add("§7»»————-　★　————-««");
            lore.add("§c★★★★★★");
            meta.setLore(lore);

            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            meta.setCustomModelData(1);

            AttributeModifier health = new AttributeModifier(UUID.randomUUID(), "generic.maxhealth", +20.0, AttributeModifier.Operation.ADD_NUMBER);
            meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, health);

            AttributeModifier armor = new AttributeModifier(UUID.randomUUID(),"generic.armor", +10.0, AttributeModifier.Operation.ADD_NUMBER);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armor);

            AttributeModifier damage = new AttributeModifier(UUID.randomUUID(), "generic.attack.damage", 10.0, AttributeModifier.Operation.ADD_NUMBER);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damage);

            meta.addEnchant(Enchantment.DURABILITY, 5, true);
            meta.addEnchant(Enchantment.DIG_SPEED, 10,true);
            meta.addEnchant(Enchantment.MENDING, 2,true);
            meta.addEnchant(Enchantment.DAMAGE_ALL, 7,true);

            createNBT("BannHammer", "00009", meta);
            createNBT("lockEntchant", "001", meta);

            BannHammer.setItemMeta(meta);
            result_item = BannHammer;
        } else if (item.equals("00011")) {
            ItemStack BannHammer = new ItemStack(Material.NETHERITE_PICKAXE);
            ItemMeta meta = BannHammer.getItemMeta();
            BannHammer.setAmount(1);
            meta.setDisplayName("§c§lBann §f§lHammer");
            List<String> lore = new ArrayList<>();
            lore.add("§7Ein wertvolles §cAdmin Item§7, welches");
            lore.add("§7nicht in die Falschen Hände gelangen sollte!");
            lore.add("§7»»————-　★　————-««");
            lore.add("§7Efficiency XI");
            lore.add("§7Mending II");
            lore.add("§7Unbreaking V");
            lore.add("§7Sharpness VII");
            lore.add("");
            lore.add("§7Gemstone Slot 1: §8(§a✔§8) §7<- §a+§71 Efficiency");
            lore.add("§7Gemstone Slot 2: §8(§a✔§8) §7<- §a+§71 Efficiency");
            lore.add("§7Gemstone Slot 3: §8(⚶) §7<- +§81 Efficiency §8/ §8Entchantbar");
            lore.add("");
            lore.add("§8Extra: ");
            lore.add("§8-> §a+§c♡♡♡♡♡♡♡♡♡");
            lore.add("§8-> §a+§9\uD83D\uDEE1\uD83D\uDEE1\uD83D\uDEE1\uD83D\uDEE1\uD83D\uDEE1");
            lore.add("§7»»————-　★　————-««");
            lore.add("§c★★★★★★");
            meta.setLore(lore);

            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            meta.setCustomModelData(1);

            AttributeModifier health = new AttributeModifier(UUID.randomUUID(), "generic.maxhealth", +20.0, AttributeModifier.Operation.ADD_NUMBER);
            meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, health);

            AttributeModifier armor = new AttributeModifier(UUID.randomUUID(),"generic.armor", +10.0, AttributeModifier.Operation.ADD_NUMBER);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armor);

            AttributeModifier damage = new AttributeModifier(UUID.randomUUID(), "generic.attack.damage", 10.0, AttributeModifier.Operation.ADD_NUMBER);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damage);

            meta.addEnchant(Enchantment.DURABILITY, 5, true);
            meta.addEnchant(Enchantment.DIG_SPEED, 11,true);
            meta.addEnchant(Enchantment.MENDING, 2,true);
            meta.addEnchant(Enchantment.DAMAGE_ALL, 7,true);

            createNBT("BannHammer", "00009", meta);
            createNBT("lockEntchant", "001", meta);

            BannHammer.setItemMeta(meta);
            result_item = BannHammer;
        } else if(item.equals("00012")) {
            ItemStack BannHammer = new ItemStack(Material.NETHERITE_PICKAXE);
            ItemMeta meta = BannHammer.getItemMeta();
            BannHammer.setAmount(1);
            meta.setDisplayName("§c§lBann §f§lHammer");
            List<String> lore = new ArrayList<>();
            lore.add("§7Ein wertvolles §cAdmin Item§7, welches");
            lore.add("§7nicht in die Falschen Hände gelangen sollte!");
            lore.add("§7»»————-　★　————-««");
            lore.add("§7Efficiency XII");
            lore.add("§7Mending II");
            lore.add("§7Unbreaking V");
            lore.add("§7Sharpness VII");
            lore.add("");
            lore.add("§7Gemstone Slot 1: §8(§a✔§8) §7<- §a+§71 Efficiency");
            lore.add("§7Gemstone Slot 2: §8(§a✔§8) §7<- §a+§71 Efficiency");
            lore.add("§7Gemstone Slot 3: §8(§a✔§8) §7<- §a+§71 Efficiency §7/ §2Entchantbar");
            lore.add("");
            lore.add("§8Extra: ");
            lore.add("§8-> §a+§c♡♡♡♡♡♡♡♡♡");
            lore.add("§8-> §a+§9\uD83D\uDEE1\uD83D\uDEE1\uD83D\uDEE1\uD83D\uDEE1\uD83D\uDEE1");
            lore.add("§7»»————-　★　————-««");
            lore.add("§8Secret:");
            lore.add("§6Fähigkeit §7-> 20 Sekunden 3x3 abbauen");
            lore.add("§6Abklinkzeit §7-> 8:20");
            lore.add("§7»»————-　★　————-««");
            lore.add("§c★★★★★★");
            meta.setLore(lore);

            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            meta.setCustomModelData(1);

            AttributeModifier health = new AttributeModifier(UUID.randomUUID(), "generic.maxhealth", +20.0, AttributeModifier.Operation.ADD_NUMBER);
            meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, health);

            AttributeModifier armor = new AttributeModifier(UUID.randomUUID(),"generic.armor", +10.0, AttributeModifier.Operation.ADD_NUMBER);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armor);

            AttributeModifier damage = new AttributeModifier(UUID.randomUUID(), "generic.attack.damage", 10.0, AttributeModifier.Operation.ADD_NUMBER);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damage);

            meta.addEnchant(Enchantment.DURABILITY, 5, true);
            meta.addEnchant(Enchantment.DIG_SPEED, 12,true);
            meta.addEnchant(Enchantment.MENDING, 2,true);
            meta.addEnchant(Enchantment.DAMAGE_ALL, 7,true);

            createNBT("BannHammer", "00009", meta);
            createNBT("3x3", "01", meta);

            BannHammer.setItemMeta(meta);
            result_item = BannHammer;
        } else if(item.equals("00013")) {
            ItemStack MagmaSword = new ItemStack(Material.WOODEN_SWORD);
            ItemMeta meta = MagmaSword.getItemMeta();
            meta.setDisplayName("§c§lMagma §6§lSword §r§7- §b§lV2");
            MagmaSword.setAmount(1);
            List<String> lore = new ArrayList<>();
            lore.add("§7Vitality: §l5 §r§8(20 sek)");
            lore.add("§7Damage: §7§l10.5");
            lore.add("§7»»————-　★　————-««");
            lore.add("§7Sharpness V");
            lore.add("§7Bane of arthropods V");
            lore.add("§7Smite V");
            lore.add("§7Fire aspect V");
            lore.add("§7Looting III");
            lore.add("§7Knockback II");
            lore.add("§7»»————-　★　————-««");
            lore.add("§6Fähigkeit: §cFireball");
            lore.add("§e<< Right Click >>");
            lore.add("§8Cooldown: (1:00)");
            lore.add("§7»»————-　★　————-««");
            lore.add("§8Standort: §cNether");
            lore.add("§8Gemstone Slot: (✦)");
            lore.add("§7»»————-　★　————-««");
            lore.add("§cVerzauberungs Sperre");
            lore.add("§7-------------");
            lore.add("§6★★★");
            meta.setLore(lore);

            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            meta.setCustomModelData(2);
            meta.setUnbreakable(true);

            AttributeModifier health = new AttributeModifier(UUID.randomUUID(), "generic.maxhealth", +2.0, AttributeModifier.Operation.ADD_NUMBER);
            meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, health);

            AttributeModifier armor = new AttributeModifier(UUID.randomUUID(),"generic.armor", +1.0, AttributeModifier.Operation.ADD_NUMBER);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armor);

            AttributeModifier damage = new AttributeModifier(UUID.randomUUID(), "generic.attack.damage", 4.0, AttributeModifier.Operation.ADD_NUMBER);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damage);

            meta.addEnchant(Enchantment.DAMAGE_UNDEAD, 5, true);
            meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
            meta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 5, true);
            meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
            meta.addEnchant(Enchantment.FIRE_ASPECT, 5, true);
            meta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 3, true);

            createNBT("MagmaSword", "00013", meta);

            MagmaSword.setItemMeta(meta);
            result_item = MagmaSword;
        } else if (item.equals("00014")) {
            ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
            sword.setAmount(1);
            ItemMeta meta = sword.getItemMeta();
            meta.setUnbreakable(true);
            meta.setDisplayName("§9Ender Sword");
            createNBT("EnderSword", "00014", meta);
            sword.setItemMeta(meta);
            result_item = sword;
        } else if(item.equals("00015")) {
            ItemStack multiTool = new ItemStack(Material.WOODEN_PICKAXE);
            ItemMeta meta = multiTool.getItemMeta();

            createNBT("multiTool", "00015", meta);
            multiTool.setItemMeta(meta);

            result_item = multiTool;
        }

        return result_item;
    }

    public void createNBT(String key_name,String DataString, ItemMeta item) {
        NamespacedKey key = new NamespacedKey(plugin, key_name);
        PersistentDataContainer data2 = item.getPersistentDataContainer();
        data2.set(key, PersistentDataType.STRING, DataString);
    }

}
