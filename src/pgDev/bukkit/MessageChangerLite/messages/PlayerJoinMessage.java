package pgDev.bukkit.MessageChangerLite.messages;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import pgDev.bukkit.MessageChangerLite.MessageChangerLite;

public class PlayerJoinMessage extends Message {
	public static String fileLoc = messageDir + "/PLAYER_JOIN.cfg";

	static Map<String, Map<String, String>> permMessages;
	
	public static String getMessage(Player joiner, Player receiver, String defaultMessage) {
		Map<String, String> receiverMessages = permMessages.get("default");
		for (String perm : permMessages.keySet()) {
			if (!perm.equals("default")) {
				if (joiner.hasPermission(MessageChangerLite.msgRootPerm + perm)) {
					receiverMessages = permMessages.get(perm);
				}
			}
		}
		
		String msg = receiverMessages.get("default");
		for (String perm : receiverMessages.keySet()) {
			if (!perm.equals("default")) {
				if (receiver.hasPermission(MessageChangerLite.msgRootPerm + perm)) {
					msg = receiverMessages.get(perm);
				}
			}
		}
		
		return msg.replace("%none%", "")
				.replace("%default%", defaultMessage)
				.replace("%joinedName%", joiner.getName())
				.replace("%joinedDisplayName%", joiner.getDisplayName())
				.replace("%joinedWorld%", joiner.getWorld().getName())
				.replace("%receiverName%", receiver.getName())
				.replace("%receiverDisplayName%", receiver.getDisplayName())
				.replace("%receiverWorld%", receiver.getWorld().getName());
	}
	
	@Override
	void load(MessageChangerLite plugin) throws Exception {
		permMessages = new LinkedHashMap<String, Map<String, String>>();
		
		Map<String, String> curMap = new LinkedHashMap<String, String>();
		permMessages.put("default", curMap);
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileLoc));
			
			String line = null;
			int lineNum = 0;
			while ((line = br.readLine().trim()) != null) {
				lineNum++;
				
				if (!(line.equals("") || line.startsWith("#"))) {
					if (line.endsWith(":")) {
						curMap = permMessages.get(line.substring(0, line.length() - 1));
						if (curMap == null) {
							curMap = new LinkedHashMap<String, String>();
							permMessages.put(line.substring(0, line.length() - 1), curMap);
						}
					} else {
						String[] parts = line.split("=", 2);
						if (parts.length == 2) {
							curMap.put(parts[0].toLowerCase(), parts[1]);
						} else {
							MessageChangerLite.logger.log(Level.WARNING, "There was an error found in the Player Join Message configuration on line: " + lineNum);
						}
					}
				}
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}
		
		// Check the data
		for (Map.Entry<String, Map<String, String>> joinerData : permMessages.entrySet()) {
			if (!joinerData.getValue().containsKey("default")) {
				MessageChangerLite.logger.log(Level.SEVERE, "No default message was found in the Player Join Message configuration for the \"" + joinerData.getKey() + "\" joiner permission");
				throw new UnsupportedOperationException();
			}
		}
	}

	@Override
	void generateFile() {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileLoc)));
			out.write("#\r\n");
			out.write("# Player-Join Message Configuration\r\n");
			out.write("#\r\n");
			out.write("# Format:\r\n");
			out.write("#	<joinerPem>:\r\n");
			out.write("#	<receiverPerm>=<message>\r\n");
			out.write("#\r\n");
			out.write("# Variables:\r\n");
			out.write("#	%none# -> No join message\r\n");
			out.write("#	%default% -> The default join message for this player\r\n");
			out.write("#	%joinedName% -> The username of the player who joined the server\r\n");
			out.write("#	%joinedDisplayName% -> The display name of the player who joined the server\r\n");
			out.write("#	%joinedWorld% -> The name of the world of the player joined into\r\n");
			out.write("#	%receiverName% -> The username of the player receiving this message\r\n");
			out.write("#	%receiverDisplayName% -> The display name of the player receiving this message\r\n");
			out.write("#	%receiverWorld% -> The name of the world the player receiving this message is in\r\n");
			out.write("#\r\n");
			out.write("# Note:\r\n");
			out.write("#	Every joinerPerm section needs a default message\r\n");
			out.write("\r\n");
			out.write("default:\r\n");
			out.write("default=%joinedDisplayName% has just joined the best server in the world!\r\n");
			out.write("admin=%joinedName% has joined your server\r\n");
			out.write("\r\n");
			out.write("admin:\r\n");
			out.write("default=The almighty %joinedDisplayName% has joined\r\n");
			out.write("admin=A fellow admin %joinedName% has joined\r\n");
			out.write("\r\n");
			out.write("vip:\r\n");
			out.write("default=VIP %joinedDisplayName% has just joined the best server in the world!\r\n");
			out.write("admin=VIP %joinedName% has joined your server\r\n");
		} catch (Exception e) {
			MessageChangerLite.logger.log(Level.SEVERE, "Could not generate Player-Join Message Configuration", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					MessageChangerLite.logger.log(Level.SEVERE, "Could not close Player-Join Message Configuration write stream", e);
				}
			}
		}
	}

}
