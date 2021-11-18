package me.txs.txcrates.commands;

import static org.bukkit.Material.BARREL;
import static org.bukkit.Material.BLACK_SHULKER_BOX;
import static org.bukkit.Material.BLAST_FURNACE;
import static org.bukkit.Material.BLUE_SHULKER_BOX;
import static org.bukkit.Material.BREWING_STAND;
import static org.bukkit.Material.BROWN_SHULKER_BOX;
import static org.bukkit.Material.CHEST;
import static org.bukkit.Material.CYAN_SHULKER_BOX;
import static org.bukkit.Material.DISPENSER;
import static org.bukkit.Material.DROPPER;
import static org.bukkit.Material.FURNACE;
import static org.bukkit.Material.GRAY_SHULKER_BOX;
import static org.bukkit.Material.GREEN_SHULKER_BOX;
import static org.bukkit.Material.HOPPER;
import static org.bukkit.Material.LIGHT_BLUE_SHULKER_BOX;
import static org.bukkit.Material.LIGHT_GRAY_SHULKER_BOX;
import static org.bukkit.Material.LIME_SHULKER_BOX;
import static org.bukkit.Material.MAGENTA_SHULKER_BOX;
import static org.bukkit.Material.ORANGE_SHULKER_BOX;
import static org.bukkit.Material.PINK_SHULKER_BOX;
import static org.bukkit.Material.PURPLE_SHULKER_BOX;
import static org.bukkit.Material.RED_SHULKER_BOX;
import static org.bukkit.Material.SHULKER_BOX;
import static org.bukkit.Material.SMOKER;
import static org.bukkit.Material.WHITE_SHULKER_BOX;
import static org.bukkit.Material.YELLOW_SHULKER_BOX;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.jereds.containerapi.objects.Container;
import me.jereds.containerapi.util.ContainerUtil;
import me.txs.txcrates.TxCrates;
import me.txs.txcrates.util.CrateUtil;
import me.txs.txcrates.util.InventoryUtil;
import me.txs.txcrates.util.StringUtil;
import net.md_5.bungee.api.ChatColor;

public class CrateCommand implements CommandExecutor, TabCompleter {

	private static List<Material> acceptables;
	static {
		acceptables = Arrays.asList(BARREL, BLAST_FURNACE, BREWING_STAND, CHEST, DISPENSER, DROPPER, FURNACE, HOPPER, SHULKER_BOX, SMOKER,
				RED_SHULKER_BOX,
				ORANGE_SHULKER_BOX,
				YELLOW_SHULKER_BOX,
				GREEN_SHULKER_BOX,
				BLUE_SHULKER_BOX,
				PURPLE_SHULKER_BOX,
				WHITE_SHULKER_BOX,
				BLACK_SHULKER_BOX,
				MAGENTA_SHULKER_BOX,
				LIGHT_BLUE_SHULKER_BOX,
				LIME_SHULKER_BOX,
				LIGHT_GRAY_SHULKER_BOX,
				GRAY_SHULKER_BOX,
				PINK_SHULKER_BOX,
				BROWN_SHULKER_BOX,
				CYAN_SHULKER_BOX);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player && sender.hasPermission("TxCrates.admin.crate"))) {
			sender.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "You lack permission to run this command!");
			return true;
		}

		// /crate <crate>
		if (args.length < 1) {
			sender.sendMessage(StringUtil.getPrefix() + ChatColor.RED
					+ "Incorrect format! Please use: /crate <material> <crate id>");
			return true;
		}

		Material mat;
		try {
			mat = Material.valueOf(args[0].toUpperCase());
			if (!acceptables.contains(mat)) {
				sender.sendMessage(StringUtil.getPrefix() + ChatColor.RED
						+ "Sorry! Can't use that material. It must be able to hold persistent data: " + args[0]);
				return true;
			}
		} catch (Exception e) {
			sender.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "I couldn't find material type: " + args[0]);
			return true;
		}

		var builder = new StringBuilder();
		for (int i = 1; i < args.length; i++) {
		    builder.append(args[i] + " ");
		}

		String id = builder.toString().trim();

		CrateUtil.fromId(id).ifPresentOrElse(crate -> {
			InventoryUtil.addItem((Player) sender, CrateUtil.asItem(mat, crate));
			sender.sendMessage(StringUtil.getPrefix() + ChatColor.GREEN + "Successfully received a "
					+ crate.getDisplay() + ChatColor.GREEN + "!");
		}, () -> sender.sendMessage(StringUtil.getPrefix() + ChatColor.RED + "That crate does not exist!"));
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (args.length == 1) {
			return acceptables.stream().map(mat -> mat.name().toLowerCase())
					.filter(str -> str.toLowerCase().startsWith(args[0].toLowerCase()) || str.trim().isEmpty())
					.collect(Collectors.toList());
		}
		return new ContainerUtil(TxCrates.getHolder()).getAllContainers().stream().map(Container::getId)
					.filter(str -> str.toLowerCase().startsWith(args[1].toLowerCase()) || str.trim().isEmpty())
					.collect(Collectors.toList());
	}
}
