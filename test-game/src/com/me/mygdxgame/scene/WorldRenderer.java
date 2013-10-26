package com.me.mygdxgame.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.lights.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.lights.Lights;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.me.mygdxgame.input.FPSCameraController;
import com.me.mygdxgame.util.Settings;

public class WorldRenderer implements Disposable
{
	private final SceneManager scene = new SceneManager();
	private final Renderable ren = new Renderable();
	private final Vector3 renPos = new Vector3();
	private final Vector3 lastPosition = new Vector3();
	private float dist = 0.0f, innerDist;
	private boolean checkCollision = false, positionAdjusted = false;
	private Array<ModelInstance> instances;
	private final Skybox skybox = new Skybox();
	private Lights lights;
	private final FPSCameraController camController;
//	private Fog fog = new Fog();

	public WorldRenderer(FPSCameraController camController)
	{
		this.camController = camController;
		lights = new Lights();
		lights.ambientLight.set(0.4f, 0.9f, 0.0f, 1f);
		lights.fog = new Color(0.1f, 0.1f, 0.1f, 1);
		lights.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	}

	public void render(ModelBatch modelBatch)
	{
		// calculate new position of camera after movement
		camController.update();

//		skybox.render();

	    if (camController.moved())
	    {
	    	dist = lastPosition.dst2(camController.camera.position);
	    	if (dist > Settings.getMovementDistance())
	    		checkCollision = true;
	    }
	    else
	    	checkCollision = false;

		instances = scene.getInstances(camController.camera.frustum);
		for (ModelInstance instance : instances)
		{
			instance.getRenderable(ren);
			ren.worldTransform.getTranslation(renPos);
			if (camController.camera.position.dst2(renPos) <= Settings.getViewDistance2())
			{
				modelBatch.render(instance);

				/* check collision after rendering */
				if (checkCollision)
				{
					/* when there's no collision yet */
					if (!positionAdjusted)
					{
						innerDist = camController.camera.position.dst2(renPos);
						if (innerDist <= Settings.getCollisionDistance2())
//						instance.calculateBoundingBox(bbox);
//						if (bbox.contains(camController.newPosition))
						{
							Gdx.app.log("main", "" + camController.camera.position + " collides with " + renPos + " dist: " + innerDist);
							positionAdjusted = true;
						}
					}
				}
			}
		}

		if (positionAdjusted)
		{
			// don't update camera
//			camController.reject();
			camController.camera.position.set(lastPosition);
			positionAdjusted = false;
		}
		else if (checkCollision)
		{
			// accept movement of camera
//			camController.accept();
			lastPosition.set(camController.camera.position);
			checkCollision = false;
		}

//		fog.drawFog();

		if (camController.moved())
			camController.accept();

	}

	public boolean positionAdjusted()
	{
		return positionAdjusted;
	}

	@Override
	public void dispose()
	{
		if (instances.size != 0)
			instances.clear();
		skybox.dispose();
	}
}
