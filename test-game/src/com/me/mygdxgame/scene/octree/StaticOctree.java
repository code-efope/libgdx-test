package com.me.mygdxgame.scene.octree;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.scene.models.CollidableModelInstance;
import com.me.mygdxgame.util.Settings;

/**
 * o x ------------>
 * y ---------------
 * | |......|......|
 * | |.0(4).| 1(5).|
 * | |......|......|
 * | -------o-------
 * | |......|......|
 * | |.2(6).|.3(7).|
 * | |......|......|
 * v ---------------
 * 
 * binary interpretation of octtree index:
 * MSB -> z y x <- LSB
 * 
 * this means:
 * 0 -> 0,0,0
 * 1 -> 0,0,1
 * 2 -> 0,1,0
 * 3 -> 0,1,1
 * .
 * .
 * 7 -> 1,1,1
 * 
 * distance between center of parent and each child is
 * parentSize / 4
 * 
 */
public class StaticOctree implements OctreeIf
{
	private final Vector3 center;
	private final float diameter;
	private final BoundingBox bounds;
	private StaticOctree children[] = null;
	private Array<CollidableModelInstance> instances = new Array<CollidableModelInstance>();
	private static final boolean talk = false;
	private final CollidableModelInstance internalGridInstance;

/*	public StaticOctree(Vector3 min, Vector3 max)
	{
		Vector3 center = new Vector3(Math.abs(max.x - min.x), Math.abs(max.y - min.y), Math.abs(max.z - min.z));
		
		this(center, diamater);
	}*/

	public StaticOctree(Vector3 center, float diameter)
	{
		this.center = new Vector3(center);
		this.diameter = diameter;

		Vector3 min = new Vector3(center);
		min.sub(diameter);
		Vector3 max = new Vector3(center);
		max.add(diameter);
		this.bounds = new BoundingBox(min, max);

		ModelBuilder builder = new ModelBuilder();
		Model octreeModel = builder.createBox(diameter * 2, diameter * 2, diameter * 2, GL10.GL_LINES, new Material(), Usage.Position);
		internalGridInstance = new CollidableModelInstance(octreeModel, min, false);

		if (diameter <= Settings.getOctreeMinSize())
		{
			if (talk)
				Gdx.app.log(this.getClass().getName(), "i'm a new child and i'm at " + center.x + "/" + center.y + "/" + center.z + " and my size is " + diameter);
		}
		else
			createChildren();
	}

	private void createChildren()
	{
		Vector3 newCenter = new Vector3();
		float newDiameter = diameter / 2.0f;

		children = new StaticOctree[8];
		for (int childIndex = 0; childIndex < 8; childIndex++)
		{
			float xAdjust = newDiameter * ((childIndex & 0x01) * 2 - 1);
			float yAdjust = newDiameter * (((childIndex & 0x02) >> 1) * 2 - 1);
			float zAdjust = newDiameter * (((childIndex & 0x04) >> 2) * 2 - 1);
			newCenter.set(center.x - xAdjust, center.y - yAdjust, center.z - zAdjust);
			children[childIndex] = new StaticOctree(newCenter, newDiameter);
		}
		if (talk)
			Gdx.app.log(this.getClass().getName(), "" + diameter + "is still too big, so i needed to create 8 new children with size " + newDiameter);
	}

	@Override
	public boolean insert(CollidableModelInstance instance)
	{
		Renderable ren = new Renderable();
		Vector3 position = new Vector3();
		instance.getRenderable(ren);
		ren.worldTransform.getTranslation(position);
		if (Math.abs(position.x - center.x) <= diameter &&
			Math.abs(position.y - center.y) <= diameter &&
			Math.abs(position.z - center.z) <= diameter)
		{
			// if children are available
			if (children != null)
			{
				for (StaticOctree child : children)
				{
					if (child.insert(instance))
						return true;
				}
				return false;
			}
			// we're at the last level
			else
			{
				if (talk)
					Gdx.app.log(this.getClass().getName(), "Heureka, i (" + center.x + "/" + center.y + "/" + center.z + "/" + diameter + ") can take " + position.x + "/" + position.y + "/" + position.z);
				instances.add(instance);
				return true;
			}
		}
		return false;
	}

	@Override
	public Array<CollidableModelInstance> getInstances(Frustum frustum)
	{
		Array<CollidableModelInstance> res = new Array<CollidableModelInstance>();

		if (frustum.boundsInFrustum(bounds))
		{
			if (children != null)
			{
				for (StaticOctree child: children)
					res.addAll(child.getInstances(frustum));
			}
			else
			{
				if (instances.size != 0)
					res.addAll(instances);
			}
		}
		return res;
	}

	@Override
	public Array<CollidableModelInstance> getOctrees(Frustum frustum)
	{
		Array<CollidableModelInstance> res = new Array<CollidableModelInstance>();

		if (frustum.boundsInFrustum(bounds))
		{
			if (children != null)
			{
				for (StaticOctree child: children)
					res.addAll(child.getOctrees(frustum));
			}
			else
			{
				res.add(internalGridInstance);
			}
		}
		return res;
	}

	@Override
	public int getLoad(boolean getInstances)
	{
		int res = getInstances ? 0 : 1;
		if (children != null)
		{
			for (StaticOctree child: children)
				res += child.getLoad(getInstances);
		}
		else
		{
			if (getInstances)
				res += instances.size;
			else
				res++;
		}
		return res;
	}
}
