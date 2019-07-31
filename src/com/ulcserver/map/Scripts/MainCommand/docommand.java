package com.ulcserver.map.Scripts.MainCommand;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ulcserver.map.Main;
import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.Handler;

public class docommand implements Handler
{

	@Override
	public boolean shouldHandle(String str) 
	{
		return str.startsWith("do-orig-command");
	}
	public static void executeOriginalFile(FileConfiguration file)
	{
		List<String> list=file.getStringList("Command");
		for(String cmd:list)
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),cmd);
	}
	@Override
	public void execute(Executer l, String line) 
	{
		String name=line.split(" ")[1];
		executeOriginalFile(YamlConfiguration.loadConfiguration(new File(Main.getMain().getDataFolder(),"/scripts/"+name+".yml")));
	}

	@Override
	public boolean continueExecute() 
	{
		
		return true;
	}

}
