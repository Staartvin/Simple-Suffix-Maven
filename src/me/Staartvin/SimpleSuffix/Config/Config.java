package me.Staartvin.SimpleSuffix.Config;

import java.util.Arrays;
import java.util.List;

import me.Staartvin.SimpleSuffix.SimpleSuffix;

public class Config {
	SimpleSuffix plugin;
	
	public Config(SimpleSuffix instance) {
		plugin = instance;
	}
	private String[] censoredWords = {"fuck", "shit", "porn"};
	
	public void loadConfiguration() {
		plugin.getConfig().options().header("SimpleSuffix v" + plugin.getDescription().getVersion() + " Config"
				+ "\n\nThe predefined suffix is the layout of the suffix."
				+ "\nIf a player would type '/suffix Example', the suffix would be [Example].");
		
		plugin.getConfig().addDefault("predefined suffix", "§f [%suffix%§f]");
		plugin.getConfig().addDefault("predefined prefix", "§f[%prefix%§f] ");
		plugin.getConfig().addDefault("chat format", "<%prefix%%player%%suffix%> %message%");
		plugin.getConfig().addDefault("character limit prefix", 20);
		plugin.getConfig().addDefault("character limit suffix", 20);
		plugin.getConfig().addDefault("censored words", Arrays.asList(censoredWords));
		plugin.getConfig().addDefault("verboselogging", true);
		plugin.getConfig().addDefault("debug", false);
		
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
	}
	
	public String replaceText(String message, String fix) {
		return message.replaceAll("%suffix%", fix).replaceAll("%prefix%", fix);
	}
	
	public String getFormat() {
		return plugin.getConfig().getString("chat format");
	}
	
	public List<String> getCensoredWords() {
		return plugin.getConfig().getStringList("censored words");
	}
}
