/**
 * 
 */
package com.me.mygdxgame.scene.octree;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * @author deDokter
 * 
 */
public class NewOctreeTest
{
	private Array<Vector3> points;
	private NewOctree octree;
	private Vector3 octreePoints[];
	private Vector3 qmin, qmax;
	private Random rand = new Random();

	private float rand11() // Random number between [-1,1]
	{
		return rand.nextFloat() * 2.0f - 1.0f;
	}

	private Vector3 randVec3() // Random vector with components in the range [-1,1]
	{
		return new Vector3(rand11(), rand11(), rand11());
	}

	public void init()
	{
		// Create a new Octree centered at the origin
		// with physical dimension 2x2x2
		octree = new NewOctree(new Vector3(0, 0, 0), new Vector3(1, 1, 1));

		// Create a bunch of random points
		final int nPoints = 10000;
		points = new Array<Vector3>();
		for (int i = 0; i < nPoints; ++i)
		{
			points.add(randVec3());
		}
		Gdx.app.log(this.getClass().getName(), "Created " + points.size + " points");

		// Insert the points into the octree
		octreePoints = new Vector3[nPoints];
		for (int i = 0; i < nPoints; ++i)
		{
			octreePoints[i] = points.get(i);
			octree.insert(octreePoints[i]);
		}
		Gdx.app.log(this.getClass().getName(), "Inserted points to octree");

		// Create a very small query box. The smaller this box is
		// the less work the octree will need to do. This may seem
		// like it is exagerating the benefits, but often, we only
		// need to know very nearby objects.
		qmin = new Vector3(-.15f, -.15f, -.15f);
		qmax = new Vector3(.15f, .15f, .15f);

		// Remember: In the case where the query is relatively close
		// to the size of the whole octree space, the octree will
		// actually be a good bit slower than brute forcing every point!
		
		Gdx.app.log(this.getClass().getName(), "children: " + octree.getNumChildren());
	}

	// Query using Octree
	public void testOctree()
	{
		double start = System.nanoTime();

		Array<Vector3> results = new Array<Vector3>();
		octree.getPointsInsideBox(qmin, qmax, results);

		double T = System.nanoTime() - start;
		Gdx.app.log(this.getClass().getName(), "testOctree found " + results.size + " points in " + T / 1000 + " msec.");
	}

}
