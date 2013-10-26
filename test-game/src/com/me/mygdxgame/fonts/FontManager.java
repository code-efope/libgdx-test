package com.me.mygdxgame.fonts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FontManager
{
	private SpriteBatch spriteBatch;
	private BitmapFont font;

	public FontManager()
	{
		spriteBatch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("fonts/verdana39.fnt"), false);
	}

	public void render(String text)
	{
		int viewHeight = Gdx.graphics.getHeight();

		spriteBatch.begin();

		font.setColor(Color.RED);
		font.setScale(0.3f);

		font.draw(spriteBatch, text, 0, viewHeight - 10);
		spriteBatch.end();
	}
}