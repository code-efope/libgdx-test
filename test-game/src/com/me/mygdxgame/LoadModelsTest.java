package com.me.mygdxgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.me.mygdxgame.input.FPSCameraController;
import com.me.mygdxgame.scene.GUIRenderer;
import com.me.mygdxgame.scene.WorldRenderer;
import com.me.mygdxgame.util.FPSCounter;

public class LoadModelsTest implements ApplicationListener
{
	private AssetManager assets;
	private FPSCounter fpsCounter;
	private ModelBatch modelBatch;
	private WorldRenderer world;
	private GUIRenderer gui;
	private PerspectiveCamera cam;
	private FPSCameraController camController;
	private String text;

	@Override
	public void create()
	{
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 0f, -10f);
		cam.lookAt(0f, 0f, 0f);
		cam.near = 0.01f;
		cam.far = 300f;
		cam.update();

		camController = new FPSCameraController(cam);
		Gdx.input.setInputProcessor(camController);

		modelBatch = new ModelBatch();
		world = new WorldRenderer(camController);
		gui = new GUIRenderer();
		fpsCounter = new FPSCounter();
	}

	@Override
	public void render()
	{
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearColor(0.5f, 0.2f, 0.5f, 0.0f);

		modelBatch.begin(camController.camera);
		world.render(modelBatch);
		
		text = fpsCounter.logFrame();
		gui.render(text);

		modelBatch.end();
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