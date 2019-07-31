package com.ulcserver.map.Scripts.MainCommand;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import com.ulcserver.map.Main;
import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.Handler;
import com.ulcserver.map.Scripts.ReadUtil;

public class timer implements Handler
{

	@Override
	public boolean shouldHandle(String str) 
	{
		return str.startsWith("timer");
	}

	@Override
	public void execute(Executer l, String line) 
	{
		Location loc=ReadUtil.getLocation(line.split(" ")[1]);
		double time=Double.valueOf(line.split(" ")[2]);
		double interval=Double.valueOf(line.split(" ")[3]);
		double count=Double.valueOf(line.split(" ")[4]);
		new BukkitRunnable() 
		{
			int c=1;
			@Override
			public void run() 
			{
				if(c>count)
				{
					cancel();
					return;
				}
				c++;
				new Executer(loc.clone(),c,l.timerStep,YamlConfiguration.loadConfiguration(new File(Main.getMain().getDataFolder(),"/scripts/"+line.split(" ")[5]+".yml"))).execute();
			}
		}.runTaskTimer(Main.getMain(),(int)(time*20),(int)(interval*20));
	}

	@Override
	public boolean continueExecute() 
	{
		
		return true;
	}

}
