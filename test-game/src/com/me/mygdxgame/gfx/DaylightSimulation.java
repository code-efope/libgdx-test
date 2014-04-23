package com.me.mygdxgame.gfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class DaylightSimulation
{
	private static float time = 0;
	private static Vector3 sunPosition = new Vector3(0.0f, 0.0f, 0.0f);
	private static final float TIME_SCALING = 0.5f;
	private static final float SUN_RADIUS = 90.0f;
	private static final float FACTOR = 0.7f / 6.0f;
	private static final float MORNING = 6.0f;
	private static final float NOON = 12.0f;
	private static final float EVENING = 18.0f;
	private static final float MIDNIGHT = 24.0f;

	public static Color getColor()
	{
		float c = 0.3f;

		if (time >= MORNING && time < NOON)
			c += (time - MORNING) * FACTOR;
		else if (time >= NOON && time < EVENING)
			c += (MORNING - (time - NOON)) * FACTOR;
		return new Color(c, c, c, 1.0f);
	}

	public static void updateTime(float deltaTime)
	{
		time += deltaTime / TIME_SCALING;
		if (time > MIDNIGHT)
			time -= MIDNIGHT;
		sunPosition.z = SUN_RADIUS * (float)Math.cos(time * 15.0f * Math.PI / 180.0);
		sunPosition.y = SUN_RADIUS * (float)Math.sin(time * 15.0f * Math.PI / 180.0);
	}

	public static float getTime()
	{
		return time;
	}
	
	public static Vector3 getSunPosition()
	{
		return sunPosition;
	}
}
