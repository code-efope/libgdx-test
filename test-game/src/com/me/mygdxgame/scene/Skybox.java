/* taken from https://github.com/recastrodiaz/java-webgl/blob/master/CGLab04/src/com/icyhill/cglab04/Skybox.java */
package com.me.mygdxgame.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.utils.Disposable;

public class Skybox implements Disposable
{
	private static final float SKYBOX_SIZE = 100f;
	private static final int NB_FLOATS_PER_FACE = 5;
	private static final int NB_FACES = 6;
	private static final int QUAD_LENGTH = 4;

	// x, y, z, tx, ty
	private final static float[] VERTICES =
	{
		// coordinates for vertices including texture coords
		-SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE, 0f, 1f, // A
		-SKYBOX_SIZE, SKYBOX_SIZE, -SKYBOX_SIZE, 0f, 0f, // B
		SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE, 1f, 1f, // C
		SKYBOX_SIZE, SKYBOX_SIZE, -SKYBOX_SIZE, 1f, 0f, // D

		SKYBOX_SIZE, -SKYBOX_SIZE, SKYBOX_SIZE, 0f, 1f, // E
		SKYBOX_SIZE, SKYBOX_SIZE, SKYBOX_SIZE, 0f, 0f, // F
		-SKYBOX_SIZE, -SKYBOX_SIZE, SKYBOX_SIZE, 1f, 1f, // G
		-SKYBOX_SIZE, SKYBOX_SIZE, SKYBOX_SIZE, 1f, 0f, // H

		SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE, 0f, 1f, // C
		SKYBOX_SIZE, SKYBOX_SIZE, -SKYBOX_SIZE, 0f, 0f, // D
		SKYBOX_SIZE, -SKYBOX_SIZE, SKYBOX_SIZE, 1f, 1f, // E
		SKYBOX_SIZE, SKYBOX_SIZE, SKYBOX_SIZE, 1f, 0f, // F

		-SKYBOX_SIZE, -SKYBOX_SIZE, SKYBOX_SIZE, 0f, 1f, // G
		-SKYBOX_SIZE, SKYBOX_SIZE, SKYBOX_SIZE, 0f, 0f, // H
		-SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE, 1f, 1f, // A
		-SKYBOX_SIZE, SKYBOX_SIZE, -SKYBOX_SIZE, 1f, 0f, // B		

		-SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE, 1f, 0f, // A
		-SKYBOX_SIZE, -SKYBOX_SIZE, SKYBOX_SIZE, 0f, 0f, // G
		SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE, 1f, 1f, // C
		SKYBOX_SIZE, -SKYBOX_SIZE, SKYBOX_SIZE, 0f, 1f, // E

		-SKYBOX_SIZE, SKYBOX_SIZE, -SKYBOX_SIZE, 1f, 1f, // B
		-SKYBOX_SIZE, SKYBOX_SIZE, SKYBOX_SIZE, 0f, 1f, // H
		SKYBOX_SIZE, SKYBOX_SIZE, -SKYBOX_SIZE, 1f, 0f, // D
		SKYBOX_SIZE, SKYBOX_SIZE, SKYBOX_SIZE, 0f, 0f, // F
	};

	private final static short[] ORDERED_INDICES =
	{ 0, 1, 2, 3 };

	private Mesh[] meshes = new Mesh[NB_FACES];
	private final Texture[] textures = new Texture[NB_FACES];
	private final static String textureFolder = "textures/";
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
	 * initialize vertices and setup meshes
	 */
	public Skybox()
	{
		for (int i = 0; i < NB_FACES; i++)
		{
			textures[i] = new Texture(Gdx.files.internal(textureFolder + textureNames[i]));
			Mesh mesh = new Mesh(true, QUAD_LENGTH * 5, QUAD_LENGTH, //
				new VertexAttribute(Usage.Position, 3, "a_position"), //
				new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
			mesh.setVertices(getVertices(i));
			mesh.setIndices(ORDERED_INDICES);
			meshes[i] = mesh;
		}
	}

	private float[] getVertices(int indexNb)
	{
		float[] vertices = new float[NB_FLOATS_PER_FACE * QUAD_LENGTH];
		for (int i = 0; i < QUAD_LENGTH; i++)
		{
			int offset = QUAD_LENGTH * indexNb + i;
			for (int v = 0; v < NB_FLOATS_PER_FACE; v++)
				vertices[NB_FLOATS_PER_FACE * i + v] = VERTICES[NB_FLOATS_PER_FACE * offset + v];
		}
		return vertices;
	}

	public void render()
	{
		Gdx.gl.glDisable(GL10.GL_DEPTH_TEST);
		for (int i = 0; i < NB_FACES; i++)
		{
			textures[i].bind();
			meshes[i].render(GL10.GL_TRIANGLE_STRIP);
		}
		Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
	}

	@Override
	public void dispose()
	{
		for (Mesh mesh : meshes)
			mesh.dispose();
		for (Texture texture : textures)
			texture.dispose();
	}
}