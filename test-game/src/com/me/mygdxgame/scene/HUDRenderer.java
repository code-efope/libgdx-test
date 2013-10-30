package com.me.mygdxgame.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class HUDRenderer
{
	private Stage hud;
	private Skin skin;
	private Label fpsLabel;

	public HUDRenderer()
	{
		hud = new Stage();
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		fpsLabel = new Label("FPS: 999", skin);
		hud.addActor(fpsLabel);
	}

	public void render(String text)
	{
		fpsLabel.setText(text);
		hud.act(Gdx.graphics.getDeltaTime());
		
		hud.draw();
	}
}
