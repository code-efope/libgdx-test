package com.me.mygdxgame.gfx.sector;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.me.mygdxgame.gfx.model.CollidableModelInstance;
import com.me.mygdxgame.interfaces.Treeable;

public class BaseWall implements Treeable
{
	protected final static short[] ORDERED_INDICES =
	{ 0, 1, 2, 3 };
	protected final static int EDGES_PER_WALL = 4;
	protected Mesh mesh;
	protected Model model;
	protected CollidableModelInstance instance;
	protected BoundingBox bounds = new BoundingBox();

	protected BaseWall()
	{
	}

	public BaseWall(final Vector3[] edges, final int offset, final Color color,
		final Texture texture, final boolean hasNormal, final boolean blend)
	{
		float vertices[] = null;
		
		if (texture != null)
			vertices = new float[20];
		else
			vertices = new float[12];

		int vertIndex = 0;
		vertices[vertIndex++] = edges[offset].x;
		vertices[vertIndex++] = edges[offset].y;
		vertices[vertIndex++] = edges[offset].z;
		if (texture != null)
		{
			vertices[vertIndex++] = 1;
			vertices[vertIndex++] = 1;			
		}

		vertices[vertIndex++] = edges[offset + 1].x;
		vertices[vertIndex++] = edges[offset + 1].y;
		vertices[vertIndex++] = edges[offset + 1].z;
		if (texture != null)
		{
			vertices[vertIndex++] = 1;
			vertices[vertIndex++] = 0;			
		}

		vertices[vertIndex++] = edges[offset + 2].x;
		vertices[vertIndex++] = edges[offset + 2].y;
		vertices[vertIndex++] = edges[offset + 2].z;
		if (texture != null)
		{
			vertices[vertIndex++] = 0;
			vertices[vertIndex++] = 1;			
		}

		vertices[vertIndex++] = edges[offset + 3].x;
		vertices[vertIndex++] = edges[offset + 3].y;
		vertices[vertIndex++] = edges[offset + 3].z;
		if (texture != null)
		{
			vertices[vertIndex++] = 0;
			vertices[vertIndex++] = 0;			
		}

		createMesh(vertices, ORDERED_INDICES, color, texture, hasNormal, blend);
	}

	public BaseWall(final Vector3[] edges, final int offset, final Color color,
		final Texture texture)
	{
		this(edges, offset, color, texture, false, false);
	}

	public BaseWall(final Vector3[] edges, final int offset, final Color color, final Texture texture, final boolean hasNormal)
	{
		this(edges, offset, color, texture, hasNormal, false);
	}

	public BaseWall(final Vector3[] edges, final int offset, final Color color)
	{
		this(edges, offset, color, null, false, false);
	}

	public BaseWall(final Vector3[] edges, final int offset)
	{
		this(edges, offset, new Color(0.0f, 0.6f, 0.6f, 0.0f), null, false);
	}

	public BaseWall(final Vector3[] edges)
	{
		this(edges, 0, new Color(0.0f, 0.6f, 0.6f, 0.0f), null, false);
	}

	protected void createMesh(final float[] vertices, final short[] indices,
		final Color color, final Texture texture, final boolean hasNormal, final boolean blend)
	{
		ModelBuilder builder = new ModelBuilder();

		int valsPerVert = 3;

		if (texture != null)
			valsPerVert += 2;

		if (hasNormal)
			valsPerVert += 3;

		// set material
		Material mat = new Material();
		if (color != null)
			mat.set(ColorAttribute.createDiffuse(color));
		if (texture != null)
			mat.set(TextureAttribute.createDiffuse(texture));
		if (blend)
			mat.set(new BlendingAttribute(true, GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA, 1.0f));

		// create meshes
		if (texture != null && hasNormal)
		{
			mesh = new Mesh(true, valsPerVert * 4,
				4, //
				new VertexAttribute(Usage.Position, 3, "a_position"),
				new VertexAttribute(Usage.Normal, 3, "a_nomal"),
				new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
		}
		else if (texture != null)
		{
			mesh = new Mesh(true, valsPerVert * 4,
				4, //
				new VertexAttribute(Usage.Position, 3, "a_position"),
				new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));			
		}
		else
			mesh = new Mesh(true, valsPerVert * 4, 4, //
				new VertexAttribute(Usage.Position, 3, "a_position"));

		mesh.setVertices(vertices);
		mesh.setIndices(ORDERED_INDICES);

		builder.begin();
		builder.part("wall", mesh, GL10.GL_TRIANGLE_STRIP, mat);
		model = builder.end();

		if (texture != null)
			model.manageDisposable(texture);
		instance = new CollidableModelInstance(model, true);
		model.calculateBoundingBox(bounds);
//		instance.calculateBoundingBox(bounds);
	}

	@Override
	public BoundingBox getBounds()
	{
		return bounds;
	}
}
