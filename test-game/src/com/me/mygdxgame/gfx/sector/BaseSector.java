package com.me.mygdxgame.gfx.sector;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.UnalignedBoundingBox;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.gfx.model.CollidableModelInstance;
import com.me.mygdxgame.interfaces.Treeable;
import com.me.mygdxgame.util.ModelBuilderUtil;
import com.me.mygdxgame.util.Settings;

/**
 * a sector is used to traverse the map.
 * other sectors can be accessed by portals which are part of the sector
 * 
 * @author deDokter
 * 
 */
public abstract class BaseSector implements SectorIf, Treeable
{
	/**
	 * the center of the sector
	 */
	protected final Vector3 center = new Vector3();

	/**
	 * walls contained in sector
	 */
	protected Array<BaseWall> walls = new Array<BaseWall>();

	/**
	 * portals for accessing neighboring sectors
	 */
	protected Array<Portal> portals = new Array<Portal>();

	/**
	 * ModelBuilder to build meshes
	 */
	protected final ModelBuilder builder = new ModelBuilder();

	/**
	 * bounding box of sector
	 */
	protected final UnalignedBoundingBox bounds = new UnalignedBoundingBox();
	protected CollidableModelInstance boundsInstance;
	private final Vector3 out = new Vector3();

	@Override
	public void addWall(final BaseWall wall)
	{
		walls.add(wall);
		if (walls.size == 1)
			bounds.set(wall.bounds);
		else
			bounds.ext(wall.bounds);

		Vector3 size = bounds.getDimensions(out);
		boundsInstance = new CollidableModelInstance(ModelBuilderUtil.getInstance().getBox(size.x, size.y, size.z, GL20.GL_LINES, new Color(0.5f, 0.3f, 0.6f, 0.5f)));
		boundsInstance.transform.setToTranslation(bounds.getCenter(out));
		
		/*
		 * when adding a new wall the lowest and highest z-coords are calculated
		 * to determine if a given position is between this two coordinates.
		 */
		if (walls.size == 1)
		{
			
		}
	}

	@Override
	public void addPortal(final Vector3 v1, final Vector3 v2, final BaseSector dest)
	{
		addPortal(v1, v2, dest, true);
	}

	@Override
	public void addPortal(final Vector3 v1, final Vector3 v2, final BaseSector dest, final boolean mirror)
	{
		Portal tmpPortal;
		if (dest != null)
		{
			tmpPortal = new Portal(v1, v2);
			tmpPortal.setDestination(dest);
			portals.add(tmpPortal);
			
			if (mirror)
			{
				// add portal for reverse direction
				Vector3 tmpV1 = new Vector3(v2.x, v1.y, v2.z);
				Vector3 tmpV2 = new Vector3(v1.x, v2.y, v1.z);
				tmpPortal = new Portal(tmpV1, tmpV2);
				tmpPortal.setDestination(this);
				portals.add(tmpPortal);				
			}
		}
	}

	@Override
	public Array<CollidableModelInstance> getAllInstances(final Camera camera)
	{
		Array<CollidableModelInstance> instances = new Array<CollidableModelInstance>();

		for (BaseWall wall : walls)
		{
			if (camera.frustum.boundsInFrustum(wall.bounds))
				instances.add(wall.instance);
		}

		if (Settings.showPortals())
		{
			for (Portal portal : portals)
			{
				if (camera.frustum.boundsInFrustum(portal.bounds))
					instances.add(portal.instance);
			}
		}
		return instances;
	}

	public Array<Portal> getPortals()
	{
		return portals;
	}

	@Override
	public BoundingBox getBounds()
	{
		return bounds;
	}
	
	@Override
	public boolean isWithinSector(Vector3 position)
	{
		for (BaseWall wall: walls)
		{
			int bla = wall.mesh.getNumVertices();
		}
		return false;
	}
}
