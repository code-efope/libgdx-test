package com.me.mygdxgame.util;

public class Settings
{
	private static final int VIEW_DISTANCE = 160;
	private static final int SCENE_TYPE = 3;
	private static final float MOVEMENT_DISTANCE = 0.1f;
	private static final float COLLISION_DISTANCE = 1.0f;
	private static final float COLLISION_DISTANCE2 = COLLISION_DISTANCE * COLLISION_DISTANCE;
	private static final int OCTREE_MIN_SIZE = 4;
	private static final int OCTREE_MAX_NUM_DATA = 64;
	private static final int OCTREE_MAX_DEPTH = 9;
	private static final int DEFAULT_SCENE_SIZE = 256;

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

	public static int getOctreeMaxDepth()
	{
		return OCTREE_MAX_DEPTH;
	}

	public static int getSceneSize()
	{
		return DEFAULT_SCENE_SIZE;
	}
}
