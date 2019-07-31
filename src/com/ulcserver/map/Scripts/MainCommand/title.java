package com.ulcserver.map.Scripts.MainCommand;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.Handler;
import com.ulcserver.map.Scripts.ReadUtil;

public class title implements Handler
{

	@Override
	public boolean shouldHandle(String str) 
	{
		return str.startsWith("title");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(Executer l, String line) 
	{
		Location loc=ReadUtil.getLocation(line.split(" ")[1]);
		double r=Double.valueOf(line.split(" ")[2]);
		for(Player p:Executer.getNearByPlayerSet(loc, r))
			p.sendTitle(line.split(" ")[3].replaceAll("&","¡ì"),line.split(" ")[4].replaceAll("&","¡ì"));
	}

	@Override
	public boolean continueExecute() 
	{
		
		return true;
	}

}
