package me.txs.txcrates.inventories;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import me.jereds.containerapi.objects.Container;
import me.txs.txcrates.TxCrates;
import net.md_5.bungee.api.ChatColor;

public class CreateCrateMenu extends Menu {

	private final Container crate;
	private final String display;

	public CreateCrateMenu(Player player, String display) {
		super(player, display, 54);
		crate = new Container(new File(TxCrates.getInstance().getDataFolder() + "/crates", ChatColor.stripColor(display) + ".yml"), display);
		this.display = display;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
	}

	@Override
	public void onClose(InventoryCloseEvent event) {
		crate.saveItems(event.getInventory());
		event.getPlayer().sendMessage(
				ChatColor.GREEN + "You successfully created a crate called: " + ChatColor.RESET + crate.getDisplay());
		
		System.out.println(ChatColor.AQUA + "New crate was created by: " + ChatColor.WHITE + player.getName()
				+ ChatColor.AQUA + " called: " + display);
	}

	@Override
	public void build() {
		open();
	}
}
