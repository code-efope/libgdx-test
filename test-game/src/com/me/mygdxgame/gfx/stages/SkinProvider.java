package com.me.mygdxgame.gfx.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SkinProvider
{
	public final static Skin theSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

	public final static Skin getSkin()
	{
		return theSkin;
	}
}
