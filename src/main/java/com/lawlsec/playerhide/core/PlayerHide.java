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
	
	/**
	 * Generates a team name for the specified player 
	 * in the format PH_PLAYERNAME to maximize compatibility
	 * 
	 * @param  p The player to generate the team name for
	 * @return Generated team name string
	 */
	public String generateTeamName(Player p) {
		String TeamName = String.format("PH_%s", p.getName());
		TeamName = TeamName.length() > 16 ? TeamName.substring(0, 16) : TeamName;
		return TeamName;
	}
	
	/**
	 * Assigns the PlayerHide team to the specified player
	 * 
	 * @param p The player to which we are assigning the team
	 */
	private void setPlayerTeam(Player p) {
		Team team;
		
		String TeamName = generateTeamName(p);
		
		if (p.getScoreboard() == null) {
			ScoreboardManager manager = Bukkit.getScoreboardManager();
			Scoreboard board = manager.getNewScoreboard();
			team = board.registerNewTeam(TeamName);
		} else {
			if (p.getScoreboard().getTeam(TeamName) == null)
				team = p.getScoreboard().registerNewTeam(TeamName);
			else
				team = p.getScoreboard().getTeam(TeamName);
		}
		
		team.addEntry(p.getName());
	}
	
	@Override
	public void onEnable() {
		ToggledPlayers = new ArrayList<Player>();
		ToggledTags = new ArrayList<Player>();
		
		getServer().getPluginManager().registerEvents(this, this);
		
		this.getCommand("toggleplayers").setExecutor(new PlayerCommandExecutor(this));
		this.getCommand("toggletags").setExecutor(new TagCommandExecutor(this));
		
		for (Player ps : Bukkit.getOnlinePlayers()){
			setPlayerTeam(ps);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		setPlayerTeam(player);
		
		for (Player ps : ToggledPlayers){
			ps.hidePlayer(player);
		}
	}
}
