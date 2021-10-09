package me.txs.txcrates.util;

import java.util.List;
import java.util.stream.Collectors;

import me.jereds.pluginprefix.api.GradientManager;
import net.md_5.bungee.api.ChatColor;

public class StringUtil {

    public static String toColor(String str) {
    	return ChatColor.translateAlternateColorCodes('&', str);
    }
	
	public static List<String> toColor(List<String> str) {
		return str.stream().map(StringUtil::toColor).collect(Collectors.toList());
	}
	
	private final static String prefix;
	
	public static String getPrefix() {
		return ChatColor.GRAY + "[" + prefix + ChatColor.GRAY + "] ";
	}
	
	static {
		prefix = doPrefix();
	}
	
	private static String doPrefix() {
		String prefix = "TxCrates";
		String hexFrom = "#b45eff";
		String hexTo = "#21c4ff";
		return GradientManager.toGradient(prefix, hexFrom, hexTo);
	}
}
