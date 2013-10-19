package com.me.mygdxgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main
{
	public static void main(String[] args)
	{
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		/* init default values */
		cfg.title = "test-game";
		cfg.useGL20 = true;
		cfg.width = 640;
		cfg.height = 480;
		cfg.fullscreen = false;
		cfg.vSyncEnabled = false;
		cfg.resizable = false;
//		cfg.backgroundFPS = -1;
//		cfg.foregroundFPS = -1;

		for (String arg: args)
		{
			if (arg.startsWith("-xsize="))
				cfg.width = Integer.parseInt(arg.substring(7));
			else if (arg.startsWith("-ysize="))
				cfg.height = Integer.parseInt(arg.substring(7));
			else if (arg.startsWith("-fullscreen"))
				cfg.fullscreen = true;
			else if (arg.startsWith("-vsync"))
				cfg.vSyncEnabled = true;
		}
		new LwjglApplication(new LoadModelsTest(), cfg);
	}
}
