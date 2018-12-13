package com.onestian.deathvault;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.Chest;

public class deathListener implements Listener {
	
	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		
		String ply = null;
		World playerWorld = null;
		Location deathLoc = null;
		Block locBlock = null;
		
		if (event.getEntityType() == EntityType.PLAYER) {
			ply = event.getEntity().getName();
			playerWorld.getWorldFolder().getName();
			deathLoc = event.getEntity().getLocation();
			
			Player pl = deathvault.thisPlugin.getServer().getPlayer(ply);
			
			PlayerInventory inv = (PlayerInventory) pl.getInventory();
			ItemStack[] iStack = inv.getContents();
			
			int x = deathLoc.getBlockX();
			int y = deathLoc.getBlockY();
			int z = deathLoc.getBlockZ();
			
			y++;
			
			Location deathChest = new Location(playerWorld, x, y, z);
			locBlock = (Block) deathChest;
			
			locBlock.setType(Material.CHEST);
			
			if (locBlock instanceof Chest) {
				Chest chest = (Chest) locBlock.getState();
				Inventory cInv = ((HumanEntity) chest).getInventory();
				cInv.setContents(iStack);
			}
			
			pl.sendMessage("[DeathVault] Chest has been spawned with your items, where you died.");
			pl.sendMessage("[DeathVault] You died.. Get to your chest! Quick! Before someone else gets it!");
		}
	}
}
