package com.me.mygdxgame.input;

import com.badlogic.gdx.InputAdapter;
import com.me.mygdxgame.util.Settings;

public class SettingsListener extends InputAdapter
{
	@Override
	public boolean keyDown(int keyCode)
	{
		if (keyCode == InputMapper.KEYS.SETTINGS.TOGGLE_LIGHT)
			Settings.toggleLighting();
		if (keyCode == InputMapper.KEYS.SETTINGS.TOGGLE_COLLISION)
			Settings.toggleCollision();
		if (keyCode == InputMapper.KEYS.SETTINGS.TOGGLE_HUD)
			Settings.toggleHud();
		return true;
	}
}
