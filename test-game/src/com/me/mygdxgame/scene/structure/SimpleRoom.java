package com.me.mygdxgame.scene.structure;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Array;

/**
 * xxxxx
 * x___x
 * x___x
 * x___x
 * xxxxx
 * 
 */
public class SimpleRoom extends SimpleStructure
{
	private SimpleWall walls[];

	public SimpleRoom(int originX, int originY, int originZ, int sizeX, int sizeY,
		int sizeZ)
	{
		this.originX = originX;
		this.originY = originY;
		this.originZ = originZ;

		/* starting and ending coords (x, z) y is set through sizeY */
		int wallCoords[] =
		{ 0, 0, 1, sizeZ - 1, 0, sizeZ - 1, sizeX - 1, 1, sizeX - 1, 1, 1, sizeZ - 1, 1,
			0, sizeX - 1, 1 };
		walls = new SimpleWall[6];

		/* floor and ceiling */
		walls[0] = new SimpleWall(originX, originY, originZ, sizeX, 1, sizeZ);
		walls[1] = new SimpleWall(originX, originY + sizeY - 1, originZ, sizeX, 1, sizeZ);
		for (int wallIndex = 0; wallIndex < 4; wallIndex++)
		{
			walls[wallIndex + 2] = new SimpleWall(originX + wallCoords[wallIndex * 4],
				originY + 1, originZ + wallCoords[wallIndex * 4 + 1],
				wallCoords[wallIndex * 4 + 2], sizeY - 2, wallCoords[wallIndex * 4 + 3]);
		}
		walls[2].insertDoor(originX, originY + 1, originZ + (sizeZ / 2), true);
//			Gdx.app.log(this.getClass().getName(), "door inserted");
		walls[2].insertDoor(originX, originY + 2, originZ + (sizeZ / 2), true);
//			Gdx.app.log(this.getClass().getName(), "door inserted");
	}

	@Override
	public Array<ModelInstance> getInstances()
	{
		Array<ModelInstance> instances = new Array<ModelInstance>();
		for (SimpleWall wall : walls)
			instances.addAll(wall.getInstances());
		return instances;
	}
}
