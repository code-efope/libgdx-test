package com.me.mygdxgame.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class TextureProvider
{
	private final static String folderName = "minecraft";
	private final static Random rand = new Random();
	private final static TextureAtlas atlas = new TextureAtlas(
		Gdx.files.internal("game.atlas"));
	private final static Map<String, AtlasRegion> regions = new HashMap<String, AtlasRegion>();

	private final static String[] nameTexture =
	{
		//
		"blockDiamond", "blockEmerald", "blockGold", "blockIron", "blockLapis",
		"blockRedstone", "clay", "dirt", "sand", "stonebrick", "tree_birch", "vine",
		"web", "wood", "tnt_side" //
	};

	static
	{
		init();
	}

	private static void init()
	{
		for (String texName : nameTexture)
		{
			getTextureFromAtlas(texName);
		}
	}

	public static Texture getTexture(int index)
	{
		return new Texture(Gdx.files.internal(getFullTextureName(index)));
	}

	public static String getFullTextureName(int index)
	{
		return folderName + "/" + getTextureName(index) + ".png";
	}

	public static String getTextureName(int index)
	{
		if (index < 0 || index > nameTexture.length)
			index = rand.nextInt(nameTexture.length);
		return nameTexture[index];
	}

	public static Texture getAtlasTexture()
	{
		if (atlas.getRegions().size == 1)
			return atlas.getRegions().get(0).getTexture();
		else
			return atlas.getRegions().get(0).getTexture();
	}

	public static AtlasRegion getTextureFromAtlas(String name)
	{
		AtlasRegion region;
		if (regions.containsKey(name))
			region = regions.get(name);
		else
		{
			region = atlas.findRegion(name);
			regions.put(name, region);
		}
		return region;
	}
}
