package com.ulcserver.map.Scripts;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ReadUtil
{
	public static Vector getOriginalVector(String loc)
	{
		String tmp=loc.replace("{","}");
		tmp=tmp.replace("}","");
		String[] r=tmp.split(";");
		return new Vector(Double.valueOf(r[0]), Double.valueOf(r[1]), Double.valueOf(r[2]))          ;
	}
	public static Vector getVector(String loc)
	{
		Vector vector=getOriginalVector(loc);
		if(vector.getX()==0 && vector.getY()==0 && vector.getZ()==0)
			return new Vector(new Random().nextDouble()-0.5,new Random().nextDouble()-0.5,new Random().nextDouble()-0.5).normalize();
		return vector;
	}
	public static Location getLocation(String loc)
	{
		String tmp=loc.replace("{","}");
		tmp=tmp.replace("}","");
		String[] r=tmp.split(";");
		return new Location(Bukkit.getWorld(r[0]), Double.valueOf(r[1]), Double.valueOf(r[2]), Double.valueOf(r[3]));
	}
	public static String getString(Location loc)
	{
		return new String("{"+loc.getWorld().getName()+";"+loc.getX()+";"+loc.getY()+";"+loc.getZ()+";"+"}");
	}
}
