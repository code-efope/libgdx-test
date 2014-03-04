package com.me.mygdxgame.scripting;

import com.badlogic.gdx.Gdx;

public class TestScript02 extends BaseScript
{
	public TestScript02(int numOfParameters)
	{
		super(numOfParameters, "parrm1, param2");
	}

	public ScriptResult execute(String[] args)
	{
		ScriptResult res;
		
		res = super.execute(args);
		
		if (res.success)
		{
			for (String s: args)
				Gdx.app.log(this.getClass().getSimpleName(), "arg: " + s);
			res = new ScriptResult(true, "good boy");
		}
		return res;
	}
}
