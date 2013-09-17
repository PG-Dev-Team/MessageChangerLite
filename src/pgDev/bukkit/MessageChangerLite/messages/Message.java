package pgDev.bukkit.MessageChangerLite.messages;

import pgDev.bukkit.MessageChangerLite.MessageChangerLite;

public abstract class Message {
	public static String messageDir = MessageChangerLite.mainDir + "/messages";
	
	/**
	 * Loads up the configured message
	 * @param plugin The main plugin
	 */
	abstract void load(MessageChangerLite plugin) throws Exception;
	
	/**
	 * Generates a new file for message configuration
	 */
	abstract void generateFile();
}
