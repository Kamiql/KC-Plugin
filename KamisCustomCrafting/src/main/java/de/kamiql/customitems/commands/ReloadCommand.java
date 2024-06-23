package de.kamiql.customitems.commands;

import de.kamiql.customitems.Main;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ReloadCommand implements CommandExecutor {
    private final Server server;
    private final JavaPlugin plugin;

    public ReloadCommand(Server server, JavaPlugin plugin) {
        this.server = server;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("SMPlugin.reload")) {
            sender.sendMessage(Main.PREFIX + "Du hast nicht genügend Berechtigungen, um diesen Befehl zu verwenden!");
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("data")) {
            sender.sendMessage(Main.PREFIX + "Game Data reload started");
            Bukkit.reload();
            sender.sendMessage(Main.PREFIX + "Game Data reloaded");
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("config")) {
            sender.sendMessage(Main.PREFIX + "Configuration reload started");
            Bukkit.reloadData();
            sender.sendMessage(Main.PREFIX + "Configuration reloaded.");
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("soft")) {
            sender.sendMessage(Main.PREFIX + "Game Data and configuration reload started");
            Bukkit.reloadData();
            Bukkit.reload();
            sender.sendMessage(Main.PREFIX + "Game data and configuration reloaded.");
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("this")) {
            sender.sendMessage(Main.PREFIX + "Neuladen des Plugins gestartet");
            Bukkit.getPluginManager().disablePlugin(plugin);
            Bukkit.getPluginManager().enablePlugin(plugin);
            sender.sendMessage(Main.PREFIX + "Plugin wurde neu geladen.");
            return true;
        } else {
            sender.sendMessage(Main.PREFIX + "Game Data and configuration reload started");
            // kickAllPlayers(); // Alle Spieler vor dem Neuladen entfernen
            Bukkit.reloadData();
            Bukkit.reload();
            sender.sendMessage(Main.PREFIX + "Game data and configuration reloaded.");
            return false;
        }
    }

    // Methode, um alle Spieler vor dem Neuladen des Servers zu kicken
    private void kickAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer(Main.PREFIX + "Server wird neu geladen. Bitte logge dich erneut ein.");
        }
    }
}
