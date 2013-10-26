package com.me.mygdxgame.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class Fog
{
	public void drawFog()
	{
		float fogDensity = 0.7f;
		float fogColour[] =
		{ 0.2f, 0.2f, 0.2f, 1f };
		Gdx.gl.glEnable(GL10.GL_FOG);
		Gdx.gl10.glFogf(GL10.GL_FOG_START, 1.0f);
		Gdx.gl10.glFogf(GL10.GL_FOG_END, 5.0f);
		Gdx.gl10.glFogf(GL10.GL_FOG_MODE, GL10.GL_EXP2);
		Gdx.gl10.glFogfv(GL10.GL_FOG_COLOR, fogColour, 0);
		Gdx.gl10.glFogf(GL10.GL_FOG_DENSITY, fogDensity);
		// Gdx.gl.glHint(GL10.GL_FOG_HINT, GL10.GL_NICEST);
	}
}
