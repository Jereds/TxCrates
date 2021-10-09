package me.txs.txcrates.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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

	public static boolean invIsFull(Inventory inv) {
		return inv.firstEmpty() == -1;
	}

	public static int getSize(int amountOfItems) {
		return (int)Math.ceil(amountOfItems / 9.0) * 9;
	}
}
