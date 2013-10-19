/**
 * 
 */
package com.me.mygdxgame.texture;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author deDokter
 * 
 */
public class TextureContainer
{
	private final static String folderName = "minecraft";
	private final static Random rand = new Random();

	private final static String[] nameTexture =
	{ "blockDiamond.png", "blockEmerald.png", "blockGold.png", "blockIron.png", "blockLapis.png", "blockRedstone.png",
			"sand.png", "stonebrick.png", "tree_birch.png", "vine.png", "wood_jungle.png", "tnt_side.png" };

	public static Texture getTexture()
	{
		return new Texture(Gdx.files.internal(folderName + "/" + nameTexture[rand.nextInt(nameTexture.length)]));
	}

	public static Texture getTexture(int index)
	{
		if (index < 0 || index > nameTexture.length)
			return getTexture();
		else
			return new Texture(Gdx.files.internal(folderName + "/" + nameTexture[index]));
	}

	public static String getTextureName()
	{
		return folderName + "/" + nameTexture[rand.nextInt(nameTexture.length)];
	}
}
