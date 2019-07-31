package com.ulcserver.map.Scripts.MainCommand;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

import com.ulcserver.map.Main;
import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.Handler;
import com.ulcserver.map.Scripts.ReadUtil;
import com.zeus.pathfinder.AI.AIItem.WatchNearByPlayerAIItem;
import com.zeus.pathfinder.Entity.MyEntity;

public class spawnmodel implements Handler
{

	@Override
	public boolean shouldHandle(String str) 
	{
		return str.startsWith("spawn-model");
	}

	@Override
	public void execute(Executer l, String line) 
	{
		Location loc=ReadUtil.getLocation(line.split(" ")[1]);
		int time=Integer.valueOf(line.split(" ")[2]).intValue();
		EntityType type=EntityType.valueOf(line.split(" ")[3]);
		MyEntity entity=new MyEntity(type,loc);
		try 
		{
			entity.spawnEntity();
			entity.getAIManager().killAI();
			entity.getAttributeManager().setKnockBackResistance(100);
			entity.getAIManager().addAI(new WatchNearByPlayerAIItem(entity.getBukkitEntity(),20),0);
			entity.getAttributeManager().setMaxHealth(20000);
		} catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) 
		{
			e.printStackTrace();
		}
		new BukkitRunnable()
		{
			
			@Override
			public void run() 
			{
				entity.getBukkitEntity().remove();
				cancel();
			}
		}.runTaskTimer(Main.getMain(),time*20,0);			
	}

	@Override
	public boolean continueExecute() 
	{
		
		return true;
	}

}
