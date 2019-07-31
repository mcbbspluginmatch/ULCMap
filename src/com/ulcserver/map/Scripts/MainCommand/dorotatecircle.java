package com.ulcserver.map.Scripts.MainCommand;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;

import com.github.PaintLib.MainClass.VectorGet;
import com.github.PaintLib.MainClass.Shapes.Circle;
import com.ulcserver.map.Main;
import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.Handler;
import com.ulcserver.map.Scripts.ReadUtil;

public class dorotatecircle implements Handler
{

	@Override
	public boolean shouldHandle(String str) 
	{
		return str.startsWith("do-rotate-circle");
	}

	@Override
	public void execute(Executer l, String line) 
	{
		Location loc=ReadUtil.getLocation(line.split(" ")[1]);
		double r=Double.valueOf(line.split(" ")[2]);
		double node=Double.valueOf(line.split(" ")[3]);
		Vector vector=ReadUtil.getVector(Executer.handleVec(line.split(" ")[4]));
		String lineFile=line.split(" ")[5];
		double rotatecount=Double.valueOf(line.split(" ")[6]);
		double angle=Double.valueOf(line.split(" ")[7]);
		Circle c=new Circle(r,(int)node);
		c.calc();
		c.rotateY(rotatecount*angle);
		c.setVectorGet(new VectorGet(vector));
		for(Vector v:c.ChangeAllVector())
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