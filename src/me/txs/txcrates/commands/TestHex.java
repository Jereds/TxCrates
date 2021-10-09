package me.txs.txcrates.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.jereds.pluginprefix.api.GradientManager;
import net.md_5.bungee.api.ChatColor;

public class TestHex implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		try {
			if (args.length < 3) {
				sender.sendMessage(ChatColor.of(args[1])+args[0]);
			} else {
			sender.sendMessage(GradientManager.toGradient(args[0], args[1], args[2]));
			}
		}
		catch(Exception e) {}
		return true;
	}
}
