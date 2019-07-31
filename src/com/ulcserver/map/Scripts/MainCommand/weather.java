package com.ulcserver.map.Scripts.MainCommand;

import org.bukkit.Bukkit;

import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.Handler;

public class weather implements Handler
{

	@Override
	public boolean shouldHandle(String str) 
	{
		
		return str.startsWith("weather");
	}

	@Override
	public void execute(Executer l, String line) 
	{
		
		double dur=Double.valueOf(line.split(" ")[1]);
		String name=line.split(" ")[2];
		Bukkit.getWorld(name).setWeatherDuration((int) dur);
	}

	@Override
	public boolean continueExecute()
	{
		return true;
	}

}
