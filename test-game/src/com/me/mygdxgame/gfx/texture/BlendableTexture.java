package com.me.mygdxgame.gfx.texture;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.me.mygdxgame.interfaces.Blendable;

public class BlendableTexture extends Texture implements Blendable
{
	public BlendableTexture(String internalPath)
	{
		super(internalPath);
		// TODO Auto-generated constructor stub
	}

	public BlendableTexture(FileHandle file)
	{
		super(file);
		// TODO Auto-generated constructor stub
	}

	public BlendableTexture(Pixmap pixmap)
	{
		super(pixmap);
		// TODO Auto-generated constructor stub
	}

	public BlendableTexture(TextureData data)
	{
		super(data);
		// TODO Auto-generated constructor stub
	}

	public BlendableTexture(FileHandle file, boolean useMipMaps)
	{
		super(file, useMipMaps);
		// TODO Auto-generated constructor stub
	}

	public BlendableTexture(Pixmap pixmap, boolean useMipMaps)
	{
		super(pixmap, useMipMaps);
		// TODO Auto-generated constructor stub
	}

	public BlendableTexture(FileHandle file, Format format, boolean useMipMaps)
	{
		super(file, format, useMipMaps);
		// TODO Auto-generated constructor stub
	}

	public BlendableTexture(Pixmap pixmap, Format format, boolean useMipMaps)
	{
		super(pixmap, format, useMipMaps);
		// TODO Auto-generated constructor stub
	}

	public BlendableTexture(int width, int height, Format format)
	{
		super(width, height, format);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canBlend()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
