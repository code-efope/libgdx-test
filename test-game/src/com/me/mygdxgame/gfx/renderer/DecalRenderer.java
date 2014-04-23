package com.me.mygdxgame.gfx.renderer;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector3;
import com.me.mygdxgame.gfx.sprites.DecalSprite;
import com.me.mygdxgame.input.FPSCameraController;
import com.me.mygdxgame.interfaces.RendererIf;
import com.me.mygdxgame.util.BinaryRandomizer;
import com.me.mygdxgame.util.TextureProvider;

public class DecalRenderer implements RendererIf
{
	private final DecalBatch db = new DecalBatch();
	private final Map<String, DecalSprite> internalDecals = new HashMap<String, DecalSprite>();
	private final FPSCameraController camController;

	public DecalRenderer(FPSCameraController camController)
	{
		this.camController = camController;
		DecalSprite testDecal;
		String decalName;

		for (int i = 0; i < 10; i++)
		{
			decalName = "decal" + i;
			testDecal = new DecalSprite().build(TextureProvider.getTexture(12));
			testDecal.setSize(1, 1);
			testDecal.setPosition(BinaryRandomizer.get(4), 1.0f, BinaryRandomizer.get(4));
			internalDecals.put(decalName, testDecal);
		}
		testDecal = new DecalSprite().build(TextureProvider.getTexture(2));
		testDecal.setSize(3, 3);
		testDecal.setPosition(0.0f, 0.0f, 0.0f);
		internalDecals.put("sunDecal", testDecal);		
	}

	@Override
	public void render()
	{
		for (DecalSprite testDecal: internalDecals.values())
		{
			testDecal.faceCamera(camController.camera);
			db.add(testDecal.getSprite());
		}
		db.flush();
	}

	@Override
	public void render(final float deltaTime)
	{
		render();
	}
	
	public void moveDecal(final String name, final Vector3 position)
	{
		if (internalDecals.containsKey(name))
			internalDecals.get(name).setPosition(position);
	}
}
