package com.me.mygdxgame.gfx.model;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.me.mygdxgame.interfaces.Collidable;
import com.me.mygdxgame.interfaces.Treeable;

public class CollidableModelInstance extends ModelInstance implements Collidable, Treeable
{
	private boolean collision = true;
	private final BoundingBox bounds = new BoundingBox();;

	public CollidableModelInstance(Model model)
	{
		this(model, true);
	}

	public CollidableModelInstance(Model model, boolean collision)
	{
		this(model, new Vector3(0.0f, 0.0f, 0.0f), collision);
	}

	public CollidableModelInstance(Model model, Vector3 position, boolean collision)
	{
		super(model, position);
		BoundingBox tmpBounds = new BoundingBox();
		model.calculateBoundingBox(tmpBounds);
		bounds.set(new Vector3(tmpBounds.min).add(position), new Vector3(tmpBounds.max).add(position));
		this.collision = collision;
	}

	public void setCollide(final boolean collision)
	{
		this.collision = collision;
	}

	@Override
	public boolean canCollide()
	{
		return collision;
	}

	@Override
	public BoundingBox getBounds()
	{
		return bounds;
	}
}
