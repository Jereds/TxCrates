package me.txs.txcrates.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import me.jereds.containerapi.util.ContainerUtil;
import me.txs.txcrates.objects.Crate;
import me.txs.txcrates.util.CrateUtil;
import me.txs.txcrates.util.NamespacedUtil;
import me.txs.txcrates.util.StringUtil;
import net.md_5.bungee.api.ChatColor;

public class CratePlaceListener implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (!CrateUtil.isCrate(event.getItemInHand()))
			return;
		ContainerUtil.fromItem(event.getItemInHand(), NamespacedUtil.getCrateItemTypeKey()).ifPresentOrElse(crate -> {
			new Crate(crate, event.getBlock()).create();
			event.getPlayer().sendMessage(StringUtil.getPrefix() + ChatColor.GREEN + "Successfully placed a " + crate.getDisplay());
		}, () -> {
			event.setCancelled(true);
			event.getPlayer().sendMessage(StringUtil.getPrefix() + ChatColor.RED + "Sorry! I believe this crate no longer exists.");
		});
	}
}
