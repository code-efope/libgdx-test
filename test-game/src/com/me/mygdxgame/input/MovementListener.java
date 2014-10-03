package com.me.mygdxgame.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class MovementListener extends InputAdapter
{
	private String className = this.getClass().getSimpleName();

	@Override
	public boolean keyDown(int keycode)
	{
		if (keycode == InputMapper.KEYS.MOVEMENT.FORWARD)
			Gdx.app.log(className, "forward key");
		else if (keycode == InputMapper.KEYS.MOVEMENT.BACKWARD)
			Gdx.app.log(className, "backward key");
		else if (keycode == InputMapper.KEYS.MOVEMENT.LEFT)
			Gdx.app.log(className, "left key");
		else if (keycode == InputMapper.KEYS.MOVEMENT.RIGHT)
			Gdx.app.log(className, "right key");
		else if (keycode == InputMapper.KEYS.MOVEMENT.JUMP)
			Gdx.app.log(className, "jump key");
		else
			return false;

		Gdx.app.log(className, "key " + keycode + " handled.");
		return true;
	}
}
