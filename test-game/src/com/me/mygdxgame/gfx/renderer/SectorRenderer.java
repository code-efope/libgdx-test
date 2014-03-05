package com.me.mygdxgame.gfx.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.gfx.model.CollidableModelInstance;
import com.me.mygdxgame.gfx.sector.BaseSector;
import com.me.mygdxgame.gfx.sector.Portal;

/**
 * this class is used to render every sector within the frustum
 * 
 * @author deDokter
 * 
 */
public class SectorRenderer
{
	private final String className = this.getClass().getSimpleName();
	private final Array<CollidableModelInstance> instances = new Array<CollidableModelInstance>();
	private final Camera subCam;

	public SectorRenderer()
	{
		subCam = new PerspectiveCamera(67, Gdx.graphics.getWidth(),
			Gdx.graphics.getHeight());
		subCam.near = 0.5f;
		subCam.far = 200f;
	}

	private Camera updateFrustum(final Camera camera, final Portal portal)
	{
		subCam.position.set(portal.getBounds().getCenter());
		subCam.direction.set(camera.direction);
		subCam.update();

		return subCam;
	}

	public BaseSector getCurrentSector(final Vector3 position, final BaseSector[] sectors)
	{
		for (BaseSector sector: sectors)
		{
			if (sector.isWithinSector(position))
				return sector;
		}
		return null;
	}

	public Array<CollidableModelInstance> getInstances(final Camera camera, final BaseSector startSector, final boolean followPortals)
	{
		instances.clear();

		if (startSector != null)
		{
			// render current sector
			instances.addAll(startSector.getAllInstances(camera));
	
			if (followPortals)
			{
				for (Portal portal : startSector.getPortals())
				{
					if (camera.frustum.boundsInFrustum(portal.getBounds()))
					{
						// portal is within frustum, render the sector it's pointing to with a frustum according to portal
						if (portal.destination != startSector)
						{
							instances.addAll(portal.destination
								.getAllInstances(updateFrustum(camera, portal)));
						}
					}
				}
			}
		}
		else
			Gdx.app.log(className, "no suitable sector found for " + camera.position);
		return instances;
	}

	public Array<CollidableModelInstance> getInstances(final Camera camera,
		final BaseSector[] sectors, final boolean followPortals)
	{
		return getInstances(camera, getCurrentSector(camera.position, sectors), followPortals);
	}
}
