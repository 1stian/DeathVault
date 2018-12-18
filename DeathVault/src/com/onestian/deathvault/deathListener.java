package com.onestian.deathvault;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import net.md_5.bungee.api.ChatColor;

public class deathListener implements Listener {
	
	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		
		if (event.getEntityType() == EntityType.PLAYER) {
			
			Player player = deathvault.thisPlugin.getServer().getPlayer(event.getEntity().getName());
			Location loc = player.getLocation();
			
			final List<ItemStack> content = new ArrayList<ItemStack>();
			
			//Grabbing items from dead player!
			for (final ItemStack item : event.getDrops()) {
				content.add(item);
				
				//Debug line
				//deathvault.thisPlugin.getLogger().info(item.toString());
			}
			
			final ItemStack[] items = content.toArray(new ItemStack[content.size()]);
			
			//Getting location for the chest.
			final double x = loc.getX();
			
			final Location x1 = loc.clone();
			final Location x2 = loc.clone();
			
			x2.setX(x + 1);
			
			
			//Placing chests
			for (final Location cLoc : new Location[] {x1,x2}) {
				cLoc.getBlock().setType(Material.CHEST);
			}
					
			//Updating the chest
			for (final Location cLoc : new Location[] {x1,x2}) {
				Chest chest = (Chest) cLoc.getBlock().getState();
				chest.setCustomName(player.getName() + "'s death chest!");
				chest.update(true, true);
			}
			
			//Adding items to chest
			Bukkit.getScheduler().runTaskLaterAsynchronously(deathvault.thisPlugin, new Runnable(){

				@Override
				public void run() {
					for (final ItemStack item : items) {
						Chest chest = (Chest) x1.getBlock().getState();
						chest.getInventory().addItem(item);
						
						//Debug line
						//deathvault.thisPlugin.getLogger().info(item.toString());
					}					
				}
				
			}, 10);
			
			//Clearing the drops. So it won't drop on the ground.
			event.getDrops().clear();
			
			//Debug line
			//deathvault.thisPlugin.getLogger().info(player.getLocation().getBlock().toString());
			
			//Sending messages to player. With chest location as well.
			messageSender.messagePlayer("You died.. Get to your chest! Quick! Before someone else gets it!", player);
			messageSender.messagePlayer("Coordinates for your chest: x: " + ChatColor.RED + loc.getBlockX() + 
					ChatColor.GREEN + " y: " + ChatColor.RED + loc.getBlockY() + ChatColor.GREEN + " z: " + ChatColor.RED + loc.getBlockZ(), player);
		}
	}
}
