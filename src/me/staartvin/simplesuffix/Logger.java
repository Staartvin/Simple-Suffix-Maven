package me.staartvin.simplesuffix;


public class Logger {
	
	// Maven?
	SimpleSuffix plugin;
	
	protected Logger(SimpleSuffix instance) {
		plugin = instance;
	}
	
	public void logNormal(String message){
		System.out.print("[Simple Suffix] " + message);
	}
	
	public boolean logVerbose(String message){
		if (plugin.getConfig().getBoolean("verboselogging")) {
			System.out.print("[Simple Suffix] " + message);
			return true;
		}
		return false;
	}
	
	public boolean debug(String message){
		if (plugin.getConfig().getBoolean("debug")) {
			System.out.print("[Simple Suffix DEBUG] " + message);
			return true;
		}
		return false;
	}
}
