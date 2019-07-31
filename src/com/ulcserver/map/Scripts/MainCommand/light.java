package com.ulcserver.map.Scripts.MainCommand;

import org.bukkit.Location;

import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.Handler;
import com.ulcserver.map.Scripts.ReadUtil;

public class light implements Handler
{

	@Override
	public boolean shouldHandle(String str) 
	{
		return str.startsWith("light");
	}

	@Override
	public void execute(Executer l, String line) 
	{
		
		Location loc=ReadUtil.getLocation(line.split(" ")[1]);
		boolean flag=Boolean.valueOf(line.split(" ")[2]);
		if(flag)
			loc.getWorld().strikeLightning(loc);
		else 
			loc.getWorld().strikeLightningEffect(loc);
	}

	@Override
	public boolean continueExecute() 
	{
		return true;
	}

}
