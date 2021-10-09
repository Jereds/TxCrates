package me.txs.txcrates.inventories;

import java.util.List;
import java.util.stream.IntStream;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.jereds.txcontainerapi.api.ContainerUtil;
import me.jereds.txcontainerapi.api.objects.Container;
import me.txs.txcrates.listeners.ChatListener;
import me.txs.txcrates.util.ItemUtil;
import net.md_5.bungee.api.ChatColor;

public class ExistingCratesMenu extends Menu {

	//admin menu showing existing crates ready to be edited
	public ExistingCratesMenu(Player player) {
		super(player, "Existing Crates", 54);
	}
	
	@Override
	public void build() {
		List<Container> crates = ContainerUtil.getAllContainers();
		if(crates.isEmpty()) {
			close();
			player.sendMessage(ChatColor.RED + "There are currently no crates! Try creating a new one.");
			return;
		}
//		int size = (int)Math.min(crates.size(), inv.getSize());
		
		//cannot go over 54 crates or this will error out
		IntStream.range(0, crates.size())
			.forEach(i -> inv.setItem(i, ItemUtil.makeItem(Material.CHEST, 1, crates.get(i).getDisplay(), 
					"&bEdit&7: Left Click", "&eRename&7: Middle Click", "&cDelete&7: Right Click")));
		open();
	}

	@Override	
	public void onClick(InventoryClickEvent event) {
		Player player = (Player)event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		ItemMeta meta = item.getItemMeta();
		if(!meta.hasDisplayName())
			return;
		
		Container container = ContainerUtil.getByDisplay(meta.displayName().toString()).get();
		
		switch(event.getClick()) {
		case LEFT:
			new CrateInventory(player, container);
			break;
		case MIDDLE:
			close();
			player.sendMessage(ChatColor.YELLOW + "What would you like to rename this crate to?"
					+ "\nType the name into chat, use '&' for color codes.\n"
					+ "Example: " + ChatColor.WHITE + "&cBig Crate");
			player.getPersistentDataContainer().set(ChatListener.getRenameKey(), PersistentDataType.STRING, container.getDisplay());
			break;
		case RIGHT:
			close();
			player.getPersistentDataContainer().set(ChatListener.getDeleteKey(), PersistentDataType.STRING, container.getDisplay());
			player.sendMessage(ChatColor.RED + "Are you sure you want to delete this crate? This cannot be undone. \n" +
					"Please enter the name of this crate to confirm deletion. \n"
					+ "DO NOT INCLUDE COLOR CODES!!");
			break;
		default:
			break;
		
		}
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}
}
