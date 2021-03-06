package com.lawlsec.playerhide.core;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCommandExecutor implements CommandExecutor {
	private final PlayerHide plugin;
	
	public PlayerCommandExecutor(PlayerHide plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
				
			if (player.hasPermission("PlayerHide.TogglePlayers")) {
				if (plugin.ToggledPlayers.contains(player)) {
					for (Player ps : Bukkit.getOnlinePlayers()) {
						if (ps != player)
								player.showPlayer(ps);
					}
					
					plugin.ToggledPlayers.remove(player);
					player.sendMessage("Players now unhidden!");
				} else {
					for (Player ps : Bukkit.getOnlinePlayers()) {
						if (ps != player)
							player.hidePlayer(ps);
					}
					
					plugin.ToggledPlayers.add(player);
					player.sendMessage("Players now hidden!");
				}
			}
				
			return true;
				
		}
		
		sender.sendMessage("Players can only be toggled by a player.");
		
		return false;
	}
}