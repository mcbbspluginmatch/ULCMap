package com.ulcserver.map.mapItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.ulcserver.map.Execute;
import com.ulcserver.map.Main;
import com.ulcserver.map.Scripts.Executer;

public class Trap extends AbstractTrap implements TrapInterface
{
	public static List<Trap> list=new ArrayList<Trap>();
	public Trap(Location loc,double radius,String c)
	{
		this.centerWorld=loc.getWorld().getName();
		this.centerX=loc.getX();
		this.centerY=loc.getY();
		this.centerZ=loc.getZ();
		this.radius=radius;
		this.cmdFile=c;
		this.trigger=Trigger.MOVE_IN;
		this.isCooling=false;
		this.coolDownTime=0;
		//list.add(this);
	}
	public Location getCenter()
	{
		return new Location(Bukkit.getWorld(this.centerWorld),this.centerX,this.centerY,this.centerZ);
	}
	public Trap(String world,double x,double y,double z,double radius,String c)
	{
		this.centerWorld=world;
		this.centerX=x;
		this.centerY=y;
		this.centerZ=z;
		this.radius=radius;
		this.cmdFile=c;
		this.trigger=Trigger.MOVE_IN;
		this.isCooling=false;
		this.coolDownTime=0;
		//list.add(this);
	}
	@Override
	public boolean isInTrap(Location loc) 
	{
		Location center=new Location(Bukkit.getWorld(this.centerWorld),this.centerX,this.centerY,this.centerZ);
		if(loc.getWorld().equals(center.getWorld()))
			return loc.distance(center)<radius;
		return false;
	}

	@Override
	public void cast()
	{
		Location center=new Location(Bukkit.getWorld(this.centerWorld),this.centerX,this.centerY,this.centerZ);
		if(coolDownTime==0)
		{
			Execute.executeFile(center,YamlConfiguration.loadConfiguration(new File(Main.getMain().getDataFolder(),"/scripts/"+cmdFile+".yml")), 0);
			return;
		}
		if(isCooling)
			return;
		isCooling=true;
		new Executer(center,0,0,YamlConfiguration.loadConfiguration(new File(Main.getMain().getDataFolder(),"/scripts/"+cmdFile+".yml"))).execute();;
		//Execute.executeFile(center,YamlConfiguration.loadConfiguration(new File(Main.getMain().getDataFolder(),"/scripts/"+cmdFile+".yml")), 0);
		new BukkitRunnable() 
		{
			
			@Override
			public void run() 
			{
				isCooling=false;
				cancel();
			}
		}.runTaskTimer(Main.getMain(),this.coolDownTime,0);
	}
	@Override
	public boolean willCast(PlayerMoveEvent event) 
	{
		if(this.trigger.equals(Trigger.MOVE_IN))
			return (!isInTrap(event.getFrom())) && isInTrap(event.getTo());
		if(this.trigger.equals(Trigger.MOVE_OUT))
			return (!isInTrap(event.getTo())) && isInTrap(event.getFrom());
		return false;
	}

}
