/**
 * 
 */
package com.me.mygdxgame.gfx.structure;

import com.badlogic.gdx.utils.Array;

/**
 * @author deDokter
 *
 */
public class StructureManager
{
	private Array<SimpleStructure> structures = new Array<SimpleStructure>();
	
	public StructureManager()
	{
		structures.add(new SimpleWall(0, 0, 0, 4, 1, 4));
		structures.add(new SimpleRoomWithDoors(1, 1, 1, 5, 4, 5));
	}
}
