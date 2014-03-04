package com.me.mygdxgame.util;

import com.badlogic.gdx.Gdx;

public class FPSCounter
{
	long startTime = System.nanoTime();
	int frames = 0, lastFrames = 0, countFrames = 0, numFrames = 0;
	String logString;

	/**
	 * count and display current fps
	 */
	public String logFrame(final boolean console)
	{
		frames++;
		if (System.nanoTime() - startTime >= 1000000000)
		{
			logString = "fps/avg: " + frames + "/" + getAvarageFPS();
			lastFrames = frames;
			countFrames += frames;
			numFrames++;
			frames = 0;
			startTime = System.nanoTime();

			if (console)
				Gdx.app.log(this.getClass().getName(), logString);
		}
		return logString;
	}
	
	public int getAvarageFPS()
	{
		return (numFrames == 0) ? 0 : countFrames / numFrames;
	}
}
