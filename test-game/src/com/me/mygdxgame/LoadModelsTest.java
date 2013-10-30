package com.me.mygdxgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.me.mygdxgame.input.FPSCameraController;
import com.me.mygdxgame.input.SettingsListener;
import com.me.mygdxgame.scene.WorldRenderer;
import com.me.mygdxgame.util.FPSCounter;
import com.me.mygdxgame.util.Settings;

public class LoadModelsTest implements ApplicationListener
{
	private AssetManager assets;
	private FPSCounter fpsCounter;
	private WorldRenderer world;
	private PerspectiveCamera cam;
	private FPSCameraController camController;
	private SettingsListener sl = new SettingsListener();
	private Stage hud;
	private Skin skin;
	private Label fpsLabel;
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
		camController.registerListener(sl);
		Gdx.input.setInputProcessor(camController);

		world = new WorldRenderer(camController);
		fpsCounter = new FPSCounter();

		hud = new Stage();
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		fpsLabel = new Label("FPS: 999", skin);
		hud.addActor(fpsLabel);
	}

	@Override
	public void render()
	{
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearColor(0.5f, 0.2f, 0.5f, 0.0f);

		text = fpsCounter.logFrame();
		world.render(Gdx.graphics.getDeltaTime());

		fpsLabel.setText(text);
		hud.act(Gdx.graphics.getDeltaTime());
		
		if (Settings.isHudActive())
			hud.draw();
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