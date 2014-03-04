package com.me.mygdxgame.gfx.octree;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class NewOctree
{
	private static final int NUM_OCTANTS = 8;

	// Physical position/size. This implicitly defines the bounding
	// box of this node
	private Vector3 origin; // ! The physical center of this node
	private Vector3 halfDimension; // ! Half the width/height/depth of this node

	// The tree has up to eight children and can additionally store
	// a point, though in many applications only, the leaves will store data.
	private NewOctree children[] = new NewOctree[NUM_OCTANTS]; // ! Pointers to child octants
	private Vector3 data; // ! Data point to be stored at a node

	// Children follow a predictable pattern to make accesses simple.
	// Here, - means less than 'origin' in that dimension, + means greater than.
	// child: 0 1 2 3 4 5 6 7
	// x: - - - - + + + +
	// y: - - + + - - + +
	// z: - + - + - + - +

	public NewOctree(final Vector3 origin, final Vector3 halfDimension)
	{
		this.origin = origin;
		this.halfDimension = halfDimension;

		// Initially, there are no children
		for (int i = 0; i < 8; ++i)
			children[i] = null;
	}

	public NewOctree(NewOctree copy)
	{
		this.origin = copy.origin;
		this.halfDimension = copy.halfDimension;
		this.data = copy.data;
	}

	public int getNumChildren()
	{
		int res = 0;
		if (!isLeafNode())
		{
			for (int i = 0; i < NUM_OCTANTS; i++)
			{
				res += children[i].getNumChildren();
			}
		}
		else
			res = 1;
		return res;
	}

	// Determine which octant of the tree would contain 'point'
	public final int getOctantContainingPoint(final Vector3 point)
	{
		int oct = 0;
		if (point.x >= origin.x)
			oct |= 4;
		if (point.y >= origin.y)
			oct |= 2;
		if (point.z >= origin.z)
			oct |= 1;
		return oct;
	}

	public final boolean isLeafNode()
	{
		// We are a leaf iff we have no children. Since we either have none, or
		// all eight, it is sufficient to just check the first.
		return children[0] == null;
	}

	public void insert(Vector3 instance)
	{
		// If this node doesn't have a data point yet assigned
		// and it is a leaf, then we're done!
		if (isLeafNode())
		{
			if (data == null)
			{
				data = instance;
				return;
			}
			else
			{
				// We're at a leaf, but there's already something here
				// We will split this node so that it has 8 child octants
				// and then insert the old data that was here, along with
				// this new data point

				// Save this data point that was here for a later re-insert
				Vector3 oldInstance = data;
				data = null;

				// Split the current node and create new empty trees for each
				// child octant.
				for (int i = 0; i < NUM_OCTANTS; ++i)
				{
					// Compute new bounding box for this child
					Vector3 newOrigin = new Vector3(origin);
					newOrigin.x += halfDimension.x * (((i & 4) == 1) ? .5f : -.5f);
					newOrigin.y += halfDimension.y * (((i & 2) == 1) ? .5f : -.5f);
					newOrigin.z += halfDimension.z * (((i & 1) == 1) ? .5f : -.5f);
					children[i] = new NewOctree(newOrigin, new Vector3(halfDimension.x * .5f, halfDimension.y * .5f,
							halfDimension.z * .5f));
				}

				// Re-insert the old point, and insert this new point
				// (We wouldn't need to insert from the root, because we already
				// know it's guaranteed to be in this section of the tree)
				children[getOctantContainingPoint(oldInstance)].insert(oldInstance);
				children[getOctantContainingPoint(instance)].insert(instance);
			}
		}
		else
		{
			// We are at an interior node. Insert recursively into the
			// appropriate child octant
			int octant = getOctantContainingPoint(instance);
			children[octant].insert(instance);
		}
	}

	// This is a really simple routine for querying the tree for points
	// within a bounding box defined by min/max points (bmin, bmax)
	// All results are pushed into 'results'
	public void getPointsInsideBox(final Vector3 bmin, final Vector3 bmax, Array<Vector3> results)
	{
		// If we're at a leaf node, just see if the current data point is inside
		// the query bounding box
		if (isLeafNode())
		{
			if (data != null)
			{
				final Vector3 p = data;
				if (p.x > bmax.x || p.y > bmax.y || p.z > bmax.z)
					return;
				if (p.x < bmin.x || p.y < bmin.y || p.z < bmin.z)
					return;
				results.add(data);
			}
		}
		else
		{
			// We're at an interior node of the tree. We will check to see if
			// the query bounding box lies outside the octants of this node.
			for (int i = 0; i < NUM_OCTANTS; ++i)
			{
				// Compute the min/max corners of this child octant
				Vector3 cmax = new Vector3(children[i].origin.add(children[i].halfDimension));
				Vector3 cmin = new Vector3(children[i].origin.sub(children[i].halfDimension));

				// If the query rectangle is outside the child's bounding box,
				// then continue
				if (cmax.x < bmin.x || cmax.y < bmin.y || cmax.z < bmin.z)
					continue;
				if (cmin.x > bmax.x || cmin.y > bmax.y || cmin.z > bmax.z)
					continue;

				// At this point, we've determined that this child is intersecting
				// the query bounding box
				children[i].getPointsInsideBox(bmin, bmax, results);
			}
		}
	}
}