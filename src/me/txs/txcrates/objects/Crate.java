package me.txs.txcrates.objects;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataType;

import me.jereds.containerapi.objects.Container;
import me.txs.txcrates.util.NamespacedUtil;

public class Crate {

	private Container crate;
	private final Block block;

	public Crate(Container crate, Block block) {
		this.crate = crate;
		this.block = block;
	}

	public void create() {
		var state = (org.bukkit.block.Container) block.getState();
		state.getPersistentDataContainer().set(NamespacedUtil.getCrateBlockTypeKey(), PersistentDataType.STRING, crate.getId());
		state.update();
	}

	public Container getCrate() {
		return crate;
	}

	public void delete() {
		crate = null;
		block.setType(Material.AIR);
	}

	public Block getBlock() {
		return block;
	}
}
