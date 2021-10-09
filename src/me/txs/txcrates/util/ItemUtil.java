package me.txs.txcrates.util;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {

	public static ItemStack makeItem(Material mat, int amount, String displayName, String... lore) {
		ItemStack item = new ItemStack(mat, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(StringUtil.toColor(displayName));
		meta.setLore(StringUtil.toColor(Arrays.asList(lore)));
		item.setItemMeta(meta);
		return item;
	}
}
