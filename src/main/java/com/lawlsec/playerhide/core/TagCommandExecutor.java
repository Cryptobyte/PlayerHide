package com.lawlsec.playerhide.core;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Team;

public class TagCommandExecutor implements CommandExecutor {
	private final PlayerHide plugin;

	public TagCommandExecutor(PlayerHide plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
				
			if (player.hasPermission("PlayerHide.ToggleTags")) {
				if (plugin.ToggledTags.contains(player)) {
					for (Player ps : Bukkit.getOnlinePlayers()) {
						if (ps != player) {
							Team team = player.getScoreboard().getTeam(plugin.generateTeamName(ps));
								
							if (team.getNameTagVisibility() != NameTagVisibility.ALWAYS)
								team.setNameTagVisibility(NameTagVisibility.ALWAYS);
						}
					}
							
					plugin.ToggledTags.remove(player);
					player.sendMessage("Tags now unhidden!");
				} else {
					for (Player ps : Bukkit.getOnlinePlayers()) {
						if (ps != player) {
							Team team = player.getScoreboard().getTeam(plugin.generateTeamName(ps));
								
							if (team.getNameTagVisibility() != NameTagVisibility.NEVER)
								team.setNameTagVisibility(NameTagVisibility.NEVER);
						}
					}
						
					plugin.ToggledTags.add(player);
					player.sendMessage("Tags now hidden!");
				}
			}
				
			return true;
				
		} else sender.sendMessage("Tags can only be toggled by a player.");
		
		return false;
	}
}