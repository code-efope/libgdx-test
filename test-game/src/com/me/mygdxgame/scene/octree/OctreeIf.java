package com.me.mygdxgame.scene.octree;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.utils.Array;

public interface OctreeIf
{
	public boolean insert(ModelInstance instance);
	public Array<ModelInstance> getInstances(Frustum frustum);
	public Array<ModelInstance> getOctrees(Frustum frustum);
	public int getLoad(boolean instances);
}
