package com.onestian.deathvault;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class deathListener implements Listener {
	
	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		
		String ply = null;
		World playerWorld = null;
		Location deathLoc = null;
		Block locBlock = null;
		
		if (event.getEntityType() == EntityType.PLAYER) {
			ply = event.getEntity().getName();
			Player pl = deathvault.thisPlugin.getServer().getPlayer(ply);
			playerWorld = pl.getWorld();
			deathLoc = event.getEntity().getLocation();
			
			List<ItemStack> iStack = event.getDrops();
			ItemStack[] i2ForChest = iStack.toArray(new ItemStack[0]);			
			
			event.getDrops().removeAll(iStack);
			
			int x = deathLoc.getBlockX();
			int y = deathLoc.getBlockY();
			int z = deathLoc.getBlockZ();
			
			
			Location deathChest = new Location(playerWorld, x, y, z);
			locBlock = deathChest.getBlock();
			
			locBlock.setType(Material.CHEST);
			
			Chest chest = (Chest) locBlock.getState();
			Inventory cInv = chest.getInventory();
			cInv.setContents(i2ForChest);
			
			
			messageSender.messagePlayer("Chest has been spawned with your items, where you died.", ply);
			messageSender.messagePlayer("You died.. Get to your chest! Quick! Before someone else gets it!", ply);
		}
	}
}
