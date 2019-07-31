package com.ulcserver.map.Scripts.MainCommand;
import org.bukkit.Location;
import org.bukkit.Particle;

import org.bukkit.util.Vector;

import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.Handler;
import com.ulcserver.map.Scripts.ReadUtil;

public class particle implements Handler
{

	@Override
	public boolean shouldHandle(String str) 
	{
		return str.startsWith("particle");
	}

	@Override
	public void execute(Executer l, String line) 
	{
		Location loc=ReadUtil.getLocation(line.split(" ")[1]);
		Vector vector=ReadUtil.getVector(Executer.handleVec(line.split(" ")[2]));
		double num=Double.valueOf(line.split(" ")[3]);
		Particle particle=Particle.valueOf(line.split(" ")[4]);
		loc.getWorld().spawnParticle(particle,loc,(int) num,vector.getX(),vector.getY(),vector.getZ());
	}

	@Override
	public boolean continueExecute() 
	{
		
		return true;
	}

}