package com.me.mygdxgame.scene.octree;

import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.scene.models.CollidableModelInstance;

public interface OctreeIf
{
	public boolean insert(CollidableModelInstance instance);
	public Array<CollidableModelInstance> getInstances(Frustum frustum);
	public Array<CollidableModelInstance> getOctrees(Frustum frustum);
	public int getLoad(boolean instances);
}
