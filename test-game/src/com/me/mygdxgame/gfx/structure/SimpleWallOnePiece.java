package com.me.mygdxgame.gfx.structure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.gfx.model.CollidableModelInstance;
import com.me.mygdxgame.util.ModelBuilderUtil;
import com.me.mygdxgame.util.TextureProvider;

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
	private final Texture wallTex = TextureProvider.getTexture(0);

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
		instances.add(new CollidableModelInstance(wallBox, new Vector3(originX, originY, originZ), true));
	}

	@Override
	public Array<CollidableModelInstance> getInstances()
	{
		return instances;
	}
}
