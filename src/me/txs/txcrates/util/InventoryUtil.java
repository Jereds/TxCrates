package me.txs.txcrates.util;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import me.jereds.containerapi.objects.Container;
import me.txs.txcrates.TxCrates;
import net.md_5.bungee.api.ChatColor;

public class InventoryUtil {

	public static void addItem(Player player, ItemStack item) {
		if (!invIsFull(player.getInventory())) {
			player.getInventory().addItem(item);
		} else {
			player.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "Your inventory is too full to handle this item! Dropping it on the ground...");
			player.getWorld().dropItemNaturally(player.getLocation(), item);
		}
	}
	
	public static void addCrateKey(Player player, Container crate, int amount) {
		var key = CrateUtil.getCrateKey(crate);
		if(!invIsFull(player.getInventory())) {
			addItem(player, key);
			return;
		}
		
		else {
			player.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "We attempted to give you a crate key but your inventory was full! "
					+ "Please use /claimkeys to redeem.");
			var tag = new NamespacedKey(TxCrates.getInstance(), crate.getId().replace(' ', '_') + "-claimable");
			var amt = player.getPersistentDataContainer().getOrDefault(tag, PersistentDataType.INTEGER, 0);
			amt += amount;
			player.getPersistentDataContainer().set(tag, PersistentDataType.INTEGER, amt+1);
			return;
		}
		
	}

	public static boolean invIsFull(Inventory inv) {
		return inv.firstEmpty() == -1;
	}

	public static int getSize(int amountOfItems) {
		return (int)Math.ceil(amountOfItems / 9.0) * 9;
	}
}
