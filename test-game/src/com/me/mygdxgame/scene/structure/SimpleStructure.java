package com.me.mygdxgame.scene.structure;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.scene.models.CollidableModelInstance;

public abstract class SimpleStructure implements SimpleStructureIf
{
	protected final Map<Vector3, Integer> blocks = new HashMap<Vector3, Integer>();
	protected float originX, originY, originZ;
	protected float sizeX, sizeY, sizeZ;
	protected final Array<CollidableModelInstance> instances = new Array<CollidableModelInstance>();
}
