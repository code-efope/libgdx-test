package com.me.mygdxgame.gfx.renderer;

import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.me.mygdxgame.gfx.sprites.DecalSprite;
import com.me.mygdxgame.input.FPSCameraController;
import com.me.mygdxgame.interfaces.RendererIf;
import com.me.mygdxgame.util.TextureProvider;

public class DecalRenderer implements RendererIf
{
	private final DecalBatch db = new DecalBatch();
	private final DecalSprite testDecal;
	private final FPSCameraController camController;

	public DecalRenderer(FPSCameraController camController)
	{
		this.camController = camController;
		testDecal = new DecalSprite().build(TextureProvider.getTexture(12));
		testDecal.setSize(1, 1);
		testDecal.setPosition(0.0f, 1.0f, 4.0f);
	}

	@Override
	public void render()
	{
		testDecal.faceCamera(camController.camera);
		db.add(testDecal.getSprite());
		db.flush();
	}

	@Override
	public void render(float deltaTime)
	{
		render();
	}
}
