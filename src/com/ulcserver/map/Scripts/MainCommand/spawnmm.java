package com.ulcserver.map.Scripts.MainCommand;

import org.bukkit.Location;

import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.Handler;
import com.ulcserver.map.Scripts.ReadUtil;

import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.api.exceptions.InvalidMobTypeException;

public class spawnmm implements Handler
{

	@Override
	public boolean shouldHandle(String str) 
	{
		return str.startsWith("spawn-mm");
	}

	@Override
	public void execute(Executer l, String line) 
	{
		Location loc=ReadUtil.getLocation(line.split(" ")[1]);
		String name=line.split(" ")[2];
		try 
		{
			new BukkitAPIHelper().spawnMythicMob(name, loc);
		} catch (InvalidMobTypeException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean continueExecute() 
	{
		
		return true;
	}

}
