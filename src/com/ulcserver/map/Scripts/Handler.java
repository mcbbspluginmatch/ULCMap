package com.ulcserver.map.Scripts;

public interface Handler 
{
	public boolean shouldHandle(String str);
	public void execute(Executer l,String line);
	public boolean continueExecute();
}
