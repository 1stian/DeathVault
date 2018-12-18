package com.onestian.deathvault;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class chestListener implements Listener {
	
	@EventHandler
	public void deathChest (InventoryCloseEvent event) {
		
		if (event.getInventory().getType().equals(InventoryType.CHEST)) {
			Block bChest = event.getInventory().getLocation().getBlock();
			Chest chest = (Chest) bChest.getLocation().getBlock().getState();
			
			
			if (chest.getCustomName() != null) {
				if (chest.getCustomName().contains("'s death chest!")) {					
					if (checkInv(true, chest)) {						
						if (event.getInventory().getHolder() instanceof Chest) {
							Chest sChest = (Chest)event.getInventory().getHolder();
							sChest.getBlock().setType(Material.AIR);
							deathvault.thisPlugin.getLogger().info(chest.getCustomName() + " removed!");
						}
						if (event.getInventory().getHolder() instanceof DoubleChest) {
							DoubleChest dChest = (DoubleChest)event.getInventory().getHolder();
							Chest left = (Chest)dChest.getLeftSide();
							Chest right = (Chest)dChest.getRightSide();
							left.getBlock().setType(Material.AIR);
							right.getBlock().setType(Material.AIR);
							
							deathvault.thisPlugin.getLogger().info(chest.getCustomName() + " removed!");
						}
					}
				}
			}
		}
	}
	
	public Boolean checkInv(boolean result, Chest chest) {
		
		for (ItemStack items : chest.getInventory().getContents()) {
			if(items != null) {
				result = false;
			}
		}
		return result;
	}
}
