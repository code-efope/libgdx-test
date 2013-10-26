package com.me.mygdxgame.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.loader.MapLoader;
import com.me.mygdxgame.scene.octree.OctreeBuilder;
import com.me.mygdxgame.scene.octree.OctreeBuilder.OctreeType;
import com.me.mygdxgame.scene.octree.OctreeIf;
import com.me.mygdxgame.util.Settings;

public class SceneManager
{
	private static final String DEFAULT_MAP_NAME = "maps/default.map";
	private boolean showPolygons, showOctrees;
	private OctreeIf internalTree;
	private MapLoader loader;

	public SceneManager()
	{
		this(Settings.getSceneSize());
	}
	
	public SceneManager(int sceneSize)
	{
		this(sceneSize, DEFAULT_MAP_NAME);
	}

	public SceneManager(String mapName)
	{
		this(Settings.getSceneSize(), mapName);
	}

	public SceneManager(int sceneSize, String mapName)
	{
		internalTree = OctreeBuilder.createOctree(OctreeType.Dynamic, new Vector3(0, 0, 0), sceneSize);
		loader = new MapLoader(mapName);
		loader.load(internalTree, Settings.getSceneType());

		Gdx.app.log(this.getClass().getName(), "instances: " + internalTree.getLoad(true));
		Gdx.app.log(this.getClass().getName(), "octrees: " + internalTree.getLoad(false));
		setShowPolygons(true);
		setShowOctrees(true);
	}

	public void setShowPolygons(boolean value)
	{
		showPolygons = value;
	}

	public void setShowOctrees(boolean value)
	{
		showOctrees = value;
	}

	public Array<ModelInstance> getInstances(Frustum frustum)
	{
		Array<ModelInstance> instances = new Array<ModelInstance>();
		if (showPolygons)
			instances.addAll(internalTree.getInstances(frustum));
		if (showOctrees)
			instances.addAll(internalTree.getOctrees(frustum));
		return instances;
	}
}
