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
 * instead of pre partition the complete space before inserting any data to the tree, this tree get partitioned while
 * data gets inserted. when creating the tree it gets passed the size and the maximum number of data points inside a
 * leaf. when the number of data points exceeds the given maximum, the current node gets partitioned
 **/
public class DynamicOctree implements OctreeIf
{
	private final Vector3 center;
	private final float diameter;
	private final int maxInstances;
	private final BoundingBox bounds;
	private DynamicOctree children[] = null;
	private Array<CollidableModelInstance> instances = new Array<CollidableModelInstance>();
	private static final boolean talk = false;
	private final CollidableModelInstance internalGridInstance;

	public DynamicOctree(Vector3 center, float diameter)
	{
		this(center, diameter, Settings.getOctreeMaxData());
	}

	public DynamicOctree(Vector3 center, float diameter, int maxInstances)
	{
		this.center = new Vector3(center);
		this.diameter = diameter;
		this.maxInstances = maxInstances;

		Vector3 min = new Vector3(center).sub(diameter);
		Vector3 max = new Vector3(center).add(diameter);
		this.bounds = new BoundingBox(min, max);

		ModelBuilder builder = new ModelBuilder();
		Model octreeModel = builder.createBox(diameter * 2, diameter * 2, diameter * 2,
			GL10.GL_LINES, new Material(), Usage.Position + Usage.Normal);
		internalGridInstance = new CollidableModelInstance(octreeModel, min, false);
	}

	private void createChildren()
	{
		Vector3 newCenter = new Vector3();
		float newDiameter = diameter / 2.0f;

		children = new DynamicOctree[8];
		for (int childIndex = 0; childIndex < 8; childIndex++)
		{
			float xAdjust = newDiameter * ((childIndex & 0x01) * 2 - 1);
			float yAdjust = newDiameter * (((childIndex & 0x02) >> 1) * 2 - 1);
			float zAdjust = newDiameter * (((childIndex & 0x04) >> 2) * 2 - 1);
			newCenter.set(center.x - xAdjust, center.y - yAdjust, center.z - zAdjust);
			children[childIndex] = new DynamicOctree(newCenter, newDiameter,
				maxInstances);
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
	public boolean insert(CollidableModelInstance newInstance)
	{
		boolean res = true;
		Renderable ren = new Renderable();
		Vector3 position = new Vector3();

		if (talk)
		{
			Renderable rentmp = new Renderable();
			Vector3 positiontmp = new Vector3();
			newInstance.getRenderable(rentmp);
			rentmp.worldTransform.getTranslation(positiontmp);
			Gdx.app.log(this.getClass().getName(), "insert " + positiontmp + " into "
				+ center + ":" + diameter);
		}
		/* first insert the instance, when tree gets to big, just create children and move data */
		instances.add(newInstance);
		if (instances.size > maxInstances)
		{
			if (diameter > Settings.getOctreeMinSize())
			{
				/* no children present -> create new */
				if (children == null)
				{
					createChildren();
					/* move all instances to children, including newly inserted instance */
					for (CollidableModelInstance instance : instances)
					{
						instance.getRenderable(ren);
						ren.worldTransform.getTranslation(position);

						res = children[getChildrenIndex(position)].insert(instance);
					}
					instances.clear();
				}
				/* just add to child */
				else
				{
					newInstance.getRenderable(ren);
					ren.worldTransform.getTranslation(position);

					res = children[getChildrenIndex(position)].insert(newInstance);
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
	public Array<CollidableModelInstance> getInstances(Frustum frustum)
	{
		Array<CollidableModelInstance> res = new Array<CollidableModelInstance>();

		if (frustum.boundsInFrustum(bounds))
		{
			if (children != null)
			{
				for (OctreeIf child : children)
					res.addAll(child.getInstances(frustum));
			}
			if (instances.size != 0)
				res.addAll(instances);
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
				for (OctreeIf child : children)
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
