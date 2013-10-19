package com.me.mygdxgame.scene;

import java.nio.IntBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Disposable;

public class Skybox implements Disposable
{
	private static final int SKYBOX_TEXTURE_UNIT = 0;
	private static final int SKYBOX_TEXTURE_ACTIVE_UNIT = GL20.GL_TEXTURE0
		+ SKYBOX_TEXTURE_UNIT;

	private static final float ONE = 1f;

	private final static int NB_FACES = 6;
	private final static int QUAD_LENGTH = 4;

	// x, y, z
	private final static float[] VERTICES =
	{ //
	-ONE, -ONE, -ONE, //
		ONE, -ONE, -ONE, //
		-ONE, ONE, -ONE, //
		ONE, ONE, -ONE, //
		-ONE, -ONE, ONE, //
		ONE, -ONE, ONE, //
		-ONE, ONE, ONE, //
		ONE, ONE, ONE };

	// QUADS drawn with TRIANGLE_FAN
	private final static int[] INDICES =
	{ 5, 1, 7, 3, // positive x
		4, 0, 6, 2, // 0, 4, 2, 6, // negative x
		4, 5, 6, 7, // positive y
		1, 0, 3, 2, // negative y
		0, 1, 4, 5, // positive z
		3, 2, 7, 6 // negative z
	};

	private final static short[] ORDERED_INDICES =
	{ 0, 1, 2, 3 };

	private final int textureId;

	private Mesh[] meshes;

	private Matrix4 model;
	private Pixmap[] texturesSkyBox;
//	private ShaderProgram skyboxShader;

	public Skybox()
	{
		/*
		skyboxShader = new ShaderProgram(SkyboxShader.vertexShader,
			SkyboxShader.fragmentShader);
		if (skyboxShader.isCompiled() == false)
		{
			throw new RuntimeException("Could not load skybox shader: "
				+ skyboxShader.getLog());
		}
		*/

		texturesSkyBox = new Pixmap[6];

		// Image source: http://www.codemonsters.de/home/content.php?show=cubemaps
		texturesSkyBox[0] = new Pixmap( // right
			Gdx.files.internal("textures/brightday1_positive_x.png"));
		texturesSkyBox[1] = new Pixmap( // left
			Gdx.files.internal("textures/brightday1_negative_x.png"));
		texturesSkyBox[2] = new Pixmap( // top
			Gdx.files.internal("textures/brightday1_positive_y.png"));
		texturesSkyBox[3] = new Pixmap( // down
			Gdx.files.internal("textures/brightday1_negative_y.png"));
		texturesSkyBox[4] = new Pixmap( // front
			Gdx.files.internal("textures/brightday1_positive_z.png"));
		texturesSkyBox[5] = new Pixmap( // back
			Gdx.files.internal("textures/brightday1_negative_z.png"));

		this.meshes = new Mesh[NB_FACES];
		for (int i = 0; i < NB_FACES; i++)
		{
			Mesh mesh = new Mesh(true, QUAD_LENGTH * 3, QUAD_LENGTH,
				VertexAttribute.Position());
			mesh.setVertices(getVertices(i));
			mesh.setIndices(ORDERED_INDICES);
			meshes[i] = mesh;
		}

		// Generate a texture object
		IntBuffer buffer = BufferUtils.newIntBuffer(1);
		buffer.position(0);
		buffer.limit(buffer.capacity());
		Gdx.gl.glGenTextures(1, buffer);
		textureId = buffer.get(0);

		// Bind the texture object
		Gdx.gl.glActiveTexture(SKYBOX_TEXTURE_ACTIVE_UNIT);
		Gdx.gl.glBindTexture(GL20.GL_TEXTURE_CUBE_MAP, textureId);

		// Set the filtering mode
		// cf. http://www.opengl.org/sdk/docs/man/xhtml/glTexParameter.xml
		Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_CUBE_MAP, GL20.GL_TEXTURE_MIN_FILTER,
			GL20.GL_NEAREST);
		Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_CUBE_MAP, GL20.GL_TEXTURE_MAG_FILTER,
			GL20.GL_NEAREST);

		Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_CUBE_MAP, GL20.GL_TEXTURE_WRAP_S,
			GL20.GL_CLAMP_TO_EDGE);
		Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_CUBE_MAP, GL20.GL_TEXTURE_WRAP_T,
			GL20.GL_CLAMP_TO_EDGE);
		// Gdx.gl20.glTexParameteri(GL20.GL_TEXTURE_CUBE_MAP,
		// GL20.GL_TEXTURE_WRAP_R, GL20.GL_CLAMP_TO_EDGE);

		// Load cube faces
		for (int i = 0; i < 6; i++)
		{
			glTexImage2D(GL20.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, texturesSkyBox[i]);
		}

		// Create model matrix for rendering
		model = new Matrix4();

	}

	@Override
	public void dispose()
	{
		for (Mesh mesh : meshes)
			mesh.dispose();
		for (Pixmap texture : texturesSkyBox)
			texture.dispose();
	}

	public void render(Camera camera)
	{
		Gdx.gl.glDisable(GL10.GL_DEPTH_TEST);

		// Bind the texture
		Gdx.gl.glActiveTexture(SKYBOX_TEXTURE_ACTIVE_UNIT);
		Gdx.gl.glBindTexture(GL20.GL_TEXTURE_CUBE_MAP, textureId);

		model.setToTranslation(camera.position);
		model.rotate(1, 0, 0, 90);

//		shader.setUniformMatrix("u_M", model);
//		shader.setUniformMatrix("u_VP", camera.combined);
//		shader.setUniformi("u_sampler", SKYBOX_TEXTURE_UNIT);

		for (Mesh mesh : meshes)
			mesh.render(GL10.GL_TRIANGLE_STRIP);

		Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
	}

	private void glTexImage2D(int textureCubeMapIndex, Pixmap pixmap)
	{
		// cf. http://www.opengl.org/sdk/docs/man/xhtml/glTexImage2D.xml
		Gdx.gl.glTexImage2D(textureCubeMapIndex, 0, pixmap.getGLInternalFormat(),
			pixmap.getWidth(), pixmap.getHeight(), 0, pixmap.getGLFormat(),
			pixmap.getGLType(), pixmap.getPixels());
	}

	private float[] getVertices(int indexNb)
	{
		float[] vertices = new float[3 * QUAD_LENGTH];
		for (int i = 0; i < QUAD_LENGTH; i++)
		{
			int offset = INDICES[QUAD_LENGTH * indexNb + i];
			vertices[3 * i] = VERTICES[3 * offset]; // X
			vertices[3 * i + 1] = VERTICES[3 * offset + 1]; // Y
			vertices[3 * i + 2] = VERTICES[3 * offset + 2]; // Z
		}
		return vertices;
	}

	public int getCubeMapTextureUnit()
	{
		return SKYBOX_TEXTURE_UNIT;
	}
}