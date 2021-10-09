package me.txs.txcrates.inventories;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.persistence.PersistentDataType;

import me.txs.txcrates.listeners.ChatListener;
import me.txs.txcrates.util.ItemUtil;
import me.txs.txcrates.util.StringUtil;
import net.md_5.bungee.api.ChatColor;

public class BaseMenu extends Menu {
	
	//admin menu for creating and viewing existing crates.
	public BaseMenu(Player player) {
		super(player, "Crate Menu", 27);
	}
	
	private final int createSlot = 11;
	private final int viewSlot = 15;
	
	@Override
	public void build() {
		setFiller(ItemUtil.makeItem(Material.BLACK_STAINED_GLASS_PANE, 1, " "));
		fill();
		
		inv.setItem(createSlot, ItemUtil.makeItem(Material.CHEST, 1, "&aNew Crate"));
		inv.setItem(viewSlot, ItemUtil.makeItem(Material.CHEST, 1, "&bExisting Crates"));
		open();
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		Player player = (Player)event.getWhoClicked();
		event.setCancelled(true);
		switch(event.getSlot()) {
		case createSlot:
			close();
			player.getPersistentDataContainer().set(ChatListener.getCreateKey(), PersistentDataType.BYTE, (byte)1);
			player.sendMessage(StringUtil.getPrefix() + ChatColor.GRAY + "What is this crate called?"
					+ "\nType the name into chat, use '&' for color codes.\n"
					+ "Example: " + ChatColor.WHITE + "&cBig Crate");
			break;
		case viewSlot:
			new ExistingCratesMenu(player);
			break;
		default:
			break;
		}
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}
}
