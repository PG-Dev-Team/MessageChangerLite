package pgDev.bukkit.MessageChangerLite.messages;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;

import pgDev.bukkit.MessageChangerLite.MessageChangerLite;

public class PlayerJoinMessage extends Message {
	public static String fileLoc = messageDir + "/PLAYER_JOIN.cfg";

	@Override
	void load(MessageChangerLite plugin) throws Exception {
		// TODO Auto-generated method stub
		
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
