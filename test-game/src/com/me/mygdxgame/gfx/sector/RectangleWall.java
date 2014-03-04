package com.me.mygdxgame.gfx.sector;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;
import com.me.mygdxgame.util.TextureProvider;

/**
 * creates rectangle mesh in y-direction (i.e. walls)
 * 
 * @author deDokter
 * 
 */
public class RectangleWall extends BaseWall
{
	public RectangleWall(final Vector3 v1, final Vector3 v2)
	{
		this(v1, v2, new Color(0.0f, 0.0f, 1.0f, 1.0f), null, false);
	}

	public RectangleWall(final Vector3 v1, final Vector3 v2, final Color color)
	{
		this(v1, v2, color, TextureProvider.getTextureName(1), false);
	}

	public RectangleWall(final Vector3 v1, final Vector3 v2, final Color color,
		final String texName)
	{
		this(v1, v2, new Color(1.0f, 1.0f, 1.0f, 1.0f), texName, false);
	}

	public RectangleWall(final Vector3 v1, final Vector3 v2, final Color color,
		final String texName, boolean blend)
	{
		float vertices[] = new float[20];

		float tu = 0, tv = 0, tu2 = 0, tv2 = 0;
		int vertexIndex = 0;
		Texture texture = null;
		AtlasRegion atlas = TextureProvider.getTextureFromAtlas(texName);
		if (atlas != null)
			texture = TextureProvider.getAtlasTexture();

		if (texture != null)
		{
			tu = atlas.getU();
			tu2 = atlas.getU2();
			tv = atlas.getV();
			tv2 = atlas.getV2();
		}

		vertices[vertexIndex++] = v1.x;
		vertices[vertexIndex++] = v1.y;
		vertices[vertexIndex++] = v1.z;

		if (texture != null)
		{
			vertices[vertexIndex++] = tu2;
			vertices[vertexIndex++] = tv2;
		}
		vertices[vertexIndex++] = v1.x;
		vertices[vertexIndex++] = v2.y;
		vertices[vertexIndex++] = v1.z;

		if (texture != null)
		{
			vertices[vertexIndex++] = tu2;
			vertices[vertexIndex++] = tv;
		}
		vertices[vertexIndex++] = v2.x;
		vertices[vertexIndex++] = v1.y;
		vertices[vertexIndex++] = v2.z;

		if (texture != null)
		{
			vertices[vertexIndex++] = tu;
			vertices[vertexIndex++] = tv2;
		}
		vertices[vertexIndex++] = v2.x;
		vertices[vertexIndex++] = v2.y;
		vertices[vertexIndex++] = v2.z;

		if (texture != null)
		{
			vertices[vertexIndex++] = tu;
			vertices[vertexIndex++] = tv;
		}
		createMesh(vertices, ORDERED_INDICES, color, texture, false, blend);
	}
}
