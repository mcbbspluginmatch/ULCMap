package com.ulcserver.map.Scripts.Preloads;

import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.Preload;
import com.ulcserver.map.Scripts.ReadUtil;

public class addvec implements Preload
{

	@Override
	public boolean needHandle(String str) 
	{
		return str.startsWith("addvec");
	}

	@Override
	public String handleScript(Executer cmder,String cmd) 
	{
		cmder.executeLocation.add(ReadUtil.getVector(cmd.split(" ")[1]));
		return cmd;
	}

}
