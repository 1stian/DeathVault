package com.onestian.deathvault;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class messageSender {	
	
	public static void messagePlayer(String msg, Player player) {
		//Player ply = deathvault.thisPlugin.getServer().getPlayer(player);
		player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GOLD + "DeathVault" + ChatColor.AQUA + "] " + ChatColor.GREEN + msg);
	}

}
