package com.me.mygdxgame.scene;

import com.me.mygdxgame.fonts.FontManager;

public class GUIRenderer
{
	private final FontManager fm = new FontManager();

	public void render(String text)
	{
		if (text != null)
		{
			fm.render(text);
		}
	}
}
