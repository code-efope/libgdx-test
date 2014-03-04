package com.me.mygdxgame.scripting;


public abstract class BaseScript implements ScriptIf
{
	private int numOfParameters;
	private String helpMessage = "usage: ";

	public BaseScript(int numOfParameters)
	{
		this.numOfParameters = numOfParameters;
	}

	public BaseScript(int numOfParameters, String helpMessage)
	{
		this.numOfParameters = numOfParameters;
		this.helpMessage = helpMessage;
	}

	@Override
	public ScriptResult execute(String[] args)
	{
		ScriptResult res;
		if (args.length < numOfParameters)
			res = new ScriptResult("too few parameters. " + numOfParameters + " expected.");
		else if (args.length > numOfParameters)
			res = new ScriptResult("too many parameters. " + numOfParameters + " expected.");
		else
			res = new ScriptResult(true, "");
		return res;
	}
	
	@Override
	public String getHelp()
	{
		return helpMessage;
	}
}
