package com.ulcserver.map.Scripts.MainCommand;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import com.ulcserver.map.Main;
import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.Handler;
import com.ulcserver.map.Scripts.ReadUtil;

public class wait implements Handler
{

	@Override
	public boolean shouldHandle(String str) 
	{
		return str.startsWith("wait");
	}

	@Override
	public void execute(Executer l, String line) 
	{
		Location loc=ReadUtil.getLocation(line.split(" ")[1]);
		double time=Double.valueOf(line.split(" ")[2]);
		new BukkitRunnable() 
		{
			@Override
			public void run() 
			{
				new Executer(loc.clone(),l.timerCount,l.timerStep,YamlConfiguration.loadConfiguration(new File(Main.getMain().getDataFolder(),"/scripts/"+line.split(" ")[3]+".yml"))).execute();
				cancel();
			}
		}.runTaskTimer(Main.getMain(),(int)(time*20),0);
	}

	@Override
	public boolean continueExecute() 
	{
		
		return true;
	}

}