package com.me.mygdxgame.scene.structure;

import java.util.Map.Entry;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
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
public class SimpleWall extends SimpleStructure
{
	private final Model wallBox, doorBox;

	public SimpleWall(float originX, float originY, float originZ, float sizeX, float sizeY, float sizeZ)
	{
		this.originX = originX;
		this.originY = originY;
		this.originZ = originZ;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
		
		for (int indexX = 0; indexX < sizeX; indexX++)
		{
			for (int indexY = 0; indexY < sizeY; indexY++)
			{
				for (int indexZ = 0; indexZ < sizeZ; indexZ++)
				{
					blocks.put(new Vector3(originX + indexX, originY + indexY, originZ + indexZ), 0);
				}
			}
		}
		wallBox = ModelBuilderUtil.getInstance().getBox(1.0f, 1.0f, 1.0f, TextureContainer.getTexture(2));
		doorBox = ModelBuilderUtil.getInstance().getBox(1.0f, 1.0f, 1.0f, TextureContainer.getTexture(1));
		createInstances();
	}

	private void createInstances()
	{
		instances.clear();
		for (Entry<Vector3, Integer> block: blocks.entrySet())
		{
			int val = block.getValue();
			Model box = null;

			switch (val)
			{
				case 0:
					box = wallBox;
					break;
					
				case 1:
					box = doorBox;
					break;
			}
			if (box != null)
				instances.add(new ModelInstance(box, block.getKey()));
		}
	}

	@Override
	public Array<ModelInstance> getInstances()
	{
		return instances;
	}

	public boolean insertDoor(int originX, int originY, int originZ, boolean remove)
	{
		boolean res = false;
		if ((originX >= this.originX) && (originX <= this.originX + sizeX) &&
			(originY >= this.originY) && (originY <= this.originY + sizeY) &&
			(originZ >= this.originZ) && (originZ <= this.originZ + sizeZ))
		{
			Vector3 testVector = new Vector3(originX, originY, originZ);
			if (blocks.containsKey(testVector))
			{
				if (remove)
					blocks.remove(testVector);
				else
					blocks.put(testVector, 1);
				createInstances();
				res = true;
			}
		}
		return res;
	}
}
