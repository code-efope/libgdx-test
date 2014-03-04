package com.me.mygdxgame.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class MovementListener extends InputAdapter
{
	@Override
	public boolean keyDown(int keycode)
	{
		if (keycode == InputMapper.KEYS.MOVEMENT.FORWARD)
			Gdx.app.log(this.toString(), "forward key");
		else if (keycode == InputMapper.KEYS.MOVEMENT.BACKWARD)
			Gdx.app.log(this.toString(), "backward key");
		else if (keycode == InputMapper.KEYS.MOVEMENT.LEFT)
			Gdx.app.log(this.toString(), "left key");
		else if (keycode == InputMapper.KEYS.MOVEMENT.RIGHT)
			Gdx.app.log(this.toString(), "right key");
		else if (keycode == InputMapper.KEYS.MOVEMENT.JUMP)
			Gdx.app.log(this.toString(), "jump key");
		else
			return false;
		return false;
	}
}
