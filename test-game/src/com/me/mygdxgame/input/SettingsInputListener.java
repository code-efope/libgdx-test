package com.me.mygdxgame.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.me.mygdxgame.util.Settings;

public class SettingsInputListener extends InputAdapter
{
	@Override
	public boolean keyDown(int keycode)
	{
		if (keycode == InputMapper.KEYS.SETTINGS.TOGGLE_LIGHT)
			Settings.toggleLighting();
		else if (keycode == InputMapper.KEYS.SETTINGS.TOGGLE_COLLISION)
			Settings.toggleCollision();
		else if (keycode == InputMapper.KEYS.SETTINGS.TOGGLE_HUD)
			Settings.toggleHud();
		else if (keycode == InputMapper.KEYS.SETTINGS.INCREASE_VISUAL_RANGE)
			Settings.increaseVisualRange();
		else if (keycode == InputMapper.KEYS.SETTINGS.DECREASE_VISUAL_RANGE)
			Settings.decreaseVisualRange();
		else if (keycode == InputMapper.KEYS.SETTINGS.TOGGLE_OCTREES)
			Settings.toggleShowOctrees();
		else if (keycode == InputMapper.KEYS.SETTINGS.TOGGLE_PORTALS)
			Settings.toggleShowPortals();
		else
			return false;
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		Gdx.app.log(this.toString(), "touchDown");
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		Gdx.app.log(this.toString(), "touchUp");
		return false;
	}
}
