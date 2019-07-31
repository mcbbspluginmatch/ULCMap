package com.ulcserver.map.Scripts.MainCommand;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;

import com.github.PaintLib.MainClass.Shapes.Ball;
import com.ulcserver.map.Main;
import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.Handler;
import com.ulcserver.map.Scripts.ReadUtil;

public class doball implements Handler
{

	@Override
	public boolean shouldHandle(String str) 
	{
		return str.startsWith("do-ball");
	}

	@Override
	public void execute(Executer l, String line) 
	{
		Location loc=ReadUtil.getLocation(line.split(" ")[1]);
		double r=Double.valueOf(line.split(" ")[2]);
		double node=Double.valueOf(line.split(" ")[3]);
		String lineFile=line.split(" ")[4];
		Ball c=new Ball(r,(int)node);
		c.calc();
		for(Vector v:c.getList())
		{
			Location cloc=loc.clone().add(v);
			new Executer(cloc,l.timerCount,l.timerStep,YamlConfiguration.loadConfiguration(new File(Main.getMain().getDataFolder(),"/scripts/"+lineFile+".yml"))).execute();			
		}
	}

	@Override
	public boolean continueExecute() 
	{
		
		return true;
	}

}
