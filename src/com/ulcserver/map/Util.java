package com.ulcserver.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public class Util 
{
	public double PI=3.1415926;
	static public List<Player> getNearByPlayer(Location loc,double R)
	{
		List<Player> ans=new ArrayList<Player>();
		for(Entity e:loc.getWorld().getNearbyEntities(loc,R,R,R))
		{
			if(e instanceof Player)
				ans.add((Player)e);
		}
		return ans;
		/*List<LivingEntity> ans=new ArrayList<LivingEntity>();
		List<Entity> list;
		Entity g=loc.getWorld().spawnEntity(loc,EntityType.EGG);
		list=g.getNearbyEntities(R,R,R);
		g.remove();
		for(Entity e:list)
		{
			if((e instanceof LivingEntity) && (!e.isDead()))
			{
				LivingEntity ee=(LivingEntity)e;
				ans.add(ee);
			}
		}
		return ans;*/
	}
	@SuppressWarnings("deprecation")
	static public void BlockFall(Location loc,Material m,Vector v)
	{
		FallingBlock block =loc.getWorld().spawnFallingBlock(loc,m,(byte)0);
		block.setVelocity(v);
	}
	static public Vector getV(Location from,Location to)
	{
		return to.subtract(from).toVector();
	}
	static public void drawCircle(Location p,int r,int n,Effect e)
	{
		double pi=3.1415926535,po,a=(2*pi)/n;
		double x=p.getX(),z=p.getZ();
		for(int i=1;i<=n;i++)
		{
			po=i*a;
			double tx=Math.sin(po)*r+x,tz=Math.cos(po)*r+z;
			Location news=new Location(p.getWorld(),tx,p.getBlockY(),tz);
			news.getWorld().playEffect(news,e,1,256);
		}
	}
	static public void drawLine(Location from,Location to,Effect e)
	{
		Vector v=getV(from,to).normalize().multiply(0.2);
		int n=(int)(from.distance(to)/0.2);
		while(n>=0)
		{
			n--;
			Location news=from.clone();
			news.getWorld().playEffect(news,e,1,256);
			from.add(v);
		}
	}
	static public void flyAwayEntity(Location loc,int r,Vector v,Player p)
	{
		List<Entity> list;
		Entity g=loc.getWorld().spawnEntity(loc,EntityType.SNOWBALL);
		list=g.getNearbyEntities(r,r,r);
		g.remove();
		for(Entity e:list)
		{
			if(e.getType()==EntityType.PLAYER && e.equals(p))
				continue;
			e.setVelocity(v);
		}
	}
	static public void flyAwayEntityNOPlayer(Location loc,int r,Vector v)
	{
		List<Entity> list;
		Entity g=loc.getWorld().spawnEntity(loc,EntityType.SNOWBALL);
		list=g.getNearbyEntities(r,r,r);
		g.remove();
		for(Entity e:list)
		{
			if(e.getType()==EntityType.PLAYER)
				continue;
			e.setVelocity(v);
		}
	}
	static public void damageEntity(Entity entity,int damage,Entity attacker)
	{
		if(((entity instanceof LivingEntity)) && (!entity.isDead()))
	    {
			Damageable damageableEntity=(Damageable)entity;
			damageableEntity.damage(damage, attacker);
	    }
	}//p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL,(int)(1600+0.02*AP.get(p.getName())*100),2));
	static public void addPotionToAround(Location loc,double d,PotionEffect p)
	{
		List<Entity> list;
		Entity g=loc.getWorld().spawnEntity(loc,EntityType.SNOWBALL);
		list=g.getNearbyEntities(d,d,d);
		g.remove();
		for(Entity e:list)
		{
			if((e instanceof LivingEntity) && (!e.isDead()))
			{
				LivingEntity ee=(LivingEntity)e;
				ee.addPotionEffect(p);
			}
		}
	}
	static public void damageAroundNOPlayerWithEffect(Location loc,int r,int va)
	{
		List<Entity> list;
		Entity g=loc.getWorld().spawnEntity(loc,EntityType.SNOWBALL);
		list=g.getNearbyEntities(r,r,r);
		g.remove();
		for(Entity e:list)
		{
			if(e.getType()==EntityType.PLAYER)
				continue;
			if(e instanceof Damageable)
				e.getLocation().getWorld().playEffect(e.getLocation(),Effect.MOBSPAWNER_FLAMES,256);
			damageEntity(e,va,g);
		}
	}
	static public void damageAroundWithEffect(Location loc,int r,int va,Player p)
	{
		List<Entity> list;
		Entity g=loc.getWorld().spawnEntity(loc,EntityType.SNOWBALL);
		list=g.getNearbyEntities(r,r,r);
		g.remove();
		for(Entity e:list)
		{
			if(e.getType()==EntityType.PLAYER && e.equals(p))
				continue;
			if(e instanceof Damageable)
				e.getLocation().getWorld().playEffect(e.getLocation(),Effect.MOBSPAWNER_FLAMES,256);
			damageEntity(e,va,g);
		}
	}
	static public void damageAroundNOPlayer(Location loc,int r,int va,Player p)
	{
		List<Entity> list;
		Entity g=loc.getWorld().spawnEntity(loc,EntityType.SNOWBALL);
		list=g.getNearbyEntities(r,r,r);
		g.remove();
		for(Entity e:list)
		{
			if(e.getType()==EntityType.PLAYER)
				continue;
			damageEntity(e,va,p);
		}
	}
	static public void damageAround(Location loc,int r,int va,Player p)
	{
		List<Entity> list;
		Entity g=loc.getWorld().spawnEntity(loc,EntityType.SNOWBALL);
		list=g.getNearbyEntities(r,r,r);
		g.remove();
		for(Entity e:list)
		{
			if(e.getType()==EntityType.PLAYER && e.equals(p))
				continue;
			damageEntity(e,va,g);
		}
	}
	static public Location GetCarelessLocation(Location loc)
	{
		return new Location(loc.getWorld(),loc.getBlockX(),loc.getBlockY(),loc.getBlockZ());
	}
	static public boolean IsBlockCompare(Location loc1,Location loc2)
	{
		String world1=loc1.getWorld().getName();
		String world2=loc2.getWorld().getName();
		int a1,a2,b1,b2,c1,c2;
		a1=loc1.getBlockX();
		a2=loc2.getBlockX();
		b1=loc1.getBlockY();
		b2=loc2.getBlockY();
		c1=loc1.getBlockZ();
		c2=loc2.getBlockZ();
		if(world1.equals(world2) && a1==a2 && b1==b2 && c1==c2)
			return true;
		else
			return false;
	}
	static public int IsContain(Map<Location, Integer> chestList,Location loc)
	{
		Set<Location> sets=chestList.keySet();
		ArrayList<Location> list=new ArrayList<Location>(sets);
		for(Location tmp:list)
		{
			if(IsBlockCompare(loc,tmp))
				return list.indexOf(tmp);
		}
		return -1;
	}
}
