/* taken from https://github.com/recastrodiaz/java-webgl/blob/master/CGLab04/src/com/icyhill/cglab04/Skybox.java */
package com.me.mygdxgame.gfx.sector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.me.mygdxgame.gfx.model.CollidableModelInstance;

public class Skybox extends RectangleSector implements Disposable
{
	private static final float SKYBOX_SIZE = 100f;
	private static final int NB_FACES = 6;

	private final static Vector3[] wallEdges =
	{
		new Vector3(SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE), // c
		new Vector3(SKYBOX_SIZE, SKYBOX_SIZE, -SKYBOX_SIZE), // d
		new Vector3(-SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE), //a
		new Vector3(-SKYBOX_SIZE, SKYBOX_SIZE, -SKYBOX_SIZE), // b

		new Vector3(-SKYBOX_SIZE, -SKYBOX_SIZE, SKYBOX_SIZE),
		new Vector3(-SKYBOX_SIZE, SKYBOX_SIZE, SKYBOX_SIZE),
		new Vector3(SKYBOX_SIZE, -SKYBOX_SIZE, SKYBOX_SIZE),
		new Vector3(SKYBOX_SIZE, SKYBOX_SIZE, SKYBOX_SIZE),

		new Vector3(SKYBOX_SIZE, -SKYBOX_SIZE, SKYBOX_SIZE),
		new Vector3(SKYBOX_SIZE, SKYBOX_SIZE, SKYBOX_SIZE),
		new Vector3(SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE),
		new Vector3(SKYBOX_SIZE, SKYBOX_SIZE, -SKYBOX_SIZE),

		new Vector3(-SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE),
		new Vector3(-SKYBOX_SIZE, SKYBOX_SIZE, -SKYBOX_SIZE),
		new Vector3(-SKYBOX_SIZE, -SKYBOX_SIZE, SKYBOX_SIZE),
		new Vector3(-SKYBOX_SIZE, SKYBOX_SIZE, SKYBOX_SIZE),

		new Vector3(SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE),
		new Vector3(-SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE),
		new Vector3(SKYBOX_SIZE, -SKYBOX_SIZE, SKYBOX_SIZE),
		new Vector3(-SKYBOX_SIZE, -SKYBOX_SIZE, SKYBOX_SIZE),

		new Vector3(-SKYBOX_SIZE, SKYBOX_SIZE, -SKYBOX_SIZE),
		new Vector3(SKYBOX_SIZE, SKYBOX_SIZE, -SKYBOX_SIZE),
		new Vector3(-SKYBOX_SIZE, SKYBOX_SIZE, SKYBOX_SIZE),
		new Vector3(SKYBOX_SIZE, SKYBOX_SIZE, SKYBOX_SIZE),
	};

	private final Array<CollidableModelInstance> wallInstances = new Array<CollidableModelInstance>();
	private final Texture[] textures = new Texture[NB_FACES];
	private final static String textureFolder = "textures/skybox/";
	private final static String[] textureNames =
	{
		// texture names
		"brightday1_positive_x.png", //
		"brightday1_negative_x.png", //
		"brightday1_negative_z.png", //
		"brightday1_positive_z.png", //
		"brightday1_negative_y.png", //
		"brightday1_positive_y.png" //
	};

	/**
	 * initialize vertices and create instances
	 */
	public Skybox()
	{
		for (int i = 0; i < NB_FACES; i++)
		{
			textures[i] = new Texture(Gdx.files.internal(textureFolder + textureNames[i]));
			addWall(new BaseWall(wallEdges, i * 4, new Color(1.0f, 1.0f, 7.0f, 1.0f), textures[i]));
		}

		for (BaseWall wall : walls)
			wallInstances.add(wall.instance);
	}

	@Override
	public Array<CollidableModelInstance> getAllInstances(final Camera camera)
	{
		// FIXME the box shakes a bit on changing the position
		for (CollidableModelInstance instance: wallInstances)
			instance.transform.setToTranslation(camera.position);
		return wallInstances;
	}

	@Override
	public void dispose()
	{
		for (Texture texture : textures)
			texture.dispose();
	}
}