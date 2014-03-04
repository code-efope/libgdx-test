/**
 * 
 */
package com.me.mygdxgame.util;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;

/**
 * @author deDokter
 *
 */
public class ColorRandomizer
{
	private static final Random rand = new Random();
	private static float a;

	public static Color getGrayScale()
	{
		a = rand.nextFloat();
		return new Color(a, a, a, 1.0f);
	}

	public static Color getColor(final boolean randomizeAlpha)
	{
		a = 1.0f;
		if (randomizeAlpha)
			a = rand.nextFloat();
		return new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), a);
	}
}
