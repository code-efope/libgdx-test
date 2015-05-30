package com.me.mygdxgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.me.mygdxgame.gfx.hud.HUDManager;
import com.me.mygdxgame.gfx.renderer.WorldRenderer;
import com.me.mygdxgame.input.FPSCameraController;
import com.me.mygdxgame.input.InputManager;
import com.me.mygdxgame.input.MovementListener;
import com.me.mygdxgame.input.SettingsInputListener;
import com.me.mygdxgame.util.DisposableManager;
import com.me.mygdxgame.util.Settings;

public class EngineTest implements ApplicationListener
{
	private final String className = this.getClass().getSimpleName();
	private HUDManager hud;
	private final InputManager inputMan = new InputManager();
	private final DisposableManager disManager = new DisposableManager();
	private WorldRenderer world;
	private PerspectiveCamera cam;
	private FPSCameraController camController;

	@Override
	public void create()
	{
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 1f, 0f);
		cam.lookAt(0f, 1f, 0.5f);
		cam.near = 0.5f;
		cam.far = 200f;
		cam.update();

		hud = new HUDManager();

		camController = new FPSCameraController(cam);
		inputMan.addInputListener(hud.getInputAdapter());
		inputMan.addInputListener(camController);
		inputMan.addInputListener(new SettingsInputListener());
		inputMan.addInputListener(new MovementListener());

		Gdx.input.setInputProcessor(inputMan);

		world = new WorldRenderer(camController);
		disManager.addDisposable(world);

		DisplayMode[] modes = Gdx.graphics.getDisplayModes();
		for (DisplayMode mode: modes)
			Gdx.app.log(className, mode.toString());
	}

	@Override
	public void render()
	{
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);

		world.render(Gdx.graphics.getDeltaTime());

		if (Settings.isHudActive())
		{
			hud.update(cam, Gdx.graphics.getFramesPerSecond());
			hud.render();
		}
	}

	@Override
	public void dispose()
	{
		disManager.dispose();
	}

	@Override
	public void resize(int width, int height)
	{
		Gdx.app.log(className, "resized");
	}

	@Override
	public void pause()
	{
		Gdx.app.log(className, "paused");
	}

	@Override
	public void resume()
	{
		Gdx.app.log(className, "resumed");
	}
}