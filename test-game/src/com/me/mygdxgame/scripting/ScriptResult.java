package com.me.mygdxgame.scripting;

public class ScriptResult
{
	public final boolean success;
	public final String message;

	public ScriptResult(String message)
	{
		this(false, message);
	}

	public ScriptResult(boolean success, String message)
	{
		this.success = success;
		this.message = message;
	}
}
