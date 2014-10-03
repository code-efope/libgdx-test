package com.me.mygdxgame.gfx.stages;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/*
 * a transition is used to traverse from one stage to another
 * 
 */
public class StageTransition
{
	private final Stage target;

	public StageTransition(Actor actor, Stage target)
	{
		this.target = target;
	}
	
	public Stage getTarget()
	{
		return target;
	}
}
