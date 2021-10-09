package me.txs.txcrates.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.txs.txcrates.util.CrateUtil;
import me.txs.txcrates.util.InventoryUtil;
import me.txs.txcrates.util.StringUtil;
import net.md_5.bungee.api.ChatColor;

public class CrateKeyAllCommand implements CommandExecutor {

	private int amount = 1;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("TxCrates.admin.key")) {
			sender.sendMessage(
					StringUtil.getPrefix() + ChatColor.RED + "Sorry! You do not have permission to run this command.");
			return true;
		}

		if (args.length < 1) {
			sender.sendMessage(StringUtil.getPrefix() + ChatColor.RED
					+ "Incorrect format, please try /cratekeyall <crate id> <amount>");
			return true;
		}
		
		// 				0          1
		// /cratekeyall <crate id> [amount]

		String id = String.join(" ", Arrays.copyOfRange(args, 0, args.length - 1));

		try {
			amount = Integer.parseInt(args[args.length - 1]);
		} catch (NumberFormatException e) {
			id = String.join(" ", Arrays.copyOfRange(args, 0, args.length));
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
			ItemStack item = CrateUtil.getCrateKey(crate);
			item.setAmount(amount);
			for(Player player : Bukkit.getOnlinePlayers()) {
				InventoryUtil.addItem(player, item);
				sender.sendMessage(StringUtil.getPrefix() + ChatColor.GREEN + "Successfully gave " + player.displayName()
						+ ChatColor.GREEN + " " + amount + " key" + (amount > 1 ? "s" : "") + ".");
				player.sendMessage(StringUtil.getPrefix() + ChatColor.GREEN + "Successfully recieved" + 
						ChatColor.GREEN + " " + amount + " " + crate.getDisplay() + ChatColor.GREEN + " key" + (amount > 1 ? "s" : "") + ".");
			}
		}, () -> sender.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "Sorry! I couldn't find that crate."));
		amount = 1;
		return true;
	}
}
