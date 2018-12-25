package com.onestian.deathvault;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.data.type.Chest.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class deathListener implements Listener {
	
	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		
		String chestID;
		
		org.bukkit.block.data.type.Chest chestData1;
		org.bukkit.block.data.type.Chest chestData2;
		
		if (event.getEntityType() == EntityType.PLAYER) {
			org.bukkit.block.Chest bChest;
			
			Player player = (Player) event.getEntity();
			Location loc = player.getLocation();
			
			final List<ItemStack> content = new ArrayList<ItemStack>();
			
			//Grabbing items from dead player!
			for (final ItemStack item : event.getDrops()) {
				content.add(item);
			}
			
			final ItemStack[] items = content.toArray(new ItemStack[content.size()]);
			
			//Getting location for the chest.
			final double x = loc.getX();
			
			final Location x1 = loc.clone();
			final Location x2 = loc.clone();
			
			x2.setX(x + 1);
			
			Block block1 = x1.getBlock();
			Block block2 = x2.getBlock();
			
			//Placing chests
			int itemAmount = items.length;
			//Single chest
			if (itemAmount < 27) {
				block1.setType(Material.CHEST);
			}
			//Double Chest
			else {
				block1.setType(Material.CHEST);
				block2.setType(Material.CHEST);
				
				Chest chest1 = (Chest) block1.getState();
				Chest chest2 = (Chest) block2.getState();
				
				chestData1 = (org.bukkit.block.data.type.Chest) chest1.getBlockData();
				chestData2 = (org.bukkit.block.data.type.Chest) chest2.getBlockData();
				
				chestData1.setType(Type.LEFT);
				block1.setBlockData(chestData1, true);
				chestData2.setType(Type.RIGHT);
				block2.setBlockData(chestData2, true);
			}
			
			//Updating the chest
			int ident = randomInt(1000, 9999);
			bChest = (org.bukkit.block.Chest) x1.getBlock().getState();
			bChest.setCustomName(player.getName() + "'s death chest! @" + ident);
			bChest.update();
			
			//Adding chests to list
			
			
			//Adding items to chest
			Bukkit.getScheduler().runTaskLaterAsynchronously(deathvault.thisPlugin, new Runnable(){

				@Override
				public void run() {
					for (final ItemStack item : items) {
						org.bukkit.block.Chest bChest = (org.bukkit.block.Chest) x1.getBlock().getState();
						bChest.getInventory().addItem(item);
					}					
				}
				
			}, 10);
			
			//Clearing the drops. So it won't drop on the ground.
			event.getDrops().clear();
			
			//Sending messages to player. With chest location as well.
			messageSender.messagePlayer("You died.. Get to your chest! Quick! Before someone else gets it!", player);
			messageSender.messagePlayer("Coordinates for your chest: x: " + ChatColor.RED + loc.getBlockX() + 
					ChatColor.GREEN + " y: " + ChatColor.RED + loc.getBlockY() + ChatColor.GREEN + " z: " + ChatColor.RED + loc.getBlockZ(), player);
		}
	}
	
	private static int randomInt(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

}
