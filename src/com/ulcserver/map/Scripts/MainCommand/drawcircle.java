package com.ulcserver.map.Scripts.MainCommand;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import com.github.PaintLib.MainClass.VectorGet;
import com.github.PaintLib.MainClass.Shapes.Circle;
import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.Handler;
import com.ulcserver.map.Scripts.ReadUtil;

public class drawcircle implements Handler
{

	@Override
	public boolean shouldHandle(String str) 
	{
		return str.startsWith("draw-circle");
	}

	@Override
	public void execute(Executer l, String line) 
	{
		
		Location loc=ReadUtil.getLocation(line.split(" ")[1]);
		double r=Double.valueOf(line.split(" ")[2]);
		double node=Double.valueOf(line.split(" ")[3]);
		Vector vector=ReadUtil.getVector(Executer.handleVec(line.split(" ")[4]));
		Particle particle=Particle.valueOf(line.split(" ")[5]);
		Circle c=new Circle(r,(int)node);
		c.calc();
		c.setVectorGet(new VectorGet(vector));
		for(Vector v:c.ChangeAllVector())
			loc.getWorld().spawnParticle(particle,loc.clone().add(v),0);	
	}

	@Override
	public boolean continueExecute() 
	{
		return true;
	}

}
