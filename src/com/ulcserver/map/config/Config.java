package com.ulcserver.map.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ulcserver.map.Main;
import com.ulcserver.map.mapItem.Trap;


public class Config 
{
	static public File locals=new File(Main.getMain().getDataFolder(),"/traps.yml");
	static public void CheckFiles() throws IOException
	{
		if(!Main.getMain().getDataFolder().exists())
			Main.getMain().getDataFolder().mkdirs();
		if(!locals.exists())
		{
			locals.createNewFile();
			example();
		}	
	}
	public static void example() throws IOException
	{
		Trap.list.add(new Trap("world",0,0,0,10,"example"));
		saveAll();
		File example=new File(Main.getMain().getDataFolder(),"/scripts/example.yml");
		File dirFile=new File(Main.getMain().getDataFolder(),"/scripts");
		if(!dirFile.exists())
			dirFile.mkdirs();
		if(!example.exists())
			example.createNewFile();
		FileConfiguration game=YamlConfiguration.loadConfiguration(example);
		List<String> cmdList=new ArrayList<String>();
		cmdList.add(new String("draw-seal @l 2.0 {0;1;0} FLAME"));
		cmdList.add(new String("spawn-model @l 10 EVOKER"));
		cmdList.add(new String("do-orig-command example"));
		game.set("Script",cmdList);
		List<String> orig=new ArrayList<String>();
		orig.add(new String("killall mobs"));
		game.set("Command",orig);
		game.save(example);
	}
	public static void reload()
	{
		readAll();
	}
	public static void readAll()
	{
		Trap.list.clear();
		FileConfiguration game=YamlConfiguration.loadConfiguration(locals);
		int count=game.getInt("Size");
		for(int i=1;i<=count;i++)
			Trap.list.add(read(String.valueOf(i)));
	}
	public static Trap read(String path)
	{
		FileConfiguration game=YamlConfiguration.loadConfiguration(locals);
		Trap trap=new Trap("world",0,0,0,10,"example");
		trap.centerWorld=game.getString(path+".loc.world");
		trap.centerX=game.getDouble(path+".loc.x");
		trap.centerY=game.getDouble(path+".loc.y");
		trap.centerZ=game.getDouble(path+".loc.z");
		trap.radius=game.getDouble(path+".Radius");
		trap.coolDownTime=game.getInt(path+".CoolDownTime");
		trap.trigger=game.getString(path+".Trigger");
		trap.cmdFile=game.getString(path+".Script");
		return trap;
	}
	public static void saveAll() throws IOException
	{
		int count=Trap.list.size();
		FileConfiguration game=YamlConfiguration.loadConfiguration(locals);
		game.set("Size",count);
		for(int i=0;i<count;i++)
		{
			String path=String.valueOf(i+1);
			Trap t=Trap.list.get(i);
			game.set(path+".loc.world",t.centerWorld);
			game.set(path+".loc.x",t.centerX);
			game.set(path+".loc.y",t.centerY);
			game.set(path+".loc.z",t.centerZ);
			game.set(path+".Trigger",t.trigger);
			game.set(path+".CoolDownTime",t.coolDownTime);
			game.set(path+".Script",t.cmdFile);
			game.set(path+".Radius",t.radius);
			game.save(locals);
		}
		game.save(locals);
	}
	public static void saveLocation(Location loc,String path) throws IOException
	{
		FileConfiguration game=YamlConfiguration.loadConfiguration(locals);
		game.set(path+".world",loc.getWorld().getName());
		game.set(path+".x",loc.getX());
		game.set(path+".y",loc.getY());
		game.set(path+".z",loc.getZ());
		game.save(locals);
	}
}
