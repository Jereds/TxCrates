package me.txs.txcrates.vouchers;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import me.txs.txcrates.TxCrates;

public class VoucherUtil {

	protected static NamespacedKey voucher_key;
	static {
		voucher_key = new NamespacedKey(TxCrates.getInstance(), "voucher_item");
	}
	
	public static boolean isVoucher(ItemStack item) {
		return item.getItemMeta().getPersistentDataContainer().has(voucher_key, PersistentDataType.STRING);
	}
}
