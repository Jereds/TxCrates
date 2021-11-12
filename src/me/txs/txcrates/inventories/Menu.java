package me.txs.txcrates.inventories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public abstract class Menu implements InventoryHolder {
	
	private final static List<Player> viewers = new ArrayList<>();
	
	public static void onDisable() {
		viewers.forEach(Player::closeInventory);
	}

	protected final Inventory inv;
	protected final Player player;
	public Menu(Player player, String title, int size) {
		this.inv = Bukkit.createInventory(this, size, title);
		this.player = player;
		build();
		//no longer putting open() here, ExistingCratesMenu would open even if there were no crates, moved open() to after the return so it only opens when there are crates.
	}
	
	@Override
	public Inventory getInventory() {
		return inv;
	}
	
	public abstract void onClick(InventoryClickEvent event);
	public abstract void onClose(InventoryCloseEvent event);
	
	public abstract void build();
	
	private ItemStack filler = null;
	public void setFiller(ItemStack item) {
		filler = item;
	}
	
	public void fill() {
		IntStream.range(0, inv.getSize()).forEach(slot -> inv.setItem(slot, filler));
	}
	
	public void open() {
		player.openInventory(inv);
	}
	
	public void close() {
		viewers.remove(player);
		player.closeInventory();
	}
	
	public List<Player> getViewersList(){
		return viewers;
	}
}
