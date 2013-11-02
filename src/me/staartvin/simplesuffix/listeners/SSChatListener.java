package me.staartvin.simplesuffix.listeners;

import me.staartvin.simplesuffix.SimpleSuffix;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

@SuppressWarnings("deprecation")
public class SSChatListener implements Listener {

	SimpleSuffix plugin;

	public SSChatListener(SimpleSuffix instance) {
		plugin = instance;
	}

	@EventHandler
	public void onChat(PlayerChatEvent event) {
		if (!plugin.permHandler.useDatabase())
			return;

		String prefix;
		String suffix;
		
		if (plugin.permHandler.getPrefix(event.getPlayer()) == null) {
			prefix = "";
		}
		else {
			prefix = plugin.config.replaceText(
					plugin.getConfig().getString("predefined prefix"),
					plugin.permHandler.getPrefix(event.getPlayer()));	
		}
		if (plugin.permHandler.getSuffix(event.getPlayer()) == null) {
			suffix = "";
		}
		else {
			suffix = plugin.config.replaceText(
					plugin.getConfig().getString("predefined suffix"),
					plugin.permHandler.getSuffix(event.getPlayer()));	
		}
			
		String format = plugin.config.getFormat();
		format = format.replace("%prefix%", prefix)
				.replace("%suffix%", suffix)
				.replace("%player%", event.getPlayer().getName())
				.replace("%message%", event.getMessage());
		format = replaceColours(format);
		
		event.setFormat(format);
	}
	
	protected String replaceColours(String message) {
		return message.replaceAll("(?i)&([a-f0-9])", "\u00A7$1");
	}
}
