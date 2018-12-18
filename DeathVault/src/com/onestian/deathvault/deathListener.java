package com.onestian.deathvault;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class deathListener implements Listener {
	
	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		
		if (event.getEntityType() == EntityType.PLAYER) {
			
			Player player = deathvault.thisPlugin.getServer().getPlayer(event.getEntity().getName());
			Location loc = player.getLocation();
			
			final List<ItemStack> content = new ArrayList<ItemStack>();
			final ItemStack[] items = content.toArray(new ItemStack[content.size()]);
			
			//Grabbing items from dead player!
			for (final ItemStack item : event.getDrops()) {
				content.add(item);
				deathvault.thisPlugin.getLogger().info(item.toString());
			}
			
			//Placing chests on death location
			final double x = loc.getX();
			
			final Location x1 = loc.clone();
			final Location x2 = loc.clone();
			
			x2.setX(x + 1);
			
			for (final Location cLoc : new Location[] {x1,x2}) {
				cLoc.getBlock().setType(Material.CHEST);
			}
			
			Chest chest = (Chest) x1.getBlock().getState();
			chest.setCustomName(player.getName() + "'s death chest!");
			//chest.setLock("123");
			chest.update(true, true);
			
			deathvault.thisPlugin.getLogger().info(player.getLocation().getBlock().toString());
			
			messageSender.messagePlayer("You died.. Get to your chest! Quick! Before someone else gets it!", player);
		}
	}
}
