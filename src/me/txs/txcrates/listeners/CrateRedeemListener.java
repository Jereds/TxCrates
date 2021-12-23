package me.txs.txcrates.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.txs.txcrates.events.CrateRedeemEvent;
import me.txs.txcrates.util.InventoryUtil;

public class CrateRedeemListener implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onCrateRedeemed(CrateRedeemEvent event) {
		var container = event.getCrate();
		var player = event.getPlayer();
		var prize = event.getPrize();
		
		
		
		InventoryUtil.addItem(player, prize);
	}
}
