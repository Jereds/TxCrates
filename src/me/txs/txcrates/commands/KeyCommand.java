package me.txs.txcrates.commands;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.jereds.containerapi.objects.Container;
import me.jereds.containerapi.util.ContainerUtil;
import me.txs.txcrates.util.CrateUtil;
import me.txs.txcrates.util.InventoryUtil;
import me.txs.txcrates.util.StringUtil;
import net.md_5.bungee.api.ChatColor;

public class KeyCommand implements CommandExecutor, TabCompleter {

	private int amount = 1;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("TxCrates.admin.key")) {
			sender.sendMessage(
					StringUtil.getPrefix() + ChatColor.RED + "Sorry! You do not have permission to run this command.");
			return true;
		}

		if (args.length < 2) {
			sender.sendMessage(StringUtil.getPrefix() + ChatColor.RED
					+ "Incorrect format, please try /cratekey <player> <crate id> <amount>");
			return true;
		}

		// 0 1 2
		// /cratekey <player> <crate id> [amount]

		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(
					StringUtil.getPrefix() + ChatColor.RED + "Sorry I could not find player '" + args[0] + "'");
			return true;
		}

		String id = String.join(" ", Arrays.copyOfRange(args, 1, args.length - 1));

		try {
			amount = Integer.parseInt(args[args.length - 1]);
		} catch (NumberFormatException e) {
			id = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
			amount = 1;
		}

		if (amount > 500) {
			sender.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "Wtf do you think you're doing?");
			return true;
		}

		if (amount < 1) {
			sender.sendMessage(
					StringUtil.getPrefix() + ChatColor.RED + "Um... you can't have negative crate keys dingus.");
			return true;
		}

		CrateUtil.fromId(id).ifPresentOrElse(crate -> {
			sender.sendMessage(StringUtil.getPrefix() + ChatColor.GREEN + "Successfully gave " + target.getDisplayName()
					+ ChatColor.GREEN + " " + amount + " key" + (amount > 1 ? "s" : "") + ".");
			target.sendMessage(StringUtil.getPrefix() + ChatColor.GREEN + "Successfully recieved" + 
					ChatColor.GREEN + " " + amount + " " + crate.getDisplay() + ChatColor.GREEN + " key" + (amount > 1 ? "s" : "") + ".");
			InventoryUtil.addCrateKey(target, crate, amount);
		}, () -> sender.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "Sorry! I couldn't find that crate."));
		amount = 1;
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 2)
			return ContainerUtil.getAllContainers().stream().map(Container::getId)
					.filter(str -> str.toLowerCase().startsWith(args[1].toLowerCase()) || str.trim().isEmpty())
					.collect(Collectors.toList());
		return null;
	}
}
