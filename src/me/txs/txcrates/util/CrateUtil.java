package me.txs.txcrates.util;

import java.util.Arrays;
import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.jereds.containerapi.objects.Container;
import me.jereds.containerapi.util.ContainerUtil;
import net.md_5.bungee.api.ChatColor;

public class CrateUtil {

	public static Optional<Container> fromId(String id) {
		return ContainerUtil.getAllContainers().stream()
				.filter(crate -> crate.getDisplay() != null)
				.filter(crate -> crate.getId().equals(id)).findFirst();
	}
	
	public static Optional<Container> fromBlock(Block block) {
		if (!isCrate(block))
			return null;
		return fromId(((TileState) block.getState()).getPersistentDataContainer()
				.get(NamespacedUtil.getCrateBlockTypeKey(), PersistentDataType.STRING));
	}

	public static boolean isCrate(ItemStack item) {
		if (item == null || item.getType() == Material.AIR)
			return false;
		return item.getItemMeta().getPersistentDataContainer().has(NamespacedUtil.getCrateItemTypeKey(),
				PersistentDataType.STRING);
	}

	public static boolean isCrate(Block block) {
		if (block == null || !(block.getState() instanceof TileState))
			return false;
		return ((TileState) block.getState()).getPersistentDataContainer().has(NamespacedUtil.getCrateBlockTypeKey(),
				PersistentDataType.STRING);
	}
	
	public static ItemStack asItem(Material mat, Container container) {
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(container.getDisplay());
		meta.setLore(Arrays.asList(ChatColor.YELLOW + "Place this to create a crate."));
		meta.getPersistentDataContainer().set(NamespacedUtil.getCrateItemTypeKey(), PersistentDataType.STRING, container.getId());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getCrateKey(Container container) {
		ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(container.getDisplay() + ChatColor.WHITE + " Key");
		meta.setLore(Arrays.asList(ChatColor.GREEN + "Right click on a " + container.getDisplay(),
				ChatColor.GREEN + "to get a random prize!"));
		meta.getPersistentDataContainer().set(NamespacedUtil.getCrateItemTypeKey(), PersistentDataType.STRING, container.getId());
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		return item;
	}

	public static boolean isCrateKey(ItemStack item) {
		if (item == null || item.getType() != Material.TRIPWIRE_HOOK)
			return false;
		return item.getItemMeta().getPersistentDataContainer().has(NamespacedUtil.getCrateItemTypeKey(),
				PersistentDataType.STRING);
	}

	public static Optional<Container> fromKey(ItemStack item) {
		return ContainerUtil.getById(item.getItemMeta().getPersistentDataContainer()
				.get(NamespacedUtil.getCrateItemTypeKey(), PersistentDataType.STRING));
	}
}
