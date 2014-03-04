package com.me.mygdxgame.gfx.octree;

import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.gfx.model.CollidableModelInstance;
import com.me.mygdxgame.interfaces.Treeable;

public abstract class BaseOctree<T extends Treeable> implements OctreeIf
{
	public boolean insert(T instance)
	{
		return false;
	}

	public Array<T> getInstances(Frustum frustum)
	{
		return new Array<T>();
	}

	public Array<T> getInstances(Vector3 point)
	{
		return new Array<T>();
	}

	public Array<CollidableModelInstance> getOctreeInstances(Frustum frustum)
	{
		return new Array<CollidableModelInstance>();
	}

	public Array<CollidableModelInstance> getOctreeInstances()
	{
		return new Array<CollidableModelInstance>();
	}
}
