package com.me.mygdxgame.gfx.structure;

import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.gfx.model.CollidableModelInstance;

/**
 * 
 * xx xx
 * x x
 * 
 * x x
 * xx xx
 * 
 */
public class SimpleRoomWithDoors extends SimpleStructure
{
	private SimpleWall walls[];

	public SimpleRoomWithDoors(int originX, int originY, int originZ, int sizeX,
		int sizeY, int sizeZ)
	{
		this.originX = originX;
		this.originY = originY;
		this.originZ = originZ;

		/* starting and ending coords (x, z) y is set through sizeY */
		int wallCoords[] =
		{ 1, 0, 0, 1, 0, sizeZ - 2, 1, sizeZ - 1, sizeX - 2, sizeZ - 1, sizeX - 1,
			sizeZ - 2, sizeX - 1, 1, sizeX - 2, 0 };
		walls = new SimpleWall[10];

		/* floor and ceiling */
		walls[0] = new SimpleWall(originX, originY, originZ, sizeX, 1, sizeZ);
		walls[1] = new SimpleWall(originX, originY + sizeY - 1, originZ, sizeX, 1, sizeZ);
		for (int wallIndex = 0; wallIndex < 8; wallIndex++)
		{
			walls[wallIndex + 2] = new SimpleWall(originX + wallCoords[wallIndex * 2],
				originY + 1, originZ + wallCoords[wallIndex * 2 + 1], 1, sizeY - 2, 1);
		}
	}

	@Override
	public Array<CollidableModelInstance> getInstances()
	{
		Array<CollidableModelInstance> instances = new Array<CollidableModelInstance>();
		for (SimpleWall wall : walls)
			instances.addAll(wall.getInstances());
		return instances;
	}
}
