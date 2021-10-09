package me.txs.txcrates.listeners;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.persistence.PersistentDataType;

import me.jereds.txcontainerapi.api.ContainerUtil;
import me.txs.txcrates.TxCrates;
import me.txs.txcrates.inventories.CreateCrateMenu;
import me.txs.txcrates.util.StringUtil;
import net.md_5.bungee.api.ChatColor;

public class ChatListener implements Listener {

	private static final NamespacedKey key;
	private static final NamespacedKey delKey;
	private static final NamespacedKey renameKey;

	static {
		key = new NamespacedKey(TxCrates.getInstance(), "creating-crate");
		delKey = new NamespacedKey(TxCrates.getInstance(), "deleting-crate");
		renameKey = new NamespacedKey(TxCrates.getInstance(), "renaming-crate");
	}

	public static NamespacedKey getCreateKey() {
		return key;
	}

	public static NamespacedKey getDeleteKey() {
		return delKey;
	}

	public static NamespacedKey getRenameKey() {
		return renameKey;
	}

	@EventHandler
	public void onChatName(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (player.getPersistentDataContainer().has(key, PersistentDataType.BYTE)) {
			event.setCancelled(true);
			String display = ChatColor.translateAlternateColorCodes('&',
					event.getMessage().replaceAll(event.getFormat(), ""));
			ContainerUtil.getByDisplay(display).ifPresentOrElse(crate -> {
				player.sendMessage(StringUtil.getPrefix() + ChatColor.RED
						+ "There's already a crate with this name! Please enter a new name.");
			}, () -> {
				player.getPersistentDataContainer().remove(key);
				
				if(display.equalsIgnoreCase("cancel")) {
					player.sendMessage(StringUtil.getPrefix() + ChatColor.GREEN + "You successfully cancelled this process.");
					return;
				}
				
				Bukkit.getScheduler().runTask(TxCrates.getInstance(), () -> new CreateCrateMenu(player, display));
				player.sendMessage(StringUtil.getPrefix() + ChatColor.GREEN + "Creating a new crate called " + display);
			});
		}
	}

	@EventHandler
	public void onChatDel(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (player.getPersistentDataContainer().has(delKey, PersistentDataType.STRING)) {
			event.setCancelled(true);
			String display = player.getPersistentDataContainer().get(delKey, PersistentDataType.STRING);
			ContainerUtil.getByDisplay(display).ifPresentOrElse(crate -> {
				if (ChatColor.stripColor(display).equals(event.getMessage().replaceAll(event.getFormat(), ""))) {
					ContainerUtil.removeContainer(crate);
					crate.getFile().delete();
					player.sendMessage(
							StringUtil.getPrefix() + ChatColor.GREEN + "You successfully deleted this crate.");
				} else {
					player.sendMessage(StringUtil.getPrefix() + ChatColor.RED
							+ "Names did not match! Exiting deletion, please enter the GUI to try again.");
				}
			}, () -> player.sendMessage(StringUtil.getPrefix() + ChatColor.DARK_RED
					+ "Sorry! I couldn't find that crate! Please report this or try again."));
			player.getPersistentDataContainer().remove(delKey);
		}
	}

	@EventHandler
	public void onChatRename(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (player.getPersistentDataContainer().has(renameKey, PersistentDataType.STRING)) {
			event.setCancelled(true);
			String oldDisplay = player.getPersistentDataContainer().get(renameKey, PersistentDataType.STRING);
			ContainerUtil.getByDisplay(oldDisplay).ifPresentOrElse(crate -> {
				String display = event.getMessage().replaceAll(event.getFormat(), "");
				
				if(display.equalsIgnoreCase("cancel")) {
					player.sendMessage(StringUtil.getPrefix() + ChatColor.GREEN + "You successfully cancelled this process.");
					return;
				}
				crate.setDisplay(display);
				player.sendMessage(
						StringUtil.getPrefix() + ChatColor.GREEN + "You successfully renamed this crate \nfrom: "
								+ oldDisplay + ChatColor.GREEN + " \nto: " + display);
			}, () -> player.sendMessage(StringUtil.getPrefix() + ChatColor.DARK_RED
					+ "Sorry! I couldn't find that crate! Please report this or try again."));
			player.getPersistentDataContainer().remove(renameKey);
		}
	}
}