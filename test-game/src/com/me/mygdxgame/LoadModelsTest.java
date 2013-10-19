package com.me.mygdxgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.lights.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.lights.Lights;
import com.me.mygdxgame.input.FPSCameraController;
import com.me.mygdxgame.util.FPSCounter;
import com.me.mygdxgame.world.WorldRenderer;

public class LoadModelsTest implements ApplicationListener
{
	private AssetManager assets;
	private Lights lights;
	private FPSCounter fpsCounter;
	private WorldRenderer world;
	private PerspectiveCamera cam;
	private FPSCameraController camController;

	@Override
	public void create()
	{
		lights = new Lights();
		lights.ambientLight.set(0.4f, 0.9f, 0.0f, 1f);
		lights.fog = new Color(0.9f, 0.9f, 0.9f, 1);
		lights.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0, 0.5f, -2);
		cam.lookAt(0, 0.5f, -3);
		cam.near = 0.01f;
		cam.far = 300f;
		cam.update();

		camController = new FPSCameraController(cam);
		Gdx.input.setInputProcessor(camController);

		world = new WorldRenderer();

		fpsCounter = new FPSCounter();
	}

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

	@Override
	public void render()
	{
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearColor(0.1f, 0.2f, 0.2f, 0.0f);

		// drawFog();
		world.render(lights, camController);
		fpsCounter.logFrame();
	}

	@Override
	public void dispose()
	{
		world.dispose();
		if (assets != null)
			assets.dispose();
	}

	@Override
	public void resize(int width, int height)
	{
		Gdx.app.log("main", "resized");
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void resume()
	{
	}
}