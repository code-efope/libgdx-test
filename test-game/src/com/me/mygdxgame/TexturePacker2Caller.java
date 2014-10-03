package com.me.mygdxgame;

import java.io.IOException;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TexturePacker2Caller
{
	public static void main(String[] args) throws IOException
	{
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.maxWidth = 512;
		settings.maxHeight = 512;
		TexturePacker.process(settings, "../test-game-android/assets/minecraft", "../test-game-android/assets", "game");
	}
}
