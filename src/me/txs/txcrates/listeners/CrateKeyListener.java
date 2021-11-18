package me.txs.txcrates.listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.jereds.containerapi.guis.EditContainerGUI;
import me.jereds.containerapi.objects.Container;
import me.txs.txcrates.events.CrateRedeemEvent;
import me.txs.txcrates.util.CrateUtil;
import me.txs.txcrates.util.InventoryUtil;
import me.txs.txcrates.util.ItemUtil;
import me.txs.txcrates.util.StringUtil;
import net.md_5.bungee.api.ChatColor;

public class CrateKeyListener<T> implements Listener {

	@EventHandler
	public void onClickCrate(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (!CrateUtil.isCrateKey(event.getItem())) {
			if (CrateUtil.isCrate(event.getClickedBlock())) {
				if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					event.setCancelled(true);
					CrateUtil.fromBlock(event.getClickedBlock()).ifPresentOrElse(crate -> {
						
						if(player.hasPermission("txcrates.admin.bypass")) {
							player.sendMessage(StringUtil.getPrefix() + ChatColor.GREEN + "Opening crate editor.");
							new EditContainerGUI(player, crate);
							return;
						}
						
						player.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "You must have a key to open this "
								+ crate.getDisplay() + "!\n " + ChatColor.RED
								+ "You can left click this crate to view the prizes.");
						
					}, () -> {
						player.sendMessage(
								StringUtil.getPrefix() + ChatColor.RED + "Sorry! This crate no longer exists.");
					});

				} else if (event.getAction() == Action.LEFT_CLICK_BLOCK && !player.isSneaking()) {
					CrateUtil.fromBlock(event.getClickedBlock()).ifPresentOrElse(crate -> {
						new EditContainerGUI(player, crate);
					}, () -> {
						player.sendMessage(
								StringUtil.getPrefix() + ChatColor.RED + "Sorry! This crate no longer exists.");
					});
				}
			}
			return;
		}

		if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		event.setCancelled(true);

		if (!CrateUtil.isCrate(event.getClickedBlock())) {
			player.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "You must right click a "
					+ CrateUtil.fromKey(event.getItem()).get().getDisplay() + ChatColor.RED + " with this key.");
			return;
		}

		Container container = CrateUtil.fromBlock(event.getClickedBlock()).get();

		if (!container.getId().equals(CrateUtil.fromKey(event.getItem()).get().getId())) {
			player.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "This is the wrong crate! Your key is for a "
					+ CrateUtil.fromKey(event.getItem()).get().getDisplay());
			return;
		}

		if (InventoryUtil.invIsFull(player.getInventory())) {
			player.sendMessage(
					StringUtil.getPrefix() + ChatColor.RED + "You cannot open a crate with a full inventory.");
			return;
		}

		ItemStack prize = null;
		try {
			prize = getRandomItem(container);
		} catch (Exception e) {
			player.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "Sorry! This crate seems to have no prizes.");
			return;
		}
		
		event.getItem().setAmount(event.getItem().getAmount() - 1);
		
		var crateEvent = new CrateRedeemEvent(container, event.getClickedBlock(), player, prize);
		Bukkit.getPluginManager().callEvent(crateEvent);
		if(crateEvent.isCancelled()) {
			return;
		}

		InventoryUtil.addItem(player, crateEvent.getPrize());
	}
	
	public ItemStack getRandomItem(Container container) {
		var items = container.getItems(false);
		items.removeIf(ItemUtil::isFiller);
		return items.get(new Random().nextInt(items.size()));
	}
}
