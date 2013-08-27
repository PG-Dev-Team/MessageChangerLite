package pgDev.bukkit.MessageChangerLite.messages;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.logging.Level;

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
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(messageDir)));
			out.write("#\r\n");
			out.write("# Server-Stop Message Configuration\r\n");
			out.write("#\r\n");
			out.write("# Format:\r\n");
			out.write("#	<permission>=<message>\r\n");
			out.write("#\r\n");
			out.write("# Variables:\r\n");
			out.write("#	%default% -> The default message of the server\r\n");
			out.write("#	%receiverName% -> The username of the player receiving \r\n");
			out.write("this message\r\n");
			out.write("#	%receiverDisplayName% -> The display name of the \r\n");
			out.write("player receiving this message\r\n");
			out.write("\r\n");
			out.write("default=Server is shutting down...\r\n");
			out.write("\r\n");
			out.write("admin=Your server is shutting down!\r\n");
			out.write("vip=Sorry! Server going down for maintenance.\r\n");
		} catch (Exception e) {
			MessageChangerLite.logger.log(Level.SEVERE, "Could not generate Server-Stop Message Configuration", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					MessageChangerLite.logger.log(Level.SEVERE, "Could not close Server-Stop Message Configuration write stream", e);
				}
			}
		}
	}
}
