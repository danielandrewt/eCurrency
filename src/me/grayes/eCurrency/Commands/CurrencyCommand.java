package me.grayes.eCurrency.Commands;

import Utilities.ChatUtils;
import me.grayes.eCurrency.Main;
import me.grayes.eCurrency.Managers.CurrencyManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CurrencyCommand implements CommandExecutor {

    public Main plugin;

    CurrencyManager cm = new CurrencyManager(plugin);

    public CurrencyCommand(Main plugin) {
        this.plugin = plugin;

        plugin.getCommand("currency").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (commandSender.hasPermission("ecurrency.use")) {
            if (args.length == 0) {
                commandSender.sendMessage(ChatUtils.chat("&c/currency <add/subtract/set> <playerName>!"));
                return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("add")) {
                    commandSender.sendMessage(ChatUtils.chat("&c/currency add <playerName> <amount>!"));
                } else if (args[0].equalsIgnoreCase("subtract")) {
                    commandSender.sendMessage(ChatUtils.chat("&c/currency subtract <playerName> <amount>!"));
                } else if (args[0].equalsIgnoreCase("set")) {
                    commandSender.sendMessage(ChatUtils.chat("&c/currency set <playerName> <amount>!"));
                } else {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
                    if (p != null) {
                        commandSender.sendMessage(ChatUtils.chat("&e" + args[1] + "'s &abalance: " + "&2" + cm.getPlayerCurrency(p)));
                    } else {
                        commandSender.sendMessage(ChatUtils.chat("&e" + args[1] + " &ccould not be found!"));
                        return true;
                    }
                }
            } else if (args.length == 2) {
                @SuppressWarnings("depreciation")
                // /ecurrency <action> <playerName>
                OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);

                //checks if the player exists.
                if (p != null) {
                    commandSender.sendMessage(ChatUtils.chat("&c/ecurrency <action> " + p.getName() + " <amount>!"));
                } else {
                    commandSender.sendMessage(ChatUtils.chat("&e" + args[1] + " &ccould not be found!"));
                }
            } else if (args.length == 3) {
                // /ecurrency <action> <playerName> <amount>
                @SuppressWarnings("depreciation")
                OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
                int amount = Integer.parseInt(args[2]);

                if (args[0].equalsIgnoreCase("add")) {
                    if (p != null) {
                        cm.addCurrencyToPlayer(p, amount);
                        commandSender.sendMessage(ChatUtils.chat("&aAdded &e" + amount + " $ato &e" + p.getName() + "&a!"));
                    } else {
                        commandSender.sendMessage(ChatUtils.chat("&e" + args[1] + " &ccould not be found!"));
                    }
                } else if (args[0].equalsIgnoreCase("subtract")) {
                    if (p != null) {
                        // checks if the player has less money than stated.
                        if (cm.getPlayerCurrency(p) <= amount) {
                            commandSender.sendMessage(ChatUtils.chat("That player doesn't have that much money!"));
                            return true;
                        } else {
                            cm.removeCurrencyFromPlayer(p, amount);
                            commandSender.sendMessage(ChatUtils.chat("&aRemoved &e" + amount + " $afrom &e" + p.getName() + "&a!"));
                        }
                    } else {
                        commandSender.sendMessage(ChatUtils.chat("&e" + args[1] + " &ccould not be found!"));
                    }
                } else if (args[0].equalsIgnoreCase("set")) {
                    if (p != null) {
                        cm.setPlayerCurrency(p, amount);
                        commandSender.sendMessage(ChatUtils.chat("Player's balance has been updated!"));
                    } else {
                        commandSender.sendMessage(ChatUtils.chat("&e" + args[1] + " &ccould not be found!"));
                    }
                }
            }
        } else {
            commandSender.sendMessage(ChatUtils.chat("$cYou do not have permission to perform this!"));
        }
        return false;
    }
}

