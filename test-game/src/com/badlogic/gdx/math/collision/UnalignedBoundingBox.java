package com.badlogic.gdx.math.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class UnalignedBoundingBox extends BoundingBox
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4549800700256214017L;

	public UnalignedBoundingBox()
	{
		// TODO Auto-generated constructor stub
	}

	public UnalignedBoundingBox(BoundingBox bounds)
	{
		super(bounds);
		// TODO Auto-generated constructor stub
	}

	public UnalignedBoundingBox(Vector3 minimum, Vector3 maximum)
	{
		super(minimum, maximum);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean intersects(BoundingBox b)
	{
		if (!isValid())
			return false;

		// test using SAT (separating axis theorem)

		float lx = Math.abs(this.cnt.x - b.cnt.x);
		float sumx = (this.dim.x / 2.0f) + (b.dim.x / 2.0f);

		float ly = Math.abs(this.cnt.y - b.cnt.y);
		float sumy = (this.dim.y / 2.0f) + (b.dim.y / 2.0f);

		float lz = Math.abs(this.cnt.z - b.cnt.z);
		float sumz = (this.dim.z / 2.0f) + (b.dim.z / 2.0f);

		return (lx < sumx && ly < sumy && lz < sumz);
	}
}
