package com.me.mygdxgame;

import java.io.IOException;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

public class TexturePacker2Caller
{
	public static void main(String[] args) throws IOException
	{
		TexturePacker2.Settings settings = new TexturePacker2.Settings();
		settings.maxWidth = 512;
		settings.maxHeight = 512;
		TexturePacker2.process(settings, "../test-game-android/assets/minecraft", "../test-game-android/assets", "game");
	}
}
