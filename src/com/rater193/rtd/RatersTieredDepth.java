package com.rater193.rtd;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RatersTieredDepth extends JavaPlugin {

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		super.onEnable();
		Debug.log("Loading");
		Debug.log("Registering event listener");
		Bukkit.getPluginManager().registerEvents(new EventListener(this), this);
		Debug.log("Finished");
	}
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}

}
