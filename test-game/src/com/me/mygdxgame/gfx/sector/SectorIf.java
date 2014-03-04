package com.me.mygdxgame.gfx.sector;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.gfx.model.CollidableModelInstance;

public interface SectorIf
{
	public void addWall(final BaseWall wall);
	public void addPortal(final Vector3 v1, final Vector3 v2, final BaseSector dest, final boolean mirror);
	public void addPortal(final Vector3 v1, final Vector3 v2, final BaseSector dest);

	public Array<CollidableModelInstance> getAllInstances(final Camera camera);
	public boolean isWithinSector(final Vector3 position);
}
