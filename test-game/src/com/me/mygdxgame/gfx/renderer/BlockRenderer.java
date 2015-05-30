package com.me.mygdxgame.gfx.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.gfx.model.CollidableModelInstance;
import com.me.mygdxgame.gfx.octree.DynamicOctree;
import com.me.mygdxgame.loader.MapLoader;
import com.me.mygdxgame.util.Settings;

public class BlockRenderer
{
	private static final String DEFAULT_MAP_NAME = "maps/default.map";
	private final String className = this.getClass().getSimpleName();
	private DynamicOctree<CollidableModelInstance> internalTree;
	private MapLoader loader;

	public BlockRenderer()
	{
		this(Settings.getSceneSize(), DEFAULT_MAP_NAME);
	}
	
	public BlockRenderer(int sceneSize)
	{
		this(sceneSize, DEFAULT_MAP_NAME);
	}

	public BlockRenderer(String mapName)
	{
		this(Settings.getSceneSize(), mapName);
	}

	public BlockRenderer(int sceneSize, String mapName)
	{
		internalTree = new DynamicOctree<CollidableModelInstance>(new Vector3(0, 0, 0), sceneSize);
		loader = new MapLoader(mapName);
		loader.load(internalTree, Settings.getSceneType());

		Gdx.app.log(className, "instances: " + internalTree.getLoad(true));
		Gdx.app.log(className, "octrees: " + internalTree.getLoad(false));
	}

	public Array<CollidableModelInstance> getInstances(Frustum frustum)
	{
		Array<CollidableModelInstance> instances = new Array<CollidableModelInstance>();
		if (Settings.showPolygons())
			instances.addAll(internalTree.getInstances(frustum));
		if (Settings.showOctrees())
			instances.addAll(internalTree.getOctreeInstances(frustum));
		return instances;
	}
}
