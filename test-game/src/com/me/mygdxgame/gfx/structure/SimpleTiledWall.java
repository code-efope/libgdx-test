package com.me.mygdxgame.gfx.structure;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.me.mygdxgame.gfx.model.CollidableModelInstance;
import com.me.mygdxgame.gfx.sector.BaseWall;
import com.me.mygdxgame.util.TextureProvider;

public class SimpleTiledWall extends BaseWall implements Disposable
{
	private static final float SIZEX = 1.0f;
	private static final float SIZEY = 1.0f;
	private static final float SIZEZ = 1.0f;
	private static final int NB_FACES = 6;
	private static final int NB_POINTS = 4;
	private static final int NB_SCALES = 3;

	private final static Vector3[] edgesBase =
	{
		// front
		new Vector3(SIZEX, -SIZEY, SIZEZ), // c
		new Vector3(SIZEX, SIZEY, SIZEZ), // d
		new Vector3(-SIZEX, -SIZEY, SIZEZ), // a
		new Vector3(-SIZEX, SIZEY, SIZEZ), // b

		// back
		new Vector3(-SIZEX, -SIZEY, -SIZEZ),
		new Vector3(-SIZEX, SIZEY, -SIZEZ),
		new Vector3(SIZEX, -SIZEY, -SIZEZ),
		new Vector3(SIZEX, SIZEY, -SIZEZ),

		// left
		new Vector3(-SIZEX, -SIZEY, SIZEZ),
		new Vector3(-SIZEX, SIZEY, SIZEZ),
		new Vector3(-SIZEX, -SIZEY, -SIZEZ),
		new Vector3(-SIZEX, SIZEY, -SIZEZ),

		// right
		new Vector3(SIZEX, -SIZEY, -SIZEZ),
		new Vector3(SIZEX, SIZEY, -SIZEZ),
		new Vector3(SIZEX, -SIZEY, SIZEZ),
		new Vector3(SIZEX, SIZEY, SIZEZ),

		// top
		new Vector3(SIZEX, SIZEY, -SIZEZ),
		new Vector3(-SIZEX, SIZEY, -SIZEZ),
		new Vector3(SIZEX, SIZEY, SIZEZ),
		new Vector3(-SIZEX, SIZEY, SIZEZ),

		// bottom
		new Vector3(-SIZEX, -SIZEY, -SIZEZ),
		new Vector3(SIZEX, -SIZEY, -SIZEZ),
		new Vector3(-SIZEX, -SIZEY, SIZEZ),
		new Vector3(SIZEX, -SIZEY, SIZEZ),
	};

	private final static Vector2[] uvBase =
	{
		new Vector2(1.0f, 1.0f),
		new Vector2(1.0f, 0.0f),
		new Vector2(0.0f, 1.0f),
		new Vector2(0.0f, 0.0f)
	};

	private final Array<CollidableModelInstance> wallInstances = new Array<CollidableModelInstance>();
	private final Texture texture = TextureProvider.getTexture(2);
	private Vector2[] uv = new Vector2[NB_FACES * 4];
	private Vector2[] uvScales = new Vector2[NB_SCALES];
	private Vector3[] wallEdges = new Vector3[NB_FACES * 4];

	public SimpleTiledWall(float originX, float originY, float originZ,
			float sizeX, float sizeY, float sizeZ)
	{
		this(new Vector3(originX, originY, originZ), new Vector3(sizeX, sizeY,
				sizeZ));
	}

	public SimpleTiledWall(Vector3 origin, Vector3 size)
	{
		calcEdges(origin, size);
		calcUV(size);
		texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		for (int i = 0; i < NB_FACES; i++)
		{
			BaseWall wall = new BaseWall(wallEdges, i * 4, new Color(1.0f,
					1.0f, 7.0f, 1.0f), texture, false, false, uv);
			wallInstances.add(wall.instance);
		}
	}

	/**
	 * initialize vertices and create instances
	 */
	private void calcEdges(final Vector3 origin, final Vector3 size)
	{
		for (int edgesIndex = 0; edgesIndex < edgesBase.length; edgesIndex++)
		{
			wallEdges[edgesIndex] = new Vector3(origin.x
					+ edgesBase[edgesIndex].x * size.x, origin.y
					+ edgesBase[edgesIndex].y * size.y, origin.z
					+ edgesBase[edgesIndex].z * size.z);
		}
	}

	private void calcUV(Vector3 size)
	{
		uvScales[0] = new Vector2(size.x, size.y);
		uvScales[1] = new Vector2(size.z, size.y);
		uvScales[2] = new Vector2(size.z, size.x);

		int scaleIndex = 0;
		for (int faceIndex = 0; faceIndex < NB_FACES; faceIndex++)
		{
			for (int vertexIndex = 0; vertexIndex < NB_POINTS; vertexIndex++)
			{
				uv[faceIndex * 4 + vertexIndex] = new Vector2();
				uv[faceIndex * 4 + vertexIndex].x = uvBase[vertexIndex].x
						* uvScales[scaleIndex].x;
				uv[faceIndex * 4 + vertexIndex].y = uvBase[vertexIndex].y
						* uvScales[scaleIndex].y;
			}
			if (faceIndex % 2 == 1)
				scaleIndex++;
		}
	}

	@Override
	public Array<CollidableModelInstance> getInstances()
	{
		return wallInstances;
	}

	@Override
	public void dispose()
	{
		texture.dispose();
	}
};