package com.me.mygdxgame.gfx.renderer;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import com.me.mygdxgame.input.FPSCameraController;
import com.me.mygdxgame.interfaces.RendererIf;
import com.me.mygdxgame.util.BinaryRandomizer;
import com.me.mygdxgame.util.TextureProvider;

public class DecalRenderer implements RendererIf
{
	private DecalBatch db;
	private final Map<String, Decal> internalDecals = new HashMap<String, Decal>();
	private final FPSCameraController camController;

	public DecalRenderer(FPSCameraController camController)
	{
		this.camController = camController;
		Decal testDecal;
		String decalName;

		db = new DecalBatch(new CameraGroupStrategy(this.camController.camera));
		for (int i = 0; i < 10; i++)
		{
			decalName = "decal" + i;
			testDecal = Decal.newDecal(new TextureRegion(TextureProvider.getTexture(12)), true);
			testDecal.setScale(1, 1);
			testDecal.setPosition(BinaryRandomizer.get(4), 1.0f, BinaryRandomizer.get(4));
			internalDecals.put(decalName, testDecal);
		}
		Texture sun = new Texture(Gdx.files.internal("data/cylinder2_auv.png"));
		//testDecal = Decal.newDecal(new TextureRegion(TextureProvider.getTexture(1)));
		testDecal = Decal.newDecal(new TextureRegion(sun), true);
		testDecal.setScale(3, 3);
		testDecal.setPosition(0.0f, 0.0f, 0.0f);
		internalDecals.put("sunDecal", testDecal);
	}

	@Override
	public void render()
	{
		for (Decal testDecal: internalDecals.values())
		{
			testDecal.lookAt(camController.camera.position, camController.camera.up);
			db.add(testDecal);
		}
		db.flush();
	}

	@Override
	public void render(final float deltaTime)
	{
		render();
	}
	
	public boolean moveDecal(final String name, final Vector3 position)
	{
		if (internalDecals.containsKey(name))
		{
			internalDecals.get(name).setPosition(position.x, position.y, position.z);
			return true;
		}
		return false;
	}
}
