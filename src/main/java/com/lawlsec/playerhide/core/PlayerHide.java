package com.lawlsec.playerhide.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public final class PlayerHide extends JavaPlugin implements Listener {
	public List<Player> ToggledPlayers;
	public List<Player> ToggledTags;
	
	@Override
	public void onEnable() {
		ToggledPlayers = new ArrayList<Player>();
		ToggledTags = new ArrayList<Player>();
		
		getServer().getPluginManager().registerEvents(this, this);
		
		this.getCommand("toggleplayers").setExecutor(new PlayerCommandExecutor(this));
		this.getCommand("toggletags").setExecutor(new TagCommandExecutor(this));
		
		for (Player ps : Bukkit.getOnlinePlayers()){
			ScoreboardManager manager = Bukkit.getScoreboardManager();
			Scoreboard board = manager.getNewScoreboard();
			Team team = board.registerNewTeam(ps.getName());
			team.addPlayer(ps);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Team team = board.registerNewTeam(player.getName());
		team.addPlayer(player);
		
		for (Player ps : ToggledPlayers){
			ps.hidePlayer(player);
		}
	}
}
