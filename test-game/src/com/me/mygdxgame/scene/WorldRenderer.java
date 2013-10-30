package com.me.mygdxgame.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.lights.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.lights.Lights;
import com.badlogic.gdx.graphics.g3d.lights.PointLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.me.mygdxgame.input.FPSCameraController;
import com.me.mygdxgame.scene.models.CollidableModelInstance;
import com.me.mygdxgame.util.Settings;

public class WorldRenderer implements Disposable
{
	private final SceneManager scene = new SceneManager();
	private final Renderable ren = new Renderable();
	private final Vector3 renPos = new Vector3();
	private final Vector3 lastPosition = new Vector3();
	private float dist = 0.0f, innerDist;
	private boolean checkCollision = false, positionAdjusted = false;
	private Array<CollidableModelInstance> instances;
	private final Skybox skybox = new Skybox();
	private Lights lights;
	private final FPSCameraController camController;
	private final ModelBatch modelBatch = new ModelBatch();
	private DirectionalLight dirLight;
	private PointLight pointLight;

	public WorldRenderer(FPSCameraController camController)
	{
		this.camController = camController;
		lights = new Lights();
		lights.ambientLight.set(0.4f, 0.4f, 0.4f, 1f);
		lights.fog = new Color(0.1f, 0.1f, 0.1f, 1);
		dirLight = new DirectionalLight();
		pointLight = new PointLight();
		lights.add(dirLight);
		lights.add(pointLight);
	}

	public void render(float deltaTime)
	{
		// calculate new position of camera after movement
		camController.update();

		modelBatch.begin(camController.camera);
//		skybox.render();

		for (CollidableModelInstance instance: skybox.getInstaces())
			modelBatch.render(instance);

	    if (Settings.isCollisionActive() && camController.moved())
	    {
	    	dist = lastPosition.dst2(camController.camera.position);
	    	if (dist > Settings.getMovementDistance())
	    		checkCollision = true;
	    }
	    else
	    	checkCollision = false;

		instances = scene.getInstances(camController.camera.frustum);
		for (CollidableModelInstance instance : instances)
		{
			instance.getRenderable(ren);
			ren.worldTransform.getTranslation(renPos);
			if (camController.camera.position.dst2(renPos) <= Settings.getViewDistance2())
			{
				if (Settings.isLightingActive())
					modelBatch.render(instance, lights);
				else
					modelBatch.render(instance);

				/* check collision after rendering */
				if (instance.canCollide() && checkCollision)
				{
					/* when there's no collision yet */
					if (!positionAdjusted)
					{
						innerDist = camController.camera.position.dst2(renPos);
						if (innerDist <= Settings.getCollisionDistance2())
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

		if (camController.moved())
			camController.accept();

		modelBatch.end();

		if (Settings.isLightingActive())
		{
			dirLight.set(new Color(0.6f, 0.6f, 0.6f, 0.0f), camController.camera.direction);
			pointLight.set(new Color(0.8f, 0.8f, 0.8f, 0.0f), camController.camera.position, 0.8f);
		}
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
