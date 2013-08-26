package pgDev.bukkit.MessageChangerLite.messages;

import java.util.LinkedHashMap;

import org.bukkit.entity.Player;

import pgDev.bukkit.MessageChangerLite.MessageChangerLite;

public class ServerStopMessage extends Message {
	public static String fileLoc = messageDir + "/SERVER_STOP.cfg";
	
	static String bukkitShutdownMsg;

	static String defaultMessage;
	static LinkedHashMap<String, String> permMessages;
	
	public static String getMessage(Player receiver) {
		for (String perm : permMessages.keySet()) {
			if (receiver.hasPermission("perm")) {
				return replaceVars(permMessages.get(perm), receiver);
			}
		}
		return defaultMessage;
	}
	
	static String replaceVars(String msg, Player receiver) {
		return msg.replace("%default%", bukkitShutdownMsg)
				.replace("%receiverName%", receiver.getName())
				.replace("%receiverDisplayName%", receiver.getDisplayName());
	}
	
	@Override
	boolean load(MessageChangerLite plugin) {
		bukkitShutdownMsg = plugin.getServer().getShutdownMessage();
		
		
		
		return false;
	}
	
	@Override
	void generateFile() {
		
	}
}
