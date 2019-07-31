
package com.ulcserver.map.Scripts.MainCommand;

import org.bukkit.Effect;
import org.bukkit.Location;
import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.Handler;
import com.ulcserver.map.Scripts.ReadUtil;

public class playeffect implements Handler
{

	@Override
	public boolean shouldHandle(String str) 
	{
		return str.startsWith("play-effect");
	}

	@Override
	public void execute(Executer l, String line) 
	{
		Location loc=ReadUtil.getLocation(line.split(" ")[1]);
		Effect type=Effect.valueOf(line.split(" ")[2]);
		loc.getWorld().playEffect(loc,type,256);
	}

	@Override
	public boolean continueExecute() 
	{
		
		return true;
	}

}