/**
 * 
 */
package com.me.mygdxgame.gfx;

import com.badlogic.gdx.graphics.Color;

/**
 * @author deDokter
 *
 */
public class DaylightSimulation
{
	private static float time = 0;
	private static final float FACTOR = 0.7f / 6.0f;

	public static Color getColor()
	{
		float c = 0.3f;

		if (time > 6 && time < 12)
			c += (time - 6) * FACTOR;
		else if (time > 12 && time < 18)
			c += (6 - (time - 12)) * FACTOR;
		return new Color(c, c, c, 1.0f);
	}

	public static void updateTime(float deltaTime)
	{
		time += deltaTime / 10.0f;
		if (time > 24)
			time -= 24;
	}

	public static float getTime()
	{
		return time;
	}
}
