package com.me.mygdxgame.scene.structure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.texture.TextureContainer;
import com.me.mygdxgame.util.ModelBuilderUtil;

/**
 * 
 * xxxx
 * xxxx
 * xxxx
 *
 */
public class SimpleWallOnePiece extends SimpleStructure
{
	private final Model wallBox;
	private final Texture wallTex = TextureContainer.getTexture(0);

	public SimpleWallOnePiece(float originX, float originY, float originZ, float sizeX, float sizeY, float sizeZ)
	{
		this.originX = originX;
		this.originY = originY;
		this.originZ = originZ;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;

		wallTex.setWrap(TextureWrap.MirroredRepeat, TextureWrap.MirroredRepeat);
//		wallTex.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		wallBox = ModelBuilderUtil.getInstance().getBox(sizeX, sizeY, sizeZ, wallTex);
		createInstances();
	}

	private void createInstances()
	{
		instances.clear();
		instances.add(new ModelInstance(wallBox, originX, originY, originZ));
	}

	@Override
	public Array<ModelInstance> getInstances()
	{
		return instances;
	}
}
