package com.me.mygdxgame.util;

public class Settings
{
	private static final int VIEW_DISTANCE = 160;
	private static final int SCENE_TYPE = 3;
	private static final float MOVEMENT_DISTANCE = 0.1f;
	private static final float COLLISION_DISTANCE = 1.0f;
	private static final float COLLISION_DISTANCE2 = COLLISION_DISTANCE * COLLISION_DISTANCE;
	private static final int OCTREE_MIN_SIZE = 2;
	private static final int OCTREE_MAX_NUM_DATA = 32;
	private static final int DEFAULT_SCENE_SIZE = 256;
	private static boolean COLLISION_ACTIVE = true;
	private static boolean LIGHTING_ACTIVE = false;
	private static boolean HUD_ACTIVE = true;

	public static int getViewDistance2()
	{
		return VIEW_DISTANCE;
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
	
	public static int getOctreeMinSize()
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
		if (COLLISION_ACTIVE)
			COLLISION_ACTIVE = false;
		else
			COLLISION_ACTIVE = true;
	}

	public static boolean isLightingActive()
	{
		return LIGHTING_ACTIVE;
	}
	
	public static void toggleLighting()
	{
		if (LIGHTING_ACTIVE)
			LIGHTING_ACTIVE = false;
		else
			LIGHTING_ACTIVE = true;
	}

	public static boolean isHudActive()
	{
		return HUD_ACTIVE;
	}
	
	public static void toggleHud()
	{
		if (HUD_ACTIVE)
			HUD_ACTIVE = false;
		else
			HUD_ACTIVE = true;
	}
}
