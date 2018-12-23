package com.onestian.deathvault;

import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class deathvault extends JavaPlugin {
	public static deathvault thisPlugin;
	FileConfiguration config = getConfig();
	
	@Override
	public void onEnable() {
		deathvault.thisPlugin = this;
		//Metrics
		@SuppressWarnings("unused")
		Metrics metrics = new Metrics(this);
		
		//Listener
		getServer().getPluginManager().registerEvents(new deathListener(), this);
		getServer().getPluginManager().registerEvents(new chestListener(), this);
		
	}
	
	@Override
	public void onDisable() {
		
	}

}
