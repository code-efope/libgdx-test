package com.me.mygdxgame.gfx.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.me.mygdxgame.gfx.DaylightSimulation;
import com.me.mygdxgame.gfx.model.CollidableModelInstance;
import com.me.mygdxgame.gfx.sector.BaseSector;
import com.me.mygdxgame.gfx.sector.SectorBuilder;
import com.me.mygdxgame.gfx.sector.Skybox;
import com.me.mygdxgame.input.FPSCameraController;
import com.me.mygdxgame.interfaces.RendererIf;
import com.me.mygdxgame.interfaces.Treeable;
import com.me.mygdxgame.util.DiagnosisDataProvider;
import com.me.mygdxgame.util.DisposableManager;
import com.me.mygdxgame.util.Settings;

public class WorldRenderer implements Disposable, RendererIf
{
	private final String className = this.getClass().getSimpleName();
	private final BlockRenderer blocks = new BlockRenderer();
	private final Vector3 renPos = new Vector3();
	private final Vector3 lastPosition = new Vector3();
	private final Array<CollidableModelInstance> instances;
	private final Skybox skybox = new Skybox();
	private final FPSCameraController camController;
	private final ModelBatch modelBatch = new ModelBatch(new DefaultShaderProvider());;
	private final DirectionalLight dirLight;
	private final PointLight pointLight;
	private final Environment environment;
	private final DisposableManager disManager;
	private final DecalRenderer dr;
	private final SectorRenderer sr = new SectorRenderer();
	private final SectorBuilder sb = new SectorBuilder(1);
	private final BoundingBox cameraBounds = new BoundingBox();
	private final Boolean renderDecals = false;
	private final Boolean renderSkybox = true;
	private final Boolean renderBlocks = true;
	private final Boolean renderSector = false;
	
	public WorldRenderer(FPSCameraController camController)
	{
		this.camController = camController;

		environment = new Environment();
		dirLight = new DirectionalLight();
		pointLight = new PointLight();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.3f, 0.8f, 0.5f));
		environment.set(new ColorAttribute(ColorAttribute.Fog, 0.13f, 0.13f, 0.13f, 1f));
		environment.add(dirLight);
		environment.add(pointLight);
		dr = new DecalRenderer(camController);

		instances = new Array<CollidableModelInstance>();

		disManager = new DisposableManager();
		disManager.addDisposable(modelBatch);
		disManager.addDisposable(skybox);

		Gdx.app.log(className, "has " + sb.testOverlaps() + " overlaps");
	}

	@Override
	public void dispose()
	{
		if (instances != null && instances.size != 0)
			instances.clear();
		disManager.dispose();
	}

	@Override
	public void render()
	{
	}

	@Override
	public void render(float deltaTime)
	{
		boolean checkCollision = false, positionAdjusted = false;
		float dist = 0.0f; // , innerDist;

		DaylightSimulation.updateTime(deltaTime);
		dr.moveDecal("sunDecal", DaylightSimulation.getSunPosition());

		instances.clear();
		DiagnosisDataProvider.numInstances = 0;
		DiagnosisDataProvider.numMeshes = 0;
		DiagnosisDataProvider.numVertices = 0;

		// calculate new position of camera after movement
		camController.update();
		modelBatch.begin(camController.camera);
		
		Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);

		cameraBounds.set(new Vector3(camController.camera.position).sub(0.2f), new Vector3(camController.camera.position).add(0.2f));

		// render skybox
		if (renderSkybox)
		{
			for (CollidableModelInstance instance : skybox.getAllInstances(camController.camera))
				modelBatch.render(instance, environment);
			modelBatch.flush();
		}

		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		// render sector
		if (renderSector)
		{
			// get all sectors
			for (BaseSector s : sb.getSectors())
			{
				s.isWithinSector(camController.camera.position);
				instances.addAll(sr.getInstances(camController.camera, s, true));
			}
/*
			// get visible sectors
			for (BaseSector s : sb.getSector(camController.camera.position))
				instances.addAll(sr.getInstances(camController.camera, s, true));
*/
			// instances.addAll(sr.getInstances(camController.camera, sb.getSectors(), true));
		}

		if (Settings.isCollisionActive() && camController.hasMoved())
		{
			dist = lastPosition.dst2(camController.camera.position);
			if (dist > Settings.getMovementDistance())
				checkCollision = true;
		}
		else
			checkCollision = false;

		// get instances from SceneManager
		if (renderBlocks)
		{
			if (blocks != null)
			{
				for (Treeable t : blocks.getInstances(camController.camera.frustum))
					if (t instanceof CollidableModelInstance)
						instances.add((CollidableModelInstance) t);
			}
			// instances.addAll(scene.getInstances(camController.camera.frustum));
		}

		// process all received instances
		for (CollidableModelInstance instance : instances)
		{
			if (camController.camera.frustum.boundsInFrustum(instance.getBounds()))
			{
				if (Settings.isLightingActive())
					modelBatch.render(instance, environment);
				else
					modelBatch.render(instance);

				DiagnosisDataProvider.numInstances++;
				DiagnosisDataProvider.numMeshes += instance.model.meshParts.size;
				for (int meshIndex = 0; meshIndex < instance.model.meshes.size; meshIndex++)
					DiagnosisDataProvider.numVertices += instance.model.meshes.get(meshIndex).getNumVertices();

				// check collision AFTER rendering
				if (instance.canCollide() && checkCollision)
				{
					// when there was no collision yet
					if (!positionAdjusted)
					{
						// innerDist =
						// camController.camera.position.dst2(instance.getBounds().getCenter());
						// if (innerDist <= Settings.getCollisionDistance2())
						if (cameraBounds.intersects(instance.getBounds()))
						{
							Gdx.app.log(className, "camera " + cameraBounds);
							Gdx.app.log(className, "instance " + instance.getBounds());
							Gdx.app.log(className, "" + camController.camera.position + " collides with " + renPos);
							// + " dist: " + innerDist);
							positionAdjusted = true;
						}
					}
				}
			}
		}

		if (positionAdjusted)
		{
			// don't update camera
			// camController.reject();
			camController.camera.position.set(lastPosition);
			positionAdjusted = false;
		}
		else if (checkCollision)
		{
			// accept movement of camera
			// camController.accept();
			lastPosition.set(camController.camera.position);
			checkCollision = false;
		}

		modelBatch.end();

		if (renderDecals)
			dr.render();

		if (camController.hasMoved())
			camController.accept();

		if (Settings.isLightingActive())
		{
			dirLight.set(DaylightSimulation.getColor(),	camController.camera.direction);
			pointLight.set(new Color(0.8f, 0.8f, 0.8f, 0.0f), camController.camera.position, 1.0f);
		}
	}
}
