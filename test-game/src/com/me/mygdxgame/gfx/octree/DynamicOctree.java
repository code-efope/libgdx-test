package com.me.mygdxgame.gfx.octree;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.gfx.model.CollidableModelInstance;
import com.me.mygdxgame.interfaces.Treeable;
import com.me.mygdxgame.util.ModelBuilderUtil;
import com.me.mygdxgame.util.Settings;

/**
 * instead of pre partition the complete space before inserting any data to the tree, this tree gets partitioned while
 * data gets inserted. when creating the tree it gets passed the size and the maximum number of data points inside a
 * leaf. when the number of data points exceeds the given maximum, the current node gets partitioned
 **/
public class DynamicOctree<T extends Treeable> extends BaseOctree<T>
{
	private final String ClassName = this.getClass().getSimpleName();
	private final Vector3 center;
	private final float diameter;
	private final int maxInstances;
	private final BoundingBox bounds;
	private DynamicOctree<T> children[] = null;
	private final Array<T> instances = new Array<T>();
	private static final boolean talk = false;
	private final CollidableModelInstance internalGridInstance;

	public DynamicOctree(final Vector3 center, final float diameter)
	{
		this(center, diameter, Settings.getOctreeMaxData());
	}

	public DynamicOctree(final Vector3 center, final float diameter,
		final int maxInstances)
	{
		this.center = new Vector3(center);
		this.diameter = diameter;
		this.maxInstances = maxInstances;

		Vector3 min = new Vector3(center).sub(diameter);
		Vector3 max = new Vector3(center).add(diameter);
		this.bounds = new BoundingBox(min, max);

		Color octreeColor = OctreeColors.getColor((int) diameter);
		Model octreeModel = ModelBuilderUtil.getInstance().getBox(
		//
			diameter * 2, diameter * 2, diameter * 2, //
			GL20.GL_LINES, octreeColor);
		internalGridInstance = new CollidableModelInstance(octreeModel, center, false);
	}

	@SuppressWarnings("unchecked")
	private void createChildren()
	{
		final Vector3 newCenter = new Vector3();
		final float newDiameter = diameter / 2.0f;

		children = new DynamicOctree[NUM_CHILDREN];
		for (int childIndex = 0; childIndex < NUM_CHILDREN; childIndex++)
		{
			float xAdjust = newDiameter * ((childIndex & 0x01) * 2 - 1);
			float yAdjust = newDiameter * (((childIndex & 0x02) >> 1) * 2 - 1);
			float zAdjust = newDiameter * (((childIndex & 0x04) >> 2) * 2 - 1);
			newCenter.set(center.x - xAdjust, center.y - yAdjust, center.z - zAdjust);
			children[childIndex] = new DynamicOctree<T>(newCenter, newDiameter,
				maxInstances);
		}
		if (talk)
			Gdx.app.log(ClassName, "octree " + center + "/" + diameter
				+ " exceeds maximum of " + maxInstances + " data. needed to create "
				+ NUM_CHILDREN + " new children with size " + newDiameter);
	}

	private DynamicOctree<T> getChildren(final Vector3 position)
	{
		if (children != null)
		{
			int childrenIndex = 0;
			if (position.x <= center.x)
				childrenIndex += 1;
			if (position.y <= center.y)
				childrenIndex += 2;
			if (position.z <= center.z)
				childrenIndex += 4;
			return children[childrenIndex];
		}
		else
			return this;
	}

	@Override
	public boolean insert(final T newInstance)
	{
		boolean res = true;

		if (talk)
		{
			Gdx.app.log(ClassName, "trying to insert " + newInstance.getBounds()
				+ " into " + center + "/" + diameter);
		}

		// try to insert the instance, when tree gets to big,
		// just create children and move data
		if (children == null)
			instances.add(newInstance);
		else
			res = getChildren(newInstance.getBounds().getCenter()).insert(newInstance);

		// if instance was added to self, check if partitioning is needed
		if (instances.size > maxInstances)
		{
			if (diameter >= Settings.getOctreeMinSize())
			{
				/* no children present -> create new */
				if (children == null)
				{
					createChildren();
					/* move all instances to children, including newly inserted instance */
					for (T instance : instances)
						res = getChildren(instance.getBounds().getCenter()).insert(
							instance);
					instances.clear();
				}
				/* just add to child */
				else
				{
					res = getChildren(newInstance.getBounds().getCenter()).insert(
						newInstance);
					if (res)
						instances.removeValue(newInstance, true);
				}
			}
			else
			{
				Gdx.app.log(this.getClass().getName(),
					"too many instances but reached depth maximum -> dropping");
				instances.removeValue(newInstance, true);
				res = false;
			}
		}
		return res;
	}

	@Override
	public Array<T> getInstances(final Frustum frustum)
	{
		final Array<T> res = new Array<T>();

		// if (frustum.pointInFrustum(center))
		if (frustum.boundsInFrustum(bounds))
		{
			if (children != null)
			{
				for (DynamicOctree<T> child : children)
					res.addAll(child.getInstances(frustum));
			}
			if (instances.size != 0)
				res.addAll(instances);
		}
		return res;
	}

	@Override
	public Array<T> getInstances(final Vector3 point)
	{
		Array<T> res = new Array<T>();

		if (bounds.contains(point))
		{
			if (children != null)
			{
				for (DynamicOctree<T> child : children)
					res.addAll(child.getInstances(point));
			}
			else
			{
				if (Settings.isLightingActive())
				{
					Gdx.app.log(this.getClass().getSimpleName(), "searching for " + point + " within " + bounds + " with " + instances.size + " nodes: found " + res.size + " elements");
				}

				for (T instance: instances)
				{
					if (instance.getBounds().contains(point))
						res.add(instance);
				}
			}
		}
		return res;
	}

	@Override
	public Array<CollidableModelInstance> getOctreeInstances()
	{
		final Array<CollidableModelInstance> res = new Array<CollidableModelInstance>();

		if (children != null)
		{
			for (DynamicOctree<T> child : children)
				res.addAll(child.getOctreeInstances());
		}
		res.add(internalGridInstance);
		return res;
	}

	@Override
	public Array<CollidableModelInstance> getOctreeInstances(final Frustum frustum)
	{
		final Array<CollidableModelInstance> res = new Array<CollidableModelInstance>();

		// if (frustum.pointInFrustum(center))
		if (frustum.boundsInFrustum(bounds))
		{
			if (children != null)
			{
				for (DynamicOctree<T> child : children)
					res.addAll(child.getOctreeInstances(frustum));
			}
			res.add(internalGridInstance);
		}
		return res;
	}

	@Override
	public int getLoad(final boolean getInstances)
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
