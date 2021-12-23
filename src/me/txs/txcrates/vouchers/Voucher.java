package me.txs.txcrates.vouchers;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import me.jereds.containerapi.util.ItemUtil;
import me.txs.txcrates.TxCrates;

public enum Voucher {
	
	RANK_UPGRADE(Material.PAPER, "&cRank Upgrade", "/lp user %player% promote donor", "&6Right click this item", "&6to upgrade your rank!");
	
	private final NamespacedKey key;
	private final ItemStack item;
	private final String command;
	Voucher(Material mat, String command, String display, String... lore) {
		key = new NamespacedKey(TxCrates.getInstance(), name().toLowerCase());
		var item = ItemUtil.makeItem(mat, 1, display, lore);
		var meta = item.getItemMeta();
		meta.getPersistentDataContainer().set(VoucherUtil.voucher_key, PersistentDataType.STRING, "voucher");
		item.setItemMeta(meta);
		this.item = item;
		this.command = command;
	}
	
	public String getCommand(Player player) {
		return command.replaceAll("%player%", player.getName());
	}
	
	public NamespacedKey getKey() {
		return key;
	}
	
	public ItemStack getItem() {
		return item;
	}
}
