package me.txs.txcrates.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import me.jereds.containerapi.objects.Container;
import me.jereds.containerapi.util.ContainerUtil;
import me.txs.txcrates.TxCrates;
import me.txs.txcrates.util.CrateUtil;
import me.txs.txcrates.util.InventoryUtil;
import me.txs.txcrates.util.StringUtil;
import net.md_5.bungee.api.ChatColor;

public class CommandClaimKeys implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "You must be a player to run this command!");
			return true;
		}
		
		var player = (Player)sender;
		var keys = 0;
		for(Container container : new ContainerUtil(TxCrates.getHolder()).getAllContainers()) {
			var tag = new NamespacedKey(TxCrates.getInstance(), container.getId().replace(' ', '_') + "-claimable"); //needs to be declared here because it has container id in the name
			var cont = player.getPersistentDataContainer();
			if(cont.has(tag, PersistentDataType.INTEGER)) {
				var key = CrateUtil.getCrateKey(container);
				key.setAmount(cont.get(tag, PersistentDataType.INTEGER));
				InventoryUtil.addItem(player, key);
				cont.remove(tag);
				keys++;
			}
		}
		if(keys == 0) {
			player.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "You have no crate keys to claim!");
			return true;
		}
		player.sendMessage(StringUtil.getPrefix() + ChatColor.GREEN + "Successfully claimed all outstanding crate keys!");
		return true;
	}
	
	public static void startScheduler() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(TxCrates.getInstance(), () -> {
			List<Player> players = new ArrayList<>();
			playerloop: for(Player player : Bukkit.getOnlinePlayers()) {
				for(Container container : new ContainerUtil(TxCrates.getHolder()).getAllContainers()) {
					var tag = new NamespacedKey(TxCrates.getInstance(), container.getId().replace(' ', '_') + "-claimable"); //needs to be declared here because it has container id in the name
					if(player.getPersistentDataContainer().has(tag, PersistentDataType.INTEGER)) {
						players.add(player);
						continue playerloop;
					}
				}
			}
			
			players.forEach(player -> player.sendMessage(StringUtil.getPrefix() + ChatColor.LIGHT_PURPLE + "You have crate keys waiting to be claimed! Please use /claimkeys. Make sure you have empty spaces in your inventory."));
		}, 0, 5 * 60 * 20); //runs every 5 mins
	}
}
