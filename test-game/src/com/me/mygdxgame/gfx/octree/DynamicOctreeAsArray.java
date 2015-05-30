package com.me.mygdxgame.gfx.octree;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.gfx.model.CollidableModelInstance;
import com.me.mygdxgame.interfaces.Treeable;
import com.me.mygdxgame.util.Settings;

public class DynamicOctreeAsArray<T extends Treeable> extends BaseOctree<T>
{
	private final Vector3 center;
	private final float diameter;
	private final int maxInstances;
	private final int baseIndex;
	private final BoundingBox bounds;
	private Vector<DynamicOctreeAsArray<T>> children = new Vector<DynamicOctreeAsArray<T>>();
	private Array<T> instances = new Array<T>();
	private static final boolean talk = false;
	private final CollidableModelInstance internalGridInstance;

	public DynamicOctreeAsArray(Vector3 center, float diameter)
	{
		this(center, diameter, Settings.getOctreeMaxData());
	}

	public DynamicOctreeAsArray(Vector3 center, float diameter, int maxInstances)
	{
		this(center, diameter, maxInstances, 0);
	}

	public DynamicOctreeAsArray(Vector3 center, float diameter, int maxInstances,
		int baseIndex)
	{
		this.center = new Vector3(center);
		this.diameter = diameter;
		this.maxInstances = maxInstances;
		this.baseIndex = baseIndex;

		Vector3 min = new Vector3(center).sub(diameter);
		Vector3 max = new Vector3(center).add(diameter);
		this.bounds = new BoundingBox(min, max);

		ModelBuilder builder = new ModelBuilder();
		Model octreeModel = builder.createBox(diameter * 2, diameter * 2, diameter * 2,
			GL20.GL_LINES, new Material(), Usage.Position);
		internalGridInstance = new CollidableModelInstance(octreeModel, min, false);
	}

	private void createChildren()
	{
		Vector3 newCenter = new Vector3();
		float newDiameter = diameter / 2.0f;

		for (int childIndex = 0; childIndex < 8; childIndex++)
		{
			float xAdjust = newDiameter * ((childIndex & 0x01) * 2 - 1);
			float yAdjust = newDiameter * (((childIndex & 0x02) >> 1) * 2 - 1);
			float zAdjust = newDiameter * (((childIndex & 0x04) >> 2) * 2 - 1);
			newCenter.set(center.x - xAdjust, center.y - yAdjust, center.z - zAdjust);
			children.add(new DynamicOctreeAsArray<T>(newCenter, newDiameter,
				maxInstances, baseIndex + childIndex));
		}
		if (talk)
			Gdx.app.log(this.getClass().getName(), "" + diameter
				+ "is still too big, so i needed to create 8 new children with size "
				+ newDiameter);
	}

	private int getChildrenIndex(Vector3 position)
	{
		int childrenIndex = 0;
		if (position.x <= center.x)
			childrenIndex += 1;
		if (position.y <= center.y)
			childrenIndex += 2;
		if (position.z <= center.z)
			childrenIndex += 4;

		return childrenIndex;
	}

	@Override
	public boolean insert(T newInstance)
	{
		boolean res = true;
		Vector3 out = null;
		
		if (talk)
		{
			Gdx.app.log(this.getClass().getName(), "insert " + newInstance.getBounds().getCenter(out) + " into "
				+ center + ":" + diameter);
		}
		/* first insert the instance, when tree gets to big, just create children and move data */
		instances.add(newInstance);
		if (instances.size > maxInstances)
		{
			/* no children present -> create new */
			if (children == null)
			{
				createChildren();
				/* move all instances to children, including newly inserted instance */
				for (T instance : instances)
					res = children.get(baseIndex + getChildrenIndex(instance.getBounds().getCenter(out))).insert(instance);
				instances.clear();
			}
			/* just add to child */
			else
			{
				res = children.get(baseIndex + getChildrenIndex(newInstance.getBounds().getCenter(out))).insert(newInstance);
				if (res)
					instances.removeValue(newInstance, true);
			}
		}
		return res;
	}

	@Override
	public Array<T> getInstances(Frustum frustum)
	{
		Array<T> res = new Array<T>();

		if (frustum.boundsInFrustum(bounds))
		{
			if (children != null)
			{
				for (DynamicOctreeAsArray<T> child : children)
					res.addAll(child.getInstances(frustum));
			}
			if (instances.size != 0)
				res.addAll(instances);
		}
		return res;
	}

	@Override
	public Array<T> getInstances(Vector3 point)
	{
		return new Array<T>();
	}

	@Override
	public Array<CollidableModelInstance> getOctreeInstances(Frustum frustum)
	{
		Array<CollidableModelInstance> res = new Array<CollidableModelInstance>();

		if (frustum.boundsInFrustum(bounds))
		{
			if (children != null)
			{
				for (DynamicOctreeAsArray<T> child : children)
					res.addAll(child.getOctreeInstances(frustum));
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
			for (OctreeIf child : children)
				res += child.getLoad(getInstances);
		}
		/* get own data */
		if (getInstances)
			res += instances.size;
		else
			res += 1;
		return res;
	}
}
