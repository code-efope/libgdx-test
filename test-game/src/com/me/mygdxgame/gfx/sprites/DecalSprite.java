package com.me.mygdxgame.gfx.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class DecalSprite
{
	private Decal sprite;

	public DecalSprite()
	{
	}

	public DecalSprite build(String imgPath)
	{
		TextureWrap texWrap = Texture.TextureWrap.ClampToEdge; // default
		return build(imgPath, texWrap);
	}

	public DecalSprite build(String imgPath, TextureWrap texWrap)
	{
		Texture texture = new Texture(Gdx.files.internal(imgPath));
		texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		
		return build(texture, texWrap);
	}
	
	public DecalSprite build(Texture texture)
	{
		TextureWrap texWrap = Texture.TextureWrap.ClampToEdge; // default
		return build(texture, texWrap);
	}

	public DecalSprite build(Texture texture, TextureWrap texWrap)
	{
		float w = texture.getWidth();
		float h = texture.getHeight();
		sprite = Decal.newDecal(w, h, new TextureRegion(texture), true);
		return this;
	}

	public void setSize(Vector2 size)
	{
		sprite.setDimensions(size.x, size.y);
	}

	public void setSize(float x, float y)
	{
		sprite.setDimensions(x, y);		
	}

	public void setPosition(Vector3 pos)
	{
		sprite.setPosition(pos.x, pos.y, pos.z);
	}

	public void setPosition(float x, float y, float z)
	{
		sprite.setPosition(x, y, z);
	}

	public void faceCamera(Camera cam)
	{
		sprite.lookAt(cam.position.cpy(), cam.up.cpy().nor());
	}

	public void update(float delta)
	{
		// sprite.setRotation(dir, up)
	}
	
	public Decal getSprite()
	{
		return sprite;
	}
}