package com.ulcserver.map.Scripts.MainCommand;

import org.bukkit.scheduler.BukkitRunnable;

import com.ulcserver.map.Main;
import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.Handler;

public class sleep implements Handler
{

	@Override
	public boolean shouldHandle(String str) 
	{
		return str.startsWith("sleep");
	}

	@Override
	public void execute(Executer cmder,String line) 
	{
		double time=Double.valueOf(line.split(" ")[1]);
		new BukkitRunnable() 
		{
				
			@Override
			public void run() 
			{
				Executer newOne=new Executer(cmder.executeLocation,cmder.timerCount,cmder.timerStep,cmder.scripts,cmder.line+1,cmder.end);
				newOne.execute();
				cancel();
			}
		}.runTaskTimer(Main.getMain(),(int)(time*20),0);
	}

	@Override
	public boolean continueExecute() 
	{
		return false;
	}

}
