package com.ulcserver.map.Scripts.Preloads;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.Preload;
import com.ulcserver.map.Scripts.ReadUtil;

public class mark implements Preload
{

	@Override
	public boolean needHandle(String str) 
	{
		return true;
	}

	@Override
	public String handleScript(Executer cmder,String cmd) 
	{
		Player player=Executer.getNearByPlayer(cmder.executeLocation);
		String sloc=Executer.getString(player.getLocation());
		String orig=cmd.replaceAll("@p",sloc);
		Entity entity=Executer.getNearByEntity(cmder.executeLocation);
		sloc=ReadUtil.getString(entity.getLocation());
		orig=orig.replaceAll("@e",sloc);
		LivingEntity entity1=Executer.getNearByLivingEntity(cmder.executeLocation);
		sloc=ReadUtil.getString(entity1.getLocation());
		orig=orig.replaceAll("@le",sloc);
		sloc=ReadUtil.getString(cmder.executeLocation);
		orig=orig.replaceAll("@l",sloc);
		orig=orig.replaceAll("@t",String.valueOf(cmder.timerCount));
		orig=orig.replaceAll("@st",String.valueOf(cmder.timerCount*cmder.timerStep));
		return orig;
	}

}
