package me.txs.txcrates.util;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.txs.txcrates.TxCrates;

public class ItemUtil {

	@SuppressWarnings("deprecation")
	public static ItemStack makeItem(Material mat, int amount, String displayName, String... lore) {
		ItemStack item = new ItemStack(mat, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(StringUtil.toColor(displayName));
		meta.setLore(StringUtil.toColor(Arrays.asList(lore)));
		item.setItemMeta(meta);
		return item;
	}
	
	public static boolean isFiller(ItemStack item) {
		return item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING);
	}
	
	private static NamespacedKey key;
	
	static {
		key = new NamespacedKey(TxCrates.getInstance(), "txcrates-filler-item");
	}
	
	public static NamespacedKey getFillerKey() {
		return key;
	}
	
	public static ItemStack makeFiller(ItemStack item) {
		var meta = item.getItemMeta();
		meta.getPersistentDataContainer().set(getFillerKey(), PersistentDataType.STRING, "filler");
		item.setItemMeta(meta);
		return item;
	}
}
