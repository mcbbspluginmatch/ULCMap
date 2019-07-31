package com.ulcserver.map.Scripts;

public interface Preload 
{
	public boolean needHandle(String str);
	public String handleScript(Executer cmder,String cmd);
}
