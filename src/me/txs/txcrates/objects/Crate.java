package me.txs.txcrates.objects;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataType;

import me.jereds.containerapi.objects.Container;
import me.txs.txcrates.util.NamespacedUtil;
import net.md_5.bungee.api.ChatColor;

public class Crate {

	private Container crate;
	private final Block block;

	public Crate(Container crate, Block block) {
		this.crate = crate;
		this.block = block;
	}

	public String getId() {
		return ChatColor.stripColor(crate.getDisplay());
	}

	public void create() {
		var state = (org.bukkit.block.Container) block.getState();
		state.getPersistentDataContainer().set(NamespacedUtil.getCrateBlockTypeKey(), PersistentDataType.STRING, getId());
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
