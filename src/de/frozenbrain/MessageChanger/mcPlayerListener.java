package de.frozenbrain.MessageChanger;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class mcPlayerListener extends PlayerListener {
	
	private final mcPlugin plugin;
	
	public mcPlayerListener(mcPlugin plugin) {
		
		this.plugin = plugin;
		
	}
	
	public void onPlayerLogin(PlayerLoginEvent event) {
		switch(event.getResult()) {
		case KICK_BANNED:
			event.setKickMessage(plugin.getMessage("KICK_BANNED", event.getPlayer(), event.getKickMessage()));
			break;
		case KICK_WHITELIST:
			event.setKickMessage(plugin.getMessage("KICK_WHITELIST", event.getPlayer(), event.getKickMessage()));
			break;
		case KICK_FULL:
			event.setKickMessage(plugin.getMessage("KICK_FULL", event.getPlayer(), event.getKickMessage()));
			break;
		}
	}
	
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(plugin.getMessage("PLAYER_JOIN", event.getPlayer(), event.getJoinMessage()).equals("")) {
			event.setJoinMessage(null);
		} else {
			event.setJoinMessage(plugin.getMessage("PLAYER_JOIN", event.getPlayer(), event.getJoinMessage()));
		}
	}
	
	public void onPlayerKick(PlayerKickEvent event) {
		if(plugin.getMessage("KICK_KICK_LEAVEMSG", event.getPlayer(), event.getLeaveMessage()).equals("")) {
			event.setLeaveMessage(null);
		} else {
			event.setLeaveMessage(plugin.getMessage("KICK_KICK_LEAVEMSG", event.getPlayer(), event.getLeaveMessage()));
		}
		event.setReason(plugin.getMessage("KICK_KICK_REASON", event.getPlayer(), event.getReason()));
	}
	
	public void onPlayerQuit(PlayerQuitEvent event) {
		if(plugin.getMessage("PLAYER_QUIT", event.getPlayer(), event.getQuitMessage()).equals("")) {
			event.setQuitMessage(null);
		} else {
			event.setQuitMessage(plugin.getMessage("PLAYER_QUIT", event.getPlayer(), event.getQuitMessage()));
		}
	}
	
	
	
}
