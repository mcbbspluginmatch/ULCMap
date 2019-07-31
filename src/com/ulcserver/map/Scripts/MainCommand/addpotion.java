package com.ulcserver.map.Scripts.MainCommand;

import org.bukkit.Location;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ulcserver.map.Util;
import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.Handler;
import com.ulcserver.map.Scripts.ReadUtil;

public class addpotion implements Handler
{

	@Override
	public boolean shouldHandle(String str) 
	{
		return str.startsWith("addpotion");
	}

	@Override
	public void execute(Executer l, String line) 
	{
		Location loc=ReadUtil.getLocation(line.split(" ")[1]);
		double r=Double.valueOf(line.split(" ")[2]);
		PotionEffectType type=PotionEffectType.getByName(line.split(" ")[3]);
		double level=Double.valueOf(line.split(" ")[4]);
		double time=Double.valueOf(line.split(" ")[5]);
		Util.addPotionToAround(loc,r,new PotionEffect(type,(int)(time*20),(int)level));
	}

	@Override
	public boolean continueExecute() 
	{
		
		return true;
	}

}