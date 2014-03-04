package com.me.mygdxgame.scripting;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;

public class ScriptManager
{
	private static final ScriptManager instance = new ScriptManager();
	private Map<String, BaseScript> scripts = new HashMap<String, BaseScript>();

	private ScriptManager()
	{
		TestScript01 ts01 = new TestScript01(1);
		TestScript02 ts02 = new TestScript02(2);

		addScript(ts01.getClass().getSimpleName(), ts01);
		addScript("test02", ts02);
	}

	public static ScriptManager getInstance()
	{
		return instance;
	}

	public void addScript(String name, BaseScript script)
	{
		scripts.put(name, script);
	}

	public ScriptResult execute(String command)
	{
		ScriptResult res;

		Gdx.app.log(this.getClass().getSimpleName(),"executing: " + command);
		if (command.length() > 0)
		{
			String[] parts = command.split(" ");
			String[] params = new String[parts.length - 1];
			for (int i = 0; i < parts.length - 1; i++)
				params[i] = parts[i + 1];
			String scriptName = parts[0];

			if (scriptName.equals("help") && params.length == 1 && scripts.containsKey(params[0]))
			{
				BaseScript script = scripts.get(params[0]);
				if (script != null)
					return new ScriptResult(true, params[0] + "(" + script.getHelp() + ")");
			}

			if (scripts.containsKey(scriptName))
			{
				BaseScript script = scripts.get(scriptName);
				if (script != null)
					res = script.execute(params);
				else
					res = new ScriptResult("No valid script found");
			}
			else
				res = new ScriptResult("Script '" + scriptName + "' not found");
		}
		else
			res = new ScriptResult("empty command");
		return res;
	}
}
