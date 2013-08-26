package pgDev.bukkit.MessageChangerLite.messages;

import pgDev.bukkit.MessageChangerLite.MessageChangerLite;

public abstract class Message {
	public static String messageDir = MessageChangerLite.mainDir + "/messages";
	
	/**
	 * Loads up the configured message
	 * @param plugin The main plugin
	 * @return True is successful, false otherwise
	 */
	abstract boolean load(MessageChangerLite plugin);
	
	/**
	 * Generates a new file for message configuration
	 */
	abstract void generateFile();
}
