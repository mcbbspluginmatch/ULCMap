package com.ulcserver.map.Scripts.MainCommand;

import org.bukkit.Location;

import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.Handler;
import com.ulcserver.map.Scripts.ReadUtil;

public class explode implements Handler
{

	@Override
	public boolean shouldHandle(String str)
	{
		return str.startsWith("explode");
	}

	@Override
	public void execute(Executer l, String line)
	{
		Location loc=ReadUtil.getLocation(line.split(" ")[1]);
		loc.getWorld().createExplosion(loc,Float.valueOf(line.split(" ")[2]));
	}

	@Override
	public boolean continueExecute() 
	{
		return true;
	}
	
}
