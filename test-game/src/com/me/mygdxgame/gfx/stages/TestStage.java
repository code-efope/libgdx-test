package com.me.mygdxgame.gfx.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class TestStage extends Stage
{
	private final Skin skin;
	private final Label fpsLabel, timeLabel, posLabel, infoLabel, settingsLabel;
	private final TextField textField, resultField;
	private final TextButton button;
	private final Slider slider;
	private final Vector3 position = new Vector3();
	private final Vector3 lookat = new Vector3();
	private String text;

	public TestStage()
	{
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		skin = SkinProvider.getSkin();
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
		slider = new Slider(0.0f, 10.0f, 1.0f, false, skin);
		slider.setY(height - 140);
		button = new TextButton("blabla", skin);
		button.setSize(60, 40);
		button.setPosition(200, 200);
		this.addActor(fpsLabel);
		this.addActor(timeLabel);
		this.addActor(posLabel);
		this.addActor(infoLabel);
		this.addActor(settingsLabel);
		this.addActor(slider);
		this.addActor(textField);
		this.addActor(resultField);
		this.addActor(button);
	}

	@Override
	public void act(float deltaTime)
	{
		fpsLabel.setText(text);
		posLabel.setText("position: " + position + " " + lookat);
		timeLabel.setText("time: ");

		this.act(deltaTime);
	}

	public void draw()
	{
		this.draw();
	}
	
	public void update(Camera cam, int fps)
	{
		position.set(cam.position);
		lookat.set(cam.direction);
		text = "" + fps;		
	}
}
