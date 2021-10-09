package me.txs.txcrates.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.txs.txcrates.inventories.BaseMenu;
import me.txs.txcrates.util.StringUtil;
import net.md_5.bungee.api.ChatColor;

public class CratesCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String l, String[] args) {
		if(!(sender instanceof Player && sender.hasPermission("txcrates.admin.crates"))) {
			sender.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "You lack permission to run this command!");
			return true;
		}
		
		new BaseMenu((Player)sender);
		return true;
	}
}
