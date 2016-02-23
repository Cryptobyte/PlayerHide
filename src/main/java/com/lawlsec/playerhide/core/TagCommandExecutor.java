package com.lawlsec.playerhide.core;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagCommandExecutor implements CommandExecutor {
	private final PlayerHide plugin;

	public TagCommandExecutor(PlayerHide plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("toggletags")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				
				if (player.hasPermission("PlayerHide.ToggleTags")) {
					if (plugin.ToggledTags.contains(player)) {
						//Show Tags
					
						plugin.ToggledTags.remove(player);
						player.sendMessage("Tags now unhidden!");
					} else {
						//Hide Tags
					
						plugin.ToggledTags.add(player);
						player.sendMessage("Tags now hidden!");
					}
				}
				
				return true;
				
			} else sender.sendMessage("Tags can only be toggled by a player.");
		}
		
		return false;
	}
}