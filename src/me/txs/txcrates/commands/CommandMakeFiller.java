package me.txs.txcrates.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import me.txs.txcrates.util.ItemUtil;
import me.txs.txcrates.util.StringUtil;
import net.md_5.bungee.api.ChatColor;

public class CommandMakeFiller implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player && sender.hasPermission("txcrates.makefiller"))) {
			sender.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "You lack permission to run this command.");
			return true;
		}
		
		var player = (Player)sender;
		var item = player.getInventory().getItemInMainHand();
		if(item == null || item.getType() == Material.AIR) {
			sender.sendMessage(ChatColor.RED + "You must hold a valid item to run this command.");
			return true;
		}
		
		makeFiller(item);
		sender.sendMessage(ChatColor.GREEN + "Your item is now a filler item! Add it to a container without it being a winnable prize.");
		return true;
	}
	
	private ItemStack makeFiller(ItemStack item) {
		var meta = item.getItemMeta();
		meta.getPersistentDataContainer().set(ItemUtil.getFillerKey(), PersistentDataType.STRING, "filler");
		item.setItemMeta(meta);
		return item;
	}
}
