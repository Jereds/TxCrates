package me.txs.txcrates.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import me.jereds.containerapi.objects.Container;

public class CrateRedeemEvent extends Event implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	private final Container container;
	private final Player player;
	private ItemStack prize;
	private Block block;
	public CrateRedeemEvent(Container container, Block block, Player player, ItemStack prize) {
		super();
		this.container = container;
		this.player = player;
		this.block = block;
		this.prize = prize;
	}

	public Container getCrate() {
		return container;
	}
	
	public Block getBlock() {
		return block;
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
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
