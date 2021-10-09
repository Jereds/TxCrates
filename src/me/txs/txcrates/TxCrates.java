package me.txs.txcrates;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.jereds.txcontainerapi.api.ContainerUtil;
import me.jereds.txcontainerapi.api.objects.Container;
import me.txs.txcrates.commands.CrateCommand;
import me.txs.txcrates.commands.CrateKeyAllCommand;
import me.txs.txcrates.commands.CratesCommand;
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
	
	@Override
	public void onEnable() {
		plugin = this;
		setupEconomy();

		File file = new File(getDataFolder() + "/crates");
		if (!file.exists())
			file.mkdir();

		Arrays.stream(new File(getDataFolder() + "/crates").listFiles()).forEach(yml -> {
			new Container(yml, YamlConfiguration.loadConfiguration(yml).getString("display"));
		});

		getCommand("crates").setExecutor(new CratesCommand());
		getCommand("crate").setExecutor(new CrateCommand());
		getCommand("crate").setTabCompleter(new CrateCommand());
		getCommand("cratekey").setExecutor(new KeyCommand());
		getCommand("testhex").setExecutor(new TestHex());
		getCommand("cratekeyall").setExecutor(new CrateKeyAllCommand());
		listeners.forEach(listener -> Bukkit.getPluginManager().registerEvents(listener.get(), this));
	}
	
	@Override
	public void onDisable() {
		List<Container> containersToRemove = new ArrayList<>();
		ContainerUtil.getAllContainers().stream()
			.filter(container -> container != null)
			.filter(container -> container.getFile().getAbsolutePath().startsWith(getDataFolder().getAbsolutePath()))
			.forEach(containersToRemove::add);
		
		containersToRemove.forEach(ContainerUtil::removeContainer);
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