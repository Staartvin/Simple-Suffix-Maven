package me.staartvin.simplesuffix.permissions;

import me.staartvin.simplesuffix.SimpleSuffix;
import me.staartvin.simplesuffix.sqlite.Database;
import me.staartvin.simplesuffix.vault.VaultHandler;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class PermissionsHandler{

	SimpleSuffix plugin;
	
	public PermissionsHandler(SimpleSuffix instance) {
		plugin = instance;
	}
	
	public void setupPermissions() {
		if (useDatabase()) {
			plugin.setupDatabase();
			plugin.logger.logNormal("Using database because permissions plugin doesn't provide chat support!");
		}
	}
	
	public boolean useDatabase() {
		if (plugin.vaultHandler.getVault() != null) {
			if (VaultHandler.chat == null) {
				return true;
			}
		}
		return false;
	}
	
	public String getPrefix(Player player){ 
		if (useDatabase()) {
			Database prefix = plugin.sqLite.getPrefix(player.getName());
			if (prefix == null) return null;
			return prefix.getPrefix();
		}
		return plugin.vaultHandler.getPlayerPrefix(player);
	}
	
	public String getSuffix(Player player){ 
		if (useDatabase()) {
			Database suffix = plugin.sqLite.getSuffix(player.getName());
			if (suffix == null) return null;
			return suffix.getSuffix();
		}
		return plugin.vaultHandler.getPlayerSuffix(player);
	}
	
	public void setGlobalPrefix(Player player, String prefix){ 
		if (useDatabase()) {
			Database dprefix = plugin.sqLite.getPrefix(player.getName());
			if (dprefix == null) {
				dprefix = new Database();
				dprefix.setPlayerName(player.getName());
				dprefix.setPrefix(prefix);
				dprefix.setSuffix("");
				plugin.getDatabase().save(dprefix);
				return;
			}
			dprefix.setPrefix(prefix);
			plugin.getDatabase().save(dprefix);
			return;
		}
		plugin.vaultHandler.setGlobalPlayerPrefix(player, prefix);
	}
	
	public void setGlobalSuffix(Player player, String suffix){ 
		if (useDatabase()) {
			Database dsuffix = plugin.sqLite.getSuffix(player.getName());
			if (dsuffix == null) {
				dsuffix = new Database();
				dsuffix.setPlayerName(player.getName());
				dsuffix.setSuffix(suffix);
				dsuffix.setPrefix("");
				plugin.getDatabase().save(dsuffix);
				return;
			}
			dsuffix.setSuffix(suffix);
			plugin.getDatabase().save(dsuffix);
			return;
		}
		plugin.vaultHandler.setGlobalPlayerSuffix(player, suffix);
	}
	
	public void setWorldSuffix(Player player, String suffix, World world){ 
		if (useDatabase()) {
			Database dsuffix = plugin.sqLite.getSuffix(player.getName());
			if (dsuffix == null) {
				dsuffix = new Database();
				dsuffix.setPlayerName(player.getName());
				dsuffix.setSuffix(suffix);
				dsuffix.setPrefix("");
				plugin.getDatabase().save(dsuffix);
				return;
			}
			dsuffix.setSuffix(suffix);
			plugin.getDatabase().save(dsuffix);
			return;
		}
		plugin.vaultHandler.setWorldPlayerSuffix(player, suffix, world);
	}
	
	public void setWorldPrefix(Player player, String prefix, World world){ 
		if (useDatabase()) {
			Database dprefix = plugin.sqLite.getPrefix(player.getName());
			if (dprefix == null) {
				dprefix = new Database();
				dprefix.setPlayerName(player.getName());
				dprefix.setPrefix(prefix);
				dprefix.setSuffix("");
				plugin.getDatabase().save(dprefix);
				return;
			}
			dprefix.setPrefix(prefix);
			plugin.getDatabase().save(dprefix);
			return;
		}
		plugin.vaultHandler.setWorldPlayerPrefix(player, prefix, world);
	}
	
	public void clearGlobalSuffix(Player player){ 
		if (useDatabase()) {
			Database dsuffix = plugin.sqLite.getSuffix(player.getName());
			if (dsuffix == null) {
				dsuffix = new Database();
				dsuffix.setPlayerName(player.getName());
				dsuffix.setSuffix("");
				dsuffix.setPrefix("");
				plugin.getDatabase().save(dsuffix);
				return;
			}
			dsuffix.setSuffix("");
			plugin.getDatabase().save(dsuffix);
			return;
		}
		plugin.vaultHandler.clearGlobalSuffix(player.getName());
	}
	
	public void clearWorldPrefix(Player player, String world){ 
		if (useDatabase()) {
			Database dprefix = plugin.sqLite.getPrefix(player.getName());
			if (dprefix == null) {
				dprefix = new Database();
				dprefix.setPlayerName(player.getName());
				dprefix.setPrefix("");
				dprefix.setSuffix("");
				plugin.getDatabase().save(dprefix);
				return;
			}
			dprefix.setPrefix("");
			plugin.getDatabase().save(dprefix);
			return;
		}
		plugin.vaultHandler.clearWorldPrefix(player.getName(), world);
	}
	
	public void clearWorldSuffix(Player player, String world){ 
		if (useDatabase()) {
			Database dsuffix = plugin.sqLite.getSuffix(player.getName());
			if (dsuffix == null) {
				dsuffix = new Database();
				dsuffix.setPlayerName(player.getName());
				dsuffix.setSuffix("");
				dsuffix.setPrefix("");
				plugin.getDatabase().save(dsuffix);
				return;
			}
			dsuffix.setSuffix("");
			plugin.getDatabase().save(dsuffix);
			return;
		}
		plugin.vaultHandler.clearWorldSuffix(player.getName(), world);
	}
	
	public void clearGlobalPrefix(Player player){ 
		if (useDatabase()) {
			Database dprefix = plugin.sqLite.getPrefix(player.getName());
			if (dprefix == null) {
				dprefix = new Database();
				dprefix.setPlayerName(player.getName());
				dprefix.setPrefix("");
				dprefix.setSuffix("");
				plugin.getDatabase().save(dprefix);
				return;
			}
			dprefix.setPrefix("");
			plugin.getDatabase().save(dprefix);
			return;
		}
		plugin.vaultHandler.clearGlobalPrefix(player.getName());
	}
}
