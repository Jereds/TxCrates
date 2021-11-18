package me.txs.txcrates.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jereds.containerapi.objects.Container;
import me.jereds.containerapi.util.ContainerUtil;
import me.txs.txcrates.TxCrates;
import me.txs.txcrates.inventories.CreateCrateMenu;
import me.txs.txcrates.util.StringUtil;
import net.md_5.bungee.api.ChatColor;

public class CommandCreateCrate implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player && sender.hasPermission("TxCrates.admin"))) {
			sender.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "You lack permission to run this command!");
			return true;
		}
		
		//createcrate <display>
		if(args .length < 1) {
			sender.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "Please try /createcrate <display>");
			return true;
		}
		
		var builder = new StringBuilder();
		for (int i = 1; i < args.length; i++) {
		    builder.append(args[i] + " ");
		}
		
		String display = builder.toString().trim();

		var player = (Player)sender;
		
		var util = new ContainerUtil(TxCrates.getHolder());
		util.getById(Container.displayToID(display)).ifPresentOrElse(container -> {
			sender.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "A container with this ID already exists!");
			return;
		}, () -> {
			new CreateCrateMenu(player, display);
			player.sendMessage(StringUtil.getPrefix() + ChatColor.GREEN + "Creating a new crate called " + display);
		});
		
		return true;
	}
}
