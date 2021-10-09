package me.txs.txcrates.inventories;

import java.util.stream.IntStream;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import me.jereds.txcontainerapi.api.objects.Container;

public class CrateInventory extends Menu {

	private final Container crate;
	//the admin menu to edit crates
	public CrateInventory(Player player, Container crate) {
		super(player, crate.getDisplay(), 54);
		this.crate = crate;
		IntStream.range(0, getInventory().getSize()).forEach(slot -> {
			getInventory().setItem(slot, crate.getItems(true).get(slot));
		});
		open();
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		if(!event.getWhoClicked().hasPermission("TxCrates.admin.edit"))
			event.setCancelled(true);
	}

	@Override
	public void onClose(InventoryCloseEvent event) {
		if(!event.getPlayer().hasPermission("TxCrates.admin.edit"))
			return;
		crate.saveItems(event.getInventory());
	}

	@Override
	public void build() {}
}
