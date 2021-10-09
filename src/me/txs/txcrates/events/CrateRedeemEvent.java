package me.txs.txcrates.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import me.jereds.txcontainerapi.api.objects.Container;

public class CrateRedeemEvent extends Event implements Cancellable {
	
	private final Container container;
	private final Player player;
	private ItemStack prize;
	public CrateRedeemEvent(Container container, Player player, ItemStack prize) {
		this.container = container;
		this.player = player;
		this.prize = prize;
	}

	public Container getCrate() {
		return container;
	}

	public Player getPlayer() {
		return player;
	}

	public ItemStack getPrize() {
		return prize;
	}
	
	public void setPrize(ItemStack prize) {
		this.prize = prize;
	}

	private boolean cancel = false;
	
	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

	@Override
	public HandlerList getHandlers() {
		return new HandlerList();
	}
}
