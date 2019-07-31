package com.ulcserver.map.mapItem;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

public interface TrapInterface 
{
	
	public boolean isInTrap(Location loc);
	public void cast();
	public boolean willCast(PlayerMoveEvent event);
}