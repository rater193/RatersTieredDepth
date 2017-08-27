package com.rater193.rtd;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

public class EventListener implements Listener {
	
	@SuppressWarnings("unused")
	private Plugin plugin;
	public HashMap<Material, Integer> toolDepths = new HashMap<Material, Integer>();
	public ArrayList<Material> targetedMaterials = new ArrayList<Material>();
	public ArrayList<String> ignoredPlayers = new ArrayList<String>();

	public EventListener(RatersTieredDepth plugin) {
		this.plugin = plugin;
		//Here we are adding the blocks we want this to take effect on
		targetedMaterials.add(Material.STONE);
		targetedMaterials.add(Material.DIRT);
		targetedMaterials.add(Material.GRASS);
		targetedMaterials.add(Material.SAND);
		targetedMaterials.add(Material.SANDSTONE);
		ignoredPlayers.add("[BuildCraft]");

		toolDepths.putIfAbsent(Material.WOOD_SPADE, 1);
		toolDepths.putIfAbsent(Material.WOOD_PICKAXE, 1);
		toolDepths.putIfAbsent(Material.STONE_SPADE, 2);
		toolDepths.putIfAbsent(Material.STONE_PICKAXE, 2);
		toolDepths.putIfAbsent(Material.IRON_SPADE, 3);
		toolDepths.putIfAbsent(Material.IRON_PICKAXE, 3);
		toolDepths.putIfAbsent(Material.GOLD_SPADE, 3);
		toolDepths.putIfAbsent(Material.GOLD_PICKAXE, 3);
		toolDepths.putIfAbsent(Material.DIAMOND_SPADE, 5);
		toolDepths.putIfAbsent(Material.DIAMOND_PICKAXE, 5);
	}
	
	@EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
		Debug.log("Player: " + event.getPlayer());
		Debug.log("Name: " + event.getPlayer().getName().toString());
		if(!ignoredPlayers.contains(event.getPlayer().getName().toString())) {
			if(event.getPlayer().getGameMode()!=GameMode.CREATIVE) {
				if(targetedMaterials.contains(event.getBlock().getType())) {
					int minHeight = getAverageMaxHeight(event.getBlock().getWorld(), event.getBlock().getX(), event.getBlock().getZ());
					minHeight -= 1;
					if(event.getBlock().getY() <= minHeight) {
						event.getPlayer().sendMessage("[TieredDepth] You need a stronger tool to dig deeper!");
						event.setCancelled(true);
					}
				}
			}
			Debug.log("Player");
		}else{
			Debug.log("Fake player");
		}
    }
	
	public int getMaxCompatibleHeightAtColumn(World world, int x, int z) {
		int ret = 0;
		for(int _y = 0; _y < 256; _y++) {
			if(targetedMaterials.contains(world.getBlockAt(x, _y, z).getType())) {
				ret = _y;
			}
		}
		return ret;
	}
	
	public int getAverageMaxHeight(World world, int x, int z) {
		int ret = 0;
		Integer[] numbers = {
				getMaxCompatibleHeightAtColumn(world, x-1, z-1),
				getMaxCompatibleHeightAtColumn(world, x, z-1),
				getMaxCompatibleHeightAtColumn(world, x+1, z-1),
				getMaxCompatibleHeightAtColumn(world, x-1, z),
				getMaxCompatibleHeightAtColumn(world, x, z),
				getMaxCompatibleHeightAtColumn(world, x+1, z),
				getMaxCompatibleHeightAtColumn(world, x-1, z+1),
				getMaxCompatibleHeightAtColumn(world, x, z+1),
				getMaxCompatibleHeightAtColumn(world, x+1, z+1)
		};
		
		//Averging the compatible heights
		for(int index = 0; index < numbers.length; index++) {
			ret += numbers[index];
		}
		ret = ret/numbers.length;
		
		return ret;
	}

}
