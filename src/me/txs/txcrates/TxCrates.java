package me.txs.txcrates;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.jereds.containerapi.objects.Container;
import me.jereds.containerapi.objects.ContainerHolder;
import me.jereds.containerapi.util.ContainerHolderUtil;
import me.txs.txcrates.commands.CommandClaimKeys;
import me.txs.txcrates.commands.CrateCommand;
import me.txs.txcrates.commands.CrateKeyAllCommand;
import me.txs.txcrates.commands.KeyCommand;
import me.txs.txcrates.commands.TestHex;
import me.txs.txcrates.listeners.ChatListener;
import me.txs.txcrates.listeners.CrateBreakListener;
import me.txs.txcrates.listeners.CrateKeyListener;
import me.txs.txcrates.listeners.CratePlaceListener;
import me.txs.txcrates.listeners.InventoryListener;
import net.milkbowl.vault.economy.Economy;

public class TxCrates extends JavaPlugin {

	private final List<Supplier<Listener>> listeners = Arrays.asList(CrateBreakListener::new, CrateKeyListener::new,
			CratePlaceListener::new, InventoryListener::new, ChatListener::new);

	private static TxCrates plugin;

	public static TxCrates getInstance() {
		return plugin;
	}
	
	private static ContainerHolder holder;
	public static ContainerHolder getHolder() {
		return holder;
	}
	
	@Override
	public void onEnable() {
		plugin = this;
		setupEconomy();
		holder = new ContainerHolder(this, "Crates");

		File file = new File(getDataFolder() + "/Crates");
		if (!file.exists())
			file.mkdir();

		Arrays.stream(holder.getFolder().listFiles()).forEach(yml -> 
			new Container(holder, YamlConfiguration.loadConfiguration(yml).getString("display")));

		getCommand("crate").setExecutor(new CrateCommand());
		getCommand("crate").setTabCompleter(new CrateCommand());
		getCommand("cratekey").setExecutor(new KeyCommand());
		getCommand("testhex").setExecutor(new TestHex());
		getCommand("cratekeyall").setExecutor(new CrateKeyAllCommand());
		getCommand("claimkeys").setExecutor(new CommandClaimKeys());
		CommandClaimKeys.startScheduler();
		listeners.forEach(listener -> Bukkit.getPluginManager().registerEvents(listener.get(), this));
	}
	
	@Override
	public void onDisable() {
		holder.clearContainers();
		ContainerHolderUtil.removeContainerHolder(holder);
	}

	private static Economy economy;

	private void setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return;
		}
		economy = rsp.getProvider();
	}

	public static Economy getEconomy() {
		return economy;
	}
}
