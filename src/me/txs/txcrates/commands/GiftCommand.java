package me.txs.txcrates.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class GiftCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player && sender.hasPermission("txcrates.admin.gift"))) {
			sender.sendMessage(ChatColor.RED + "You lack permission to run this command.");
			return true;
		}
		
		
		
		return true;
	}
}
