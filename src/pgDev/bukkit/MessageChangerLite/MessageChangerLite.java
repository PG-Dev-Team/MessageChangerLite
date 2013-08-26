package pgDev.bukkit.MessageChangerLite;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import pgDev.bukkit.MessageChangerLite.listeners.MCLMainListener;

public class MessageChangerLite extends JavaPlugin {
	// Plugin data directory
	public static String mainDir = "./plugins/MessageChangerLite";
	
	// Bukkit logger
	public static Logger logger;
	
	// Event Listener
	MCLMainListener mainListener;
	
	@Override
	public void onLoad() {
		logger = getLogger();
	}
	
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() {
		
	}
}
