package com.me.mygdxgame.util;

public class Settings
{
	public static final int SCENE_TYPE_RANDOM_BLOCKS = 1;
	public static final int SCENE_TYPE_HOUSE = 2;
	public static final int SCENE_TYPE_BOX = 3;
	public static final int SCENE_TYPE_NOISE = 4;
	
	private static int VISUAL_RANGE = 1900;
	private static final int SCENE_TYPE = SCENE_TYPE_RANDOM_BLOCKS;
	private static final float MOVEMENT_DISTANCE = 0.1f;
	private static final float COLLISION_DISTANCE = 1.0f;
	private static final float COLLISION_DISTANCE2 = COLLISION_DISTANCE * COLLISION_DISTANCE;
	private static final int MAX_OBJECT_COUNT = 5000;
	private static final float OCTREE_MIN_SIZE = 2;
	private static final int OCTREE_MAX_NUM_DATA = 64;
	private static final int DEFAULT_SCENE_SIZE = 64;
	private static boolean COLLISION_ACTIVE = true;
	private static boolean LIGHTING_ACTIVE = false;
	private static boolean HUD_ACTIVE = true;
	private static boolean CONSOLE_ACTIVE = false;
	private static boolean SHOW_POLYGONS = true;
	private static boolean SHOW_OCTREES = false;
	private static boolean SHOW_PORTALS = true;

	public static int getViewDistance2()
	{
		return VISUAL_RANGE;
	}

	public static int getSceneType()
	{
		return SCENE_TYPE;
	}

	public static float getMovementDistance()
	{
		return MOVEMENT_DISTANCE;
	}

	public static float getCollisionDistance()
	{
		return COLLISION_DISTANCE;
	}

	public static float getCollisionDistance2()
	{
		return COLLISION_DISTANCE2;
	}
	
	public static float getOctreeMinSize()
	{
		return OCTREE_MIN_SIZE;
	}
	
	public static int getOctreeMaxData()
	{
		return OCTREE_MAX_NUM_DATA;
	}

	public static int getSceneSize()
	{
		return DEFAULT_SCENE_SIZE;
	}
	
	public static boolean isCollisionActive()
	{
		return COLLISION_ACTIVE;
	}
	
	public static void toggleCollision()
	{
		COLLISION_ACTIVE = !COLLISION_ACTIVE;
	}

	public static boolean isLightingActive()
	{
		return LIGHTING_ACTIVE;
	}
	
	public static void toggleLighting()
	{
		LIGHTING_ACTIVE = !LIGHTING_ACTIVE;
	}

	public static boolean isHudActive()
	{
		return HUD_ACTIVE;
	}
	
	public static void toggleHud()
	{
		HUD_ACTIVE = !HUD_ACTIVE;
	}

	public static boolean isConsoleActive()
	{
		return CONSOLE_ACTIVE;
	}
	
	public static void toggleConsole()
	{
		CONSOLE_ACTIVE = !CONSOLE_ACTIVE;
	}

	public static boolean showPolygons()
	{
		return SHOW_POLYGONS;
	}
	
	public static void toggleShowPolygons()
	{
		SHOW_POLYGONS = !SHOW_POLYGONS;
	}

	public static boolean showOctrees()
	{
		return SHOW_OCTREES;
	}
	
	public static void toggleShowOctrees()
	{
		SHOW_OCTREES = !SHOW_OCTREES;
	}

	public static void increaseVisualRange()
	{
		if (VISUAL_RANGE < 900)
			VISUAL_RANGE += 100;
	}

	public static void decreaseVisualRange()
	{
		if (VISUAL_RANGE > 100)
			VISUAL_RANGE -= 100;
	}

	public static int getMaxObjectCount()
	{
		return MAX_OBJECT_COUNT;
	}

	public static boolean showPortals()
	{
		return SHOW_PORTALS;
	}
	
	public static void toggleShowPortals()
	{
		SHOW_PORTALS = !SHOW_PORTALS;
	}
}
