package com.ulcserver.map.Scripts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.ulcserver.map.Main;


public class Executer 
{
	protected static final ArrayList<Preload> preloads=new ArrayList<Preload>();
	protected static final ArrayList<Handler> handlers=new ArrayList<Handler>();
	public final Location executeLocation;
	public final int timerCount,start,end;
	public int line;
	public double timerStep;
	public final FileConfiguration scripts;
	public static String getString(Location loc)
	{
		return new String("{"+loc.getWorld().getName()+";"+loc.getX()+";"+loc.getY()+";"+loc.getZ()+";"+"}");
	}
	public static void spawnModel(Location loc,int Time,EntityType type)
	{
		
	}
	public static List<Player> getNearByPlayerSet(Location loc,double r)
	{
		List<Player> targetPlayer=new ArrayList<Player>();
		for(Player p:loc.getWorld().getPlayers())
		{
			if(r>p.getLocation().distance(loc))
				targetPlayer.add(p);
		}
		return targetPlayer;
	}
	public static String handleVec(String vec)
	{
		String ans=vec;
		if(vec.startsWith("@v"))
		{
			ans=vec.replaceAll("@v", "");
			Location v1=ReadUtil.getLocation(ans.split(",")[0]);
			Location v2=ReadUtil.getLocation(ans.split(",")[1]);
			Vector a=v2.subtract(v1).toVector();
			return "{"+a.getX()+";"+a.getY()+";"+a.getZ()+"}";
		}
		return ans;
	}
	public static Player getNearByPlayer(Location loc)
	{
		Player targetPlayer=null;
		double dis=2147483647;
		for(Player p:loc.getWorld().getPlayers())
		{
			if(dis>p.getLocation().distance(loc))
			{
				dis=p.getLocation().distance(loc);
				targetPlayer=p;
			}
		}
		return targetPlayer;
	}
	public static Entity getNearByEntity(Location loc)
	{
		Entity targetPlayer=null;
		double dis=2147483647;
		for(Entity p:loc.getWorld().getEntities())
		{
			if(dis>p.getLocation().distance(loc))
			{
				dis=p.getLocation().distance(loc);
				targetPlayer=p;
			}
		}
		return targetPlayer;
	}
	public static void registerHandler(Handler h)
	{
		Executer.handlers.add(h);
	}
	public static void registerPreloader(Preload h)
	{
		Executer.preloads.add(h);
	}
	public static LivingEntity getNearByLivingEntity(Location loc)
	{
		LivingEntity targetPlayer=null;
		double dis=2147483647;
		for(Entity p:loc.getWorld().getEntities())
		{
			if(p instanceof LivingEntity)
			{
				if(dis>p.getLocation().distance(loc))
				{
					dis=p.getLocation().distance(loc);
					targetPlayer=(LivingEntity) p;
				}
			}
		}
		return targetPlayer;
	}
	public Executer(Location loc,int c,double s,FileConfiguration file,int start,int end)
	{
		this.executeLocation=loc;
		this.scripts=file;
		this.timerCount=c;
		this.timerStep=s;
		this.line=0;
		this.end=end;
		this.start=start;
	}
	
	public List<String> getCmdList()
	{
		return this.scripts.getStringList("Script");
	}
	public Executer(Location loc,int c,double s,FileConfiguration file)
	{
		this.executeLocation=loc;
		this.scripts=file;
		this.timerCount=c;
		this.timerStep=s;
		this.line=0;
		this.start=1;
		this.end=this.getCmdList().size();
	}
	public Executer(Executer e)
	{
		this.executeLocation=e.executeLocation;
		this.scripts=e.scripts;
		this.timerCount=e.timerCount;
		this.timerStep=e.timerStep;
		this.line=e.line;
		this.end=e.end;
		this.start=e.start;
	}
	public void execute()
	{
		if(start>end)
			return;
		List<String> list=this.getCmdList();
		for(int i=start;i<=end;i++)
		{
			String cmd=list.get(i-1);
			this.line=i;
			for(Preload p:Executer.preloads)
				if(p.needHandle(cmd))
					cmd=p.handleScript(this, cmd);
			Main.getMain().getLogger().info("[OrigCommand]: "+cmd);
			for(Handler h:Executer.handlers)
				if(h.shouldHandle(cmd))
				{
					h.execute(this,cmd);
					if(!h.continueExecute())
						return;
				}
		}
	}
}
