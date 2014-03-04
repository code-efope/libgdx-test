package com.me.mygdxgame.gfx.octree;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;

public class OctreeColors
{
	private static final Map<Integer, Color> octreeColors = new HashMap<Integer, Color>();
	private static final Color fallbackColor = new Color(1.0f, 0.5f, 0.5f, 1.0f);

	static
	{
		octreeColors.put(1, new Color(0.0f, 0.0f, 0.0f, 1.0f));
		octreeColors.put(2, new Color(0.25f, 0.0f, 0.0f, 1.0f));
		octreeColors.put(4, new Color(0.5f, 0.0f, 0.0f, 1.0f));
		octreeColors.put(8, new Color(0.75f, 0.0f, 0.0f, 1.0f));
		octreeColors.put(16, new Color(1.0f, 0.0f, 0.0f, 1.0f));
		octreeColors.put(32, new Color(0.0f, 0.25f, 0.0f, 1.0f));
		octreeColors.put(64, new Color(0.0f, 0.5f, 0.0f, 1.0f));
		octreeColors.put(128, new Color(0.0f, 0.75f, 0.0f, 1.0f));
		octreeColors.put(256, new Color(0.0f, 1.0f, 0.0f, 1.0f));
	}
	
	public static Color getColor(int size)
	{
		if (octreeColors.containsKey(size))
			return octreeColors.get(size);
		else
			return fallbackColor;
	}
}
