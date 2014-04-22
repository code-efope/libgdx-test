package com.me.mygdxgame.gfx.sector;

import com.badlogic.gdx.math.Vector3;

public class RectangleSector extends BaseSector
{
	public RectangleSector()
	{
		super();
		bounds.set(new Vector3(0.0f, 0.0f, 0.0f), new Vector3(2.0f, 2.0f, 2.0f));
	}

	public RectangleSector(final Vector3 v1, final Vector3 v2)
	{
		super();
		bounds.set(v1, v2);
	}

	@Override
	public boolean isWithinSector(final Vector3 position)
	{
		return bounds.contains(position);
	}
}
