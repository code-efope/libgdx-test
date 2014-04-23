package com.me.mygdxgame.gfx.structure;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.me.mygdxgame.gfx.model.CollidableModelInstance;
import com.me.mygdxgame.gfx.sector.BaseWall;
import com.me.mygdxgame.gfx.sector.RectangleSector;
import com.me.mygdxgame.util.TextureProvider;

public class SimpleTiledWall extends RectangleSector implements Disposable
{
	private static final float SKYBOX_SIZE = 5f;
	private static final float SIZEX = 3f;
	private static final float SIZEY = 2f;
	private static final int NB_FACES = 6;

	private final static Vector3[] wallEdges =
	{
		// front
		new Vector3(SIZEX, -SIZEY, SKYBOX_SIZE), // c
		new Vector3(SIZEX, SIZEY, SKYBOX_SIZE), // d
		new Vector3(-SIZEX, -SIZEY, SKYBOX_SIZE), //a
		new Vector3(-SIZEX, SIZEY, SKYBOX_SIZE), // b

		// back
		new Vector3(-SIZEX, -SIZEY, -SKYBOX_SIZE),
		new Vector3(-SIZEX, SIZEY, -SKYBOX_SIZE),
		new Vector3(SIZEX, -SIZEY, -SKYBOX_SIZE),
		new Vector3(SIZEX, SIZEY, -SKYBOX_SIZE),

		// left
		new Vector3(-SIZEX, -SIZEY, SKYBOX_SIZE),
		new Vector3(-SIZEX, SIZEY, SKYBOX_SIZE),
		new Vector3(-SIZEX, -SIZEY, -SKYBOX_SIZE),
		new Vector3(-SIZEX, SIZEY, -SKYBOX_SIZE),

		// right
		new Vector3(SIZEX, -SIZEY, -SKYBOX_SIZE),
		new Vector3(SIZEX, SIZEY, -SKYBOX_SIZE),
		new Vector3(SIZEX, -SIZEY, SKYBOX_SIZE),
		new Vector3(SIZEX, SIZEY, SKYBOX_SIZE),

		// top
		new Vector3(SIZEX, SIZEY, -SKYBOX_SIZE),
		new Vector3(-SIZEX, SIZEY, -SKYBOX_SIZE),
		new Vector3(SIZEX, SIZEY, SKYBOX_SIZE),
		new Vector3(-SIZEX, SIZEY, SKYBOX_SIZE),

		// bottom
		new Vector3(-SIZEX, -SIZEY, -SKYBOX_SIZE),
		new Vector3(SIZEX, -SIZEY, -SKYBOX_SIZE),
		new Vector3(-SIZEX, -SIZEY, SKYBOX_SIZE),
		new Vector3(SIZEX, -SIZEY, SKYBOX_SIZE),
	};

	private final static Vector2[] uv =
	{
		new Vector2(1.0f, 1.0f),
		new Vector2(1.0f, 0.0f),
		new Vector2(0.0f, 1.0f),
		new Vector2(0.0f, 0.0f),

		new Vector2(1.0f, 1.0f),
		new Vector2(1.0f, 0.0f),
		new Vector2(0.0f, 1.0f),
		new Vector2(0.0f, 0.0f),

		new Vector2(1.0f, 1.0f),
		new Vector2(1.0f, 0.0f),
		new Vector2(0.0f, 1.0f),
		new Vector2(0.0f, 0.0f),

		new Vector2(1.0f, 1.0f),
		new Vector2(1.0f, 0.0f),
		new Vector2(0.0f, 1.0f),
		new Vector2(0.0f, 0.0f),

		new Vector2(1.0f, 1.0f),
		new Vector2(1.0f, 0.0f),
		new Vector2(0.0f, 1.0f),
		new Vector2(0.0f, 0.0f),

		new Vector2(1.0f, 1.0f),
		new Vector2(1.0f, 0.0f),
		new Vector2(0.0f, 1.0f),
		new Vector2(0.0f, 0.0f),
	};

	private final Array<CollidableModelInstance> wallInstances = new Array<CollidableModelInstance>();
	private final Texture texture = TextureProvider.getTexture(0);

	/**
	 * initialize vertices and create instances
	 */
	public SimpleTiledWall(float originX, float originY, float originZ, float sizeX, float sizeY, float sizeZ)
	{
		for (int i = 0; i < NB_FACES; i++)
			addWall(new BaseWall(wallEdges, i * 4, new Color(1.0f, 1.0f, 7.0f, 1.0f), texture, false, false, uv));

		for (BaseWall wall : walls)
			wallInstances.add(wall.instance);
	}

	@Override
	public Array<CollidableModelInstance> getAllInstances(final Camera camera)
	{
		// FIXME the box shakes a bit on changing the position
		return wallInstances;
	}

	@Override
	public void dispose()
	{
		texture.dispose();
	}
};