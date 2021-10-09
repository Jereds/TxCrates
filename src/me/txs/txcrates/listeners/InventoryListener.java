package me.txs.txcrates.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

import me.txs.txcrates.inventories.Menu;

public class InventoryListener implements Listener {

	@EventHandler
	public void onInvClick(InventoryClickEvent event) {
		InventoryHolder holder = event.getInventory().getHolder();
		if (holder == null || !(holder instanceof Menu))
			return;

		Menu txHolder = (Menu)holder;
		
		if (event.getClick() != ClickType.LEFT && event.getClick() != ClickType.RIGHT && event.getClick() != ClickType.MIDDLE && !event.isShiftClick()) {
			event.setCancelled(true);
			return;
		}

		if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)
			return;
		
		if (event.getView().getTopInventory().equals(event.getClickedInventory()))
			txHolder.onClick(event);
	}

	@EventHandler
	public void onPlayerPickup(EntityPickupItemEvent event) {
		if (!(event.getEntity() instanceof Player))
			return;
		Player player = (Player) event.getEntity();
		if (player.getOpenInventory() != null && player.getOpenInventory().getTopInventory() != null)
			if (player.getOpenInventory().getTopInventory().getHolder() instanceof Menu)
				event.setCancelled(true);
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		InventoryHolder holder = event.getInventory().getHolder();
		if (holder == null || !(holder instanceof Menu))
			return;

		if (event.getView().getTopInventory().getHolder() instanceof Menu)
			((Menu)holder).onClose(event);
	}
}
