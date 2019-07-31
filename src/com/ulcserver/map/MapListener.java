package com.ulcserver.map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.ulcserver.map.mapItem.Trap;

public class MapListener implements Listener 
{
	@EventHandler
	public void onmove(PlayerMoveEvent event)
	{
		for(Trap t:Trap.list)
		{
			if(t.willCast(event))
				t.cast();
		}
	}
}
