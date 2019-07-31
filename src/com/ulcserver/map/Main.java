package com.ulcserver.map;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.MainCommand.addpotion;
import com.ulcserver.map.Scripts.MainCommand.doball;
import com.ulcserver.map.Scripts.MainCommand.docircle;
import com.ulcserver.map.Scripts.MainCommand.dorotatecircle;
import com.ulcserver.map.Scripts.MainCommand.doseal;
import com.ulcserver.map.Scripts.MainCommand.drawball;
import com.ulcserver.map.Scripts.MainCommand.drawcircle;
import com.ulcserver.map.Scripts.MainCommand.drawrotatecircle;
import com.ulcserver.map.Scripts.MainCommand.drawrotateseal;
import com.ulcserver.map.Scripts.MainCommand.drawseal;
import com.ulcserver.map.Scripts.MainCommand.explode;
import com.ulcserver.map.Scripts.MainCommand.light;
import com.ulcserver.map.Scripts.MainCommand.particle;
import com.ulcserver.map.Scripts.MainCommand.playeffect;
import com.ulcserver.map.Scripts.MainCommand.sleep;
//import com.ulcserver.map.Scripts.MainCommand.spawnmm;
import com.ulcserver.map.Scripts.MainCommand.timer;
import com.ulcserver.map.Scripts.MainCommand.title;
import com.ulcserver.map.Scripts.MainCommand.wait;
import com.ulcserver.map.Scripts.MainCommand.weather;
import com.ulcserver.map.Scripts.Preloads.addvec;
import com.ulcserver.map.Scripts.Preloads.mark;
import com.ulcserver.map.Scripts.Preloads.setstep;
import com.ulcserver.map.config.Config;
import com.ulcserver.map.mapItem.Trap;

public class Main extends JavaPlugin
{
	static
	{
		Executer.registerHandler(new addpotion());
		Executer.registerHandler(new doball());
		Executer.registerHandler(new dorotatecircle());
		Executer.registerHandler(new docircle());
		Executer.registerHandler(new doseal());
		Executer.registerHandler(new drawball());
		Executer.registerHandler(new drawcircle());
		Executer.registerHandler(new drawrotatecircle());
		Executer.registerHandler(new drawrotateseal());
		Executer.registerHandler(new drawseal());
		Executer.registerHandler(new explode());
		Executer.registerHandler(new light());
		Executer.registerHandler(new particle());
		Executer.registerHandler(new playeffect());
		Executer.registerHandler(new sleep());
		//Executer.registerHandler(new spawnmm());
		//Executer.registerHandler(new spawnmodel());
		Executer.registerHandler(new timer());
		Executer.registerHandler(new title());
		Executer.registerHandler(new wait());
		Executer.registerHandler(new weather());
		Executer.registerPreloader(new addvec());
		Executer.registerPreloader(new mark());
		Executer.registerPreloader(new setstep());
	}
	public static Plugin getMain() 
	{
		return Bukkit.getPluginManager().getPlugin("ULCMap");
	}
	@Override
	public void onEnable()
	{
		try 
		{
			Config.CheckFiles();
			Config.readAll();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		Bukkit.getServer().getPluginManager().registerEvents(new MapListener(),this);
	}
	@Override
	public void onDisable()
	{
		try 
		{
			Config.saveAll();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	public static void addTrap(Location loc,double radius,String cmd,int cool) 
	{
		Trap trap=new Trap(loc,radius,cmd);
		trap.coolDownTime=cool;
		Trap.list.add(trap);
	}
	@Override
	public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args)
	{
		if(sender instanceof Player)
		{
			if(cmd.getName().equalsIgnoreCase("ulcmap"))
			{
				if(args[0].equals("addtrap"))
				{
					if(args.length!=4)
					{
						sender.sendMessage("参数不正确！");
						return false;
					}
					double radius=Double.valueOf(args[1]);
					Main.addTrap(((Player) sender).getLocation(), radius,args[2],Integer.valueOf(args[3]));
					sender.sendMessage("触发器添加完毕！");
				}
				if(args[0].equals("num"))
				{
					sender.sendMessage("现存"+Trap.list.size()+"个陷阱！");
				}
				if(args[0].equals("totrap"))
				{
					if(args.length!=1)
					{
						sender.sendMessage("参数不正确！");
						return false;
					}
					if(Integer.valueOf(args[0])>Trap.list.size())
					{
						sender.sendMessage("没有这个陷阱！");
						return false;
					}		
					((Player) sender).teleport(Trap.list.get(Integer.valueOf(args[0])).getCenter());
					sender.sendMessage("传送到序号为"+Integer.valueOf(args[0])+"的陷阱处！");
					((Player) sender).getWorld().playEffect(((Player) sender).getLocation(),Effect.MOBSPAWNER_FLAMES,256,256);
				}
				if(args[0].equals("deltrap"))
				{
					if(args.length!=1)
					{
						sender.sendMessage("参数不正确！");
						return false;
					}
					int c=Integer.valueOf(args[0]);
					Trap.list.remove(c);
					sender.sendMessage("已经删除序号为"+Integer.valueOf(args[0])+"的陷阱处！");
					sender.sendMessage("序号重置");
				}
				if(args[0].equals("reload"))
				{
					Config.reload();
					sender.sendMessage("重载完毕！");
				}
				if(args[0].equals("save"))
				{
					try 
					{
						Config.saveAll();
						sender.sendMessage("保存完毕！");
					} catch (IOException e) 
					{
						sender.sendMessage("在保存配置文件时遇到预期之外的错误！");
						e.printStackTrace();
					}
				}
			}
		}
		return true;
	}
	
}
