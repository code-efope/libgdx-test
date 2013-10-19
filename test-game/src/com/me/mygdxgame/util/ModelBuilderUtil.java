package com.me.mygdxgame.util;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.materials.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class ModelBuilderUtil
{
	private static ModelBuilderUtil instance;
	private final ModelBuilder builder = new ModelBuilder();

	private ModelBuilderUtil() {}
	public static ModelBuilderUtil getInstance()
	{
		if (instance == null)
		{
			synchronized(ModelBuilderUtil.class)
			{
				if (instance == null)
				{
					instance = new ModelBuilderUtil();
				}
			}
		}
		return instance;
	}

	public Model getBox(float sizeX, float sizeY, float sizeZ, Texture texture)
	{
		return getBox(sizeX, sizeY, sizeZ, GL10.GL_TRIANGLES, texture);
	}

	public Model getBox(float sizeX, float sizeY, float sizeZ, int primitiveType, Texture texture)
	{
		Model box; 
		if (texture != null)
		{
			box = builder.createBox(sizeX, sizeY, sizeZ, primitiveType, new Material(TextureAttribute.createDiffuse(texture)),
					Usage.Position | Usage.Normal | Usage.TextureCoordinates);
			box.manageDisposable(texture);
		}
		else
			box = builder.createBox(sizeX, sizeY, sizeZ, new Material(), Usage.Position);
		return box;			
	}
}
