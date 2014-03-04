package com.me.mygdxgame.gfx.sector;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class Portal extends RectangleWall
{
	public BaseSector destination;

	public Portal(final Vector3 v1, final Vector3 v2)
	{
		super(v1, v2, new Color(0.0f, 0.0f, 0.0f, 1.0f), "web", true);
		instance.setCollide(false);
	}

	public void setDestination(final BaseSector destination)
	{
		this.destination = destination;
	}
}
