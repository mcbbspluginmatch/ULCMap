package com.ulcserver.map.Scripts.Preloads;

import com.ulcserver.map.Scripts.Executer;
import com.ulcserver.map.Scripts.Preload;

public class setstep implements Preload
{

	@Override
	public boolean needHandle(String str) 
	{
		return str.startsWith("setstep");
	}

	@Override
	public String handleScript(Executer cmder, String cmd) 
	{
		cmder.timerStep=Double.valueOf(cmd.split(" ")[1]);
		return cmd;
	}

}
