package com.me.mygdxgame.scene.models;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.me.mygdxgame.interfaces.Collidable;

public class CollidableModelInstance extends ModelInstance implements Collidable
{
	private final boolean collision;
	public CollidableModelInstance(Model model, boolean collision)
	{
		super(model);
		this.collision = collision;
	}

	public CollidableModelInstance(Model model, Vector3 position, boolean collision)
	{
		super(model, position);
		this.collision = collision;
	}

	@Override
	public boolean canCollide()
	{
		return collision;
	}
}
