package com.me.mygdxgame.scene.octree;

import com.badlogic.gdx.math.Vector3;

public class OctreeBuilder
{
	public enum OctreeType
	{
		Static,
		Dynamic
	}

	public static OctreeIf createOctree(OctreeType type, Vector3 center, float diameter)
	{
		switch (type)
		{
			case Static:
				return new StaticOctree(center, diameter);
				
			case Dynamic:
				return new DynamicOctree(center, diameter);
				
			default:
				throw new IllegalArgumentException();
		}
	}
}
