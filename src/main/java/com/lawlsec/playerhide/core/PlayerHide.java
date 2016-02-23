package com.lawlsec.playerhide.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerHide extends JavaPlugin implements Listener {
	public List<Player> ToggledPlayers;
	public List<Player> ToggledTags;
	
	@Override
	public void onEnable() {
		ToggledPlayers = new ArrayList<Player>();
		ToggledTags = new ArrayList<Player>();
		
		this.getCommand("toggleplayers").setExecutor(new PlayerCommandExecutor(this));
		this.getCommand("toggletags").setExecutor(new TagCommandExecutor(this));
	}
	
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		for (Player ps : ToggledPlayers){
			ps.hidePlayer(player);
		}
		
		for (Player ps : ToggledTags) {
			//Hide player tag from ps
		}
	}
}
