package com.me.mygdxgame.gfx.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.me.mygdxgame.gfx.DaylightSimulation;
import com.me.mygdxgame.input.InputMapper;
import com.me.mygdxgame.interfaces.RendererIf;
import com.me.mygdxgame.scripting.ScriptManager;
import com.me.mygdxgame.scripting.ScriptResult;
import com.me.mygdxgame.util.DiagnosisDataProvider;
import com.me.mygdxgame.util.Settings;

public class HUDManager extends InputListener implements RendererIf
{
	private final ScriptManager scripter = ScriptManager.getInstance();
	private final Stage hudStage;
	private final Skin skin;
	private final Label fpsLabel, timeLabel, posLabel, infoLabel, settingsLabel;
	private final TextField textField, resultField;
	private final Slider slider;
	private final Vector3 position = new Vector3();
	private final Vector3 lookat = new Vector3();
	private String text;
	private InputListener listener = new InputListener()
	{
		@Override
		public boolean keyDown(InputEvent event, int keycode)
		{
			if (keycode == InputMapper.KEYS.SETTINGS.TOGGLE_CONSOLE)
				Settings.toggleConsole();

			/* when console is not active, do not handle input events */
			if (!Settings.isConsoleActive())
				return false;

			/* enter executes the current entered command */
			if (keycode == Keys.ENTER)
			{
				ScriptResult res = scripter.execute(textField.getText());
				if (res.success)
					resultField.setText(res.message);
				else
					resultField.setText("error: " + res.message);
			}
			return true;
		}
	};

	public HUDManager()
	{
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		hudStage = new Stage();
		hudStage.addListener(listener);
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		fpsLabel = new Label("FPS: 99", skin);
		fpsLabel.setY(height - 20);
		timeLabel = new Label("FPS: 99", skin);
		timeLabel.setY(height - 40);
		posLabel = new Label("FPS: 99", skin);
		posLabel.setY(height - 60);
		infoLabel = new Label("FPS: 99", skin);
		infoLabel.setY(height - 80);
		settingsLabel = new Label("FPS: 99", skin);
		settingsLabel.setY(height - 100);
		resultField = new TextField("", skin);
		resultField.setY(0);
		resultField.setHeight(20);
		resultField.setWidth(width);
		resultField.setDisabled(true);
		textField = new TextField("", skin);
		textField.setY(20);
		textField.setHeight(20);
		textField.setWidth(width);
		textField.addListener(this);
		slider = new Slider(0.0f, 10.0f, 1.0f, false, skin);
		slider.setY(height - 140);
		hudStage.addActor(fpsLabel);
		hudStage.addActor(timeLabel);
		hudStage.addActor(posLabel);
		hudStage.addActor(infoLabel);
		hudStage.addActor(settingsLabel);
		hudStage.addActor(slider);
		hudStage.addActor(textField);
		hudStage.addActor(resultField);
	}

	@Override
	public void render()
	{
		render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void render(float deltaTime)
	{
		fpsLabel.setText(text);
		posLabel.setText("position: " + position + " " + lookat);
		timeLabel.setText("time: " + (int) DaylightSimulation.getTime() + ":"
			+ (int) (DaylightSimulation.getTime() * 60.0f) % 60 + " " + DaylightSimulation.getSunPosition());
		infoLabel.setText("visible (I/M/V): " + DiagnosisDataProvider.numInstances + "/"
			+ DiagnosisDataProvider.numMeshes + "/" + DiagnosisDataProvider.numVertices
			+ " maxDist: " + Settings.getViewDistance2());
		settingsLabel.setText("col: " + Settings.isCollisionActive() + " lights: "
			+ Settings.isLightingActive() + " tree: " + Settings.showOctrees()
			+ " portals: " + Settings.showPortals());
		textField.setVisible(Settings.isConsoleActive());
		textField.setDisabled(!Settings.isConsoleActive());
		resultField.setVisible(Settings.isConsoleActive());

		hudStage.act(deltaTime);
		hudStage.draw();
	}

	public void update(Camera cam, int fps)
	{
		position.set(cam.position);
		lookat.set(cam.direction);
		text = "" + fps;		
	}

	public InputAdapter getInputAdapter()
	{
		return hudStage;
	}
}
