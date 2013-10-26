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
	public String logFrame()
	{
		frames++;
		if (System.nanoTime() - startTime >= 1000000000)
		{
			logString = "fps/avg: " + frames + "/" + getAvarageFPS();
			Gdx.app.log(this.getClass().getName(), logString);
			lastFrames = frames;
			countFrames += frames;
			numFrames++;
			frames = 0;
			startTime = System.nanoTime();
		}
		return logString;
	}
	
	public int getAvarageFPS()
	{
		return (numFrames == 0) ? 0 : countFrames / numFrames;
	}
}
