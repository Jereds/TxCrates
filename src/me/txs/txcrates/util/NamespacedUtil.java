package me.txs.txcrates.util;

import org.bukkit.NamespacedKey;

import me.txs.txcrates.TxCrates;

public class NamespacedUtil {
	
	private static final NamespacedKey crate;
	static {
		crate = new NamespacedKey(TxCrates.getInstance(), "crate-item-type-id");
	}
	
	public static NamespacedKey getCrateItemTypeKey() {
		return crate;
	}
	
	private static final NamespacedKey crateBlock;
	static {
		crateBlock = new NamespacedKey(TxCrates.getInstance(), "crate-block-type-id");
	}
	
	public static NamespacedKey getCrateBlockTypeKey() {
		return crateBlock;
	}
}
