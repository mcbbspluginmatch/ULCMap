package com.ulcserver.map;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import com.github.PaintLib.MainClass.VectorGet;
import com.github.PaintLib.MainClass.Shapes.Ball;
import com.github.PaintLib.MainClass.Shapes.Circle;
import com.github.PaintLib.MainClass.Shapes.Seal;
//import com.zeus.pathfinder.AI.AIItem.WatchNearByPlayerAIItem;
//import com.zeus.pathfinder.Entity.MyEntity;

//import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
//import io.lumine.xikage.mythicmobs.api.exceptions.InvalidMobTypeException;

public class Execute 
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
	public static String handleVec(String vec)
	{
		String ans=vec;
		if(vec.startsWith("@v"))
		{
			ans=vec.replaceAll("@v", "");
			Location v1=getLocation(ans.split(",")[0]);
			Location v2=getLocation(ans.split(",")[1]);
			Vector a=v2.subtract(v1).toVector();
			return "{"+a.getX()+";"+a.getY()+";"+a.getZ()+"}";
		}
		return ans;
	}
	public static void handleScript(String cmd,int timer,double step,Location loc)
	{
		Player player=getNearByPlayer(loc);
		String sloc=getString(player.getLocation());
		String orig=cmd.replaceAll("@p",sloc);
		Entity entity=getNearByEntity(loc);
		sloc=getString(entity.getLocation());
		orig=orig.replaceAll("@e",sloc);
		LivingEntity entity1=getNearByLivingEntity(loc);
		sloc=getString(entity1.getLocation());
		orig=orig.replaceAll("@le",sloc);
		sloc=getString(loc);
		orig=orig.replaceAll("@l",sloc);
		orig=orig.replaceAll("@t",String.valueOf(timer));
		orig=orig.replaceAll("@st",String.valueOf(timer*step));
		executeCommand(orig);
	}
	public static void executeFile(Location locs,FileConfiguration file,int timer)
	{
		List<String> list=file.getStringList("Script");
		executeFile(1,list.size(),locs,file,timer);
	}
	public static void executeFile(int start,int end,Location locs,FileConfiguration file,int timer)
	{
		if(start>end)
			return;
		List<String> list=file.getStringList("Script");
		final Location loc=locs.clone();
		double step=0;
		for(int i=start;i<=end;i++)
		{
			String cmd=list.get(i-1);
			if(cmd.startsWith("addvec"))
			{
				Vector vector=getVector(cmd.split(" ")[1]);
				loc.add(vector);
			}
			if(cmd.startsWith("setstep"))
				step=Double.valueOf(cmd.split(" ")[1]);
			if(cmd.startsWith("sleep"))
			{
				double time=Double.valueOf(cmd.split(" ")[1]);
				final int s=i;
				new BukkitRunnable() 
				{
						
					@Override
					public void run() 
					{
						executeFile(s+1,end,loc,file,timer);
						cancel();
					}
				}.runTaskTimer(Main.getMain(),(int)(time*20),0);
				break;
			}
			handleScript(cmd,timer,step,loc);
			
		}
	}
	public static void executeOriginalFile(FileConfiguration file)
	{
		List<String> list=file.getStringList("Command");
		for(String cmd:list)
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),cmd);
	}
	@SuppressWarnings("deprecation")
	public static void executeCommand(String cmd)
	{
		
		
		if(cmd.startsWith("explode"))
		{
			Location loc=getLocation(cmd.split(" ")[1]);
			loc.getWorld().createExplosion(loc,Float.valueOf(cmd.split(" ")[2]));
		}
		if(cmd.startsWith("light"))
		{
			Location loc=getLocation(cmd.split(" ")[1]);
			boolean flag=Boolean.valueOf(cmd.split(" ")[2]);
			if(flag)
				loc.getWorld().strikeLightning(loc);
			else 
				loc.getWorld().strikeLightningEffect(loc);
		}
		if(cmd.startsWith("weather"))
		{
			double dur=Double.valueOf(cmd.split(" ")[1]);
			String name=cmd.split(" ")[2];
			Bukkit.getWorld(name).setWeatherDuration((int) dur);
		}
		/*if(cmd.startsWith("spawn-mm"))
		{
			Location loc=getLocation(cmd.split(" ")[1]);
			String name=cmd.split(" ")[2];
			try 
			{
				new BukkitAPIHelper().spawnMythicMob(name, loc);
			} catch (InvalidMobTypeException e)
			{
				e.printStackTrace();
			}
		}*/
		if(cmd.startsWith("draw-circle"))
		{
			Location loc=getLocation(cmd.split(" ")[1]);
			double r=Double.valueOf(cmd.split(" ")[2]);
			double node=Double.valueOf(cmd.split(" ")[3]);
			Vector vector=getVector(handleVec(cmd.split(" ")[4]));
			Particle particle=Particle.valueOf(cmd.split(" ")[5]);
			Circle c=new Circle(r,(int)node);
			c.calc();
			c.setVectorGet(new VectorGet(vector));
			for(Vector v:c.ChangeAllVector())
				loc.getWorld().spawnParticle(particle,loc.clone().add(v),0);					
		}
		if(cmd.startsWith("draw-seal"))
		{
			Location loc=getLocation(cmd.split(" ")[1]);
			double r=Double.valueOf(cmd.split(" ")[2]);
			Vector vector=getVector(handleVec(cmd.split(" ")[3]));
			Particle particle=Particle.valueOf(cmd.split(" ")[4]);
			Seal c=new Seal(r);
			c.calc();
			c.setVectorGet(new VectorGet(vector));
			for(Vector v:c.ChangeAllVector())
				loc.getWorld().spawnParticle(particle,loc.clone().add(v),0);
		}
		if(cmd.startsWith("draw-ball"))
		{
			Location loc=getLocation(cmd.split(" ")[1]);
			double r=Double.valueOf(cmd.split(" ")[2]);
			double node=Integer.valueOf(cmd.split(" ")[3]).intValue();
			Particle particle=Particle.valueOf(cmd.split(" ")[4]);
			Ball c=new Ball(r,(int) node);
			c.calc();
			for(Vector v:c.getList())
				loc.getWorld().spawnParticle(particle,loc.clone().add(v),0);
		}
	/*	if(cmd.startsWith("spawn-model"))
		{
			Location loc=getLocation(cmd.split(" ")[1]);
			int time=Integer.valueOf(cmd.split(" ")[2]).intValue();
			EntityType type=EntityType.valueOf(cmd.split(" ")[3]);
			MyEntity entity=new MyEntity(type,loc);
			try 
			{
				entity.spawnEntity();
				entity.getAIManager().killAI();
				entity.getAttributeManager().setKnockBackResistance(100);
				entity.getAIManager().addAI(new WatchNearByPlayerAIItem(entity.getBukkitEntity(),20),0);
				entity.getAttributeManager().setMaxHealth(20000);
			} catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) 
			{
				e.printStackTrace();
			}
			new BukkitRunnable()
			{
				
				@Override
				public void run() 
				{
					entity.getBukkitEntity().remove();
					cancel();
				}
			}.runTaskTimer(Main.getMain(),time*20,0);				
		}*/
		if(cmd.startsWith("do-circle"))
		{
			Location loc=getLocation(cmd.split(" ")[1]);
			double r=Double.valueOf(cmd.split(" ")[2]);
			double node=Double.valueOf(cmd.split(" ")[3]);
			Vector vector=getVector(handleVec(cmd.split(" ")[4]));
			String cmdFile=cmd.split(" ")[5];
			Circle c=new Circle(r,(int)node);
			c.calc();
			c.setVectorGet(new VectorGet(vector));
			for(Vector v:c.ChangeAllVector())
			{
				Location cloc=loc.clone().add(v);
				executeFile(cloc,YamlConfiguration.loadConfiguration(new File(Main.getMain().getDataFolder(),"/scripts/"+cmdFile+".yml")),0);				
			}
		}
		if(cmd.startsWith("do-seal"))
		{
			Location loc=getLocation(cmd.split(" ")[1]);
			double r=Double.valueOf(cmd.split(" ")[2]);
			Vector vector=getVector(handleVec(cmd.split(" ")[3]));
			String cmdFile=cmd.split(" ")[4];
			Seal c=new Seal(r);
			c.calc();
			c.setVectorGet(new VectorGet(vector));
			for(Vector v:c.ChangeAllVector())
			{
				Location cloc=loc.clone().add(v);
				executeFile(cloc,YamlConfiguration.loadConfiguration(new File(Main.getMain().getDataFolder(),"/scripts/"+cmdFile+".yml")),0);
			}
		}
		if(cmd.startsWith("do-ball"))
		{
			Location loc=getLocation(cmd.split(" ")[1]);
			double r=Double.valueOf(cmd.split(" ")[2]);
			double node=Double.valueOf(cmd.split(" ")[3]);
			String cmdFile=cmd.split(" ")[4];
			Ball c=new Ball(r,(int)node);
			c.calc();
			for(Vector v:c.getList())
			{
				Location cloc=loc.clone().add(v);
				executeFile(cloc,YamlConfiguration.loadConfiguration(new File(Main.getMain().getDataFolder(),"/scripts/"+cmdFile+".yml")),0);				
			}
		}
		if(cmd.startsWith("wait"))
		{
			Location loc=getLocation(cmd.split(" ")[1]);
			double time=Double.valueOf(cmd.split(" ")[2]);
			new BukkitRunnable() 
			{
				@Override
				public void run() 
				{
					executeFile(loc,YamlConfiguration.loadConfiguration(new File(Main.getMain().getDataFolder(),"/scripts/"+cmd.split(" ")[3]+".yml")),0);
					cancel();
				}
			}.runTaskTimer(Main.getMain(),(int)(time*20),0);
				
		}
		if(cmd.startsWith("timer"))
		{
			Location loc=getLocation(cmd.split(" ")[1]);
			double time=Double.valueOf(cmd.split(" ")[2]);
			double interval=Double.valueOf(cmd.split(" ")[3]);
			double count=Double.valueOf(cmd.split(" ")[4]);
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
					executeFile(loc,YamlConfiguration.loadConfiguration(new File(Main.getMain().getDataFolder(),"/scripts/"+cmd.split(" ")[5]+".yml")),c);
					//cancel();
				}
			}.runTaskTimer(Main.getMain(),(int)(time*20),(int)(interval*20));
		}
		if(cmd.startsWith("do-orig-command"))
		{
			String name=cmd.split(" ")[1];
			executeOriginalFile(YamlConfiguration.loadConfiguration(new File(Main.getMain().getDataFolder(),"/scripts/"+name+".yml")));
		}
		if(cmd.startsWith("particle"))
		{
			Location loc=getLocation(cmd.split(" ")[1]);
			Vector vector=getVector(handleVec(cmd.split(" ")[2]));
			double num=Double.valueOf(cmd.split(" ")[3]);
			Particle particle=Particle.valueOf(cmd.split(" ")[4]);
			loc.getWorld().spawnParticle(particle,loc,(int) num,vector.getX(),vector.getY(),vector.getZ());
		}
		if(cmd.startsWith("addpotion"))
		{
			Location loc=getLocation(cmd.split(" ")[1]);
			double r=Double.valueOf(cmd.split(" ")[2]);
			PotionEffectType type=PotionEffectType.getByName(cmd.split(" ")[3]);
			double level=Double.valueOf(cmd.split(" ")[4]);
			double time=Double.valueOf(cmd.split(" ")[5]);
			Util.addPotionToAround(loc,r,new PotionEffect(type,(int)(time*20),(int)level));
		}
		if(cmd.startsWith("play-effect"))
		{
			Location loc=getLocation(cmd.split(" ")[1]);
			Effect type=Effect.valueOf(cmd.split(" ")[2]);
			loc.getWorld().playEffect(loc,type,256);
		}
		if(cmd.startsWith("do-rotate-circle"))
		{
			Location loc=getLocation(cmd.split(" ")[1]);
			double r=Double.valueOf(cmd.split(" ")[2]);
			double node=Double.valueOf(cmd.split(" ")[3]);
			Vector vector=getVector(handleVec(cmd.split(" ")[4]));
			String cmdFile=cmd.split(" ")[5];
			double rotatecount=Double.valueOf(cmd.split(" ")[6]);
			double angle=Double.valueOf(cmd.split(" ")[7]);
			Circle c=new Circle(r,(int)node);
			c.calc();
			c.rotateY(rotatecount*angle);
			c.setVectorGet(new VectorGet(vector));
			for(Vector v:c.ChangeAllVector())
			{
				Location cloc=loc.clone().add(v);
				executeFile(cloc,YamlConfiguration.loadConfiguration(new File(Main.getMain().getDataFolder(),"/scripts/"+cmdFile+".yml")),0);				
			}
		}
		if(cmd.startsWith("draw-rotate-circle"))
		{
			Location loc=getLocation(cmd.split(" ")[1]);
			double r=Double.valueOf(cmd.split(" ")[2]);
			double node=Double.valueOf(cmd.split(" ")[3]);
			Vector vector=getVector(handleVec(cmd.split(" ")[4]));
			Particle particle=Particle.valueOf(cmd.split(" ")[5]);
			double rotatecount=Double.valueOf(cmd.split(" ")[6]);
			double angle=Double.valueOf(cmd.split(" ")[7]);
			Circle c=new Circle(r,(int)node);
			c.calc();
			c.rotateY(rotatecount*angle);
			c.setVectorGet(new VectorGet(vector));
			for(Vector v:c.ChangeAllVector())
				loc.getWorld().spawnParticle(particle,loc.clone().add(v),0);			
		}
		if(cmd.startsWith("draw-rotate-seal"))
		{
			Location loc=getLocation(cmd.split(" ")[1]);
			double r=Double.valueOf(cmd.split(" ")[2]);
			Vector vector=getVector(handleVec(cmd.split(" ")[3]));
			Particle particle=Particle.valueOf(cmd.split(" ")[4]);
			double rotatecount=Double.valueOf(cmd.split(" ")[5]);
			double angle=Double.valueOf(cmd.split(" ")[6]);
			Seal c=new Seal(r);
			c.calc();
			c.rotateY(rotatecount*angle);
			c.setVectorGet(new VectorGet(vector));
			for(Vector v:c.ChangeAllVector())
				loc.getWorld().spawnParticle(particle,loc.clone().add(v),0);			
		}
		if(cmd.startsWith("title"))
		{
			Location loc=getLocation(cmd.split(" ")[1]);
			double r=Double.valueOf(cmd.split(" ")[2]);
			for(Player p:getNearByPlayerSet(loc, r))
				p.sendTitle(cmd.split(" ")[3].replaceAll("&","¡ì"),cmd.split(" ")[4].replaceAll("&","¡ì"));
		}
	}
}
