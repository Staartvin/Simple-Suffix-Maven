package me.Staartvin.SimpleSuffix;

import java.util.ArrayList;
import java.util.List;

import me.Staartvin.SimpleSuffix.Commands.*;
import me.Staartvin.SimpleSuffix.Config.Config;
import me.Staartvin.SimpleSuffix.Events.SSChatListener;
import me.Staartvin.SimpleSuffix.Permissions.PermissionsHandler;
import me.Staartvin.SimpleSuffix.Sqlite.Database;
import me.Staartvin.SimpleSuffix.Sqlite.SqLite;
import me.Staartvin.SimpleSuffix.VaultPackage.VaultHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public class SimpleSuffix extends JavaPlugin {

	public VaultHandler vaultHandler; 
	public Logger logger;
	public Config config; 
	public SqLite sqLite; 
	public Commands commands; 
	public PermissionsHandler permHandler; 
	
	@Override
	public void onEnable() {
		
		vaultHandler = new VaultHandler(this);
		logger = new Logger(this);
		config = new Config(this);
		sqLite = new SqLite(this);
		commands = new Commands(this);
		permHandler = new PermissionsHandler(this);
		
		getServer().getPluginManager().registerEvents(new SSChatListener(this), this);
		
		config.loadConfiguration();
		vaultHandler.setupChat();
		if (vaultHandler.getVault() != null) {
			if (VaultHandler.chat == null) {
				logger.logNormal("Permissions plugin does not support chat!");
				
			}
			logger.logNormal("Using Permissions plugin for chat!");
			logger.logVerbose("Hooked into Vault v" + vaultHandler.vault.getDescription().getVersion() + " successfully!");
		}
		permHandler.setupPermissions();
		getCommand("suffix").setExecutor(new SuffixCommands(this));
		getCommand("prefix").setExecutor(new PrefixCommands(this));
		
		logger.logNormal("Simple Suffix v" + getDescription().getVersion() + " has been enabled!");
	}
	@Override
	public void onDisable() {
		logger.logNormal("Simple Suffix v" + getDescription().getVersion() + " has been disabled!");
	}
	
	public boolean hasPermission(String permission, CommandSender sender) {
		if (!sender.hasPermission(permission)) {
			sender.sendMessage(ChatColor.RED + "You cannot do this! You need (" + permission + ") to do this!");
			return false;
		}
		return true;
	}
	
	public void setupDatabase() {
		try {
			getDatabase().find(Database.class).findRowCount();
		} catch (Exception e) {
			logger.logNormal("Installing Database for the first time!");
			installDDL();
		}
	}
	
    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(Database.class);
        return list;
    }
}
