package me.txs.txcrates.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.txs.txcrates.util.CrateUtil;
import me.txs.txcrates.util.StringUtil;
import net.md_5.bungee.api.ChatColor;

public class CrateBreakListener implements Listener {

	@EventHandler
	public void onCrateBreak(BlockBreakEvent event) {
		if(!CrateUtil.isCrate(event.getBlock()))
			return;
		
		event.setCancelled(true);
		
		Player player = event.getPlayer();
		if(!player.hasPermission("TxCrates.break")) {
			player.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "You do not have permission to break this crate.");
			return;
		}
		
		if(!player.isSneaking()) {
			player.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "You must be sneaking to break a crate.");
			return;
		}
		
		event.getBlock().setType(Material.AIR);
		player.sendMessage(StringUtil.getPrefix() + ChatColor.GREEN + "You successfully broke this crate.");
	}
}
