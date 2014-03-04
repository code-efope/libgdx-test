package com.me.mygdxgame.gfx.sector;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.gfx.model.CollidableModelInstance;
import com.me.mygdxgame.gfx.octree.DynamicOctree;

public class SectorBuilder
{
	private final DynamicOctree<BaseSector> tree = new DynamicOctree<BaseSector>(new Vector3(0.0f, 0.0f, 0.0f), 128.0f, 4);
	private final static int numSectorsX = 20;
	private final static int numSectorsZ = 20;

	private final BaseSector sector[];
	private final Vector3[] sectorWalls =
	{
		//
		new Vector3(0.0f, 2.0f, 0.0f), new Vector3(2.0f, 0.0f, 0.0f),
		new Vector3(4.0f, 2.0f, 0.0f), new Vector3(8.0f, 0.0f, 0.0f),
		new Vector3(8.0f, 2.0f, 0.0f), new Vector3(8.0f, 0.0f, 2.0f),
		new Vector3(8.0f, 2.0f, 4.0f), new Vector3(8.0f, 0.0f, 8.0f),
		new Vector3(8.0f, 2.0f, 8.0f), new Vector3(4.0f, 0.0f, 8.0f),
		new Vector3(2.0f, 2.0f, 8.0f), new Vector3(0.0f, 0.0f, 8.0f),
		new Vector3(0.0f, 2.0f, 8.0f), new Vector3(0.0f, 0.0f, 4.0f),
		new Vector3(0.0f, 2.0f, 2.0f), new Vector3(0.0f, 0.0f, 0.0f),//
	};

	private final Vector3[] sectorPortals =
	{
		//
		new Vector3(8.0f, 0.0f, 4.0f), new Vector3(8.0f, 2.0f, 2.0f), // left
		new Vector3(0.0f, 0.0f, 2.0f), new Vector3(0.0f, 2.0f, 4.0f), // right
		new Vector3(2.0f, 0.0f, 8.0f), new Vector3(4.0f, 2.0f, 8.0f), // up
		new Vector3(4.0f, 0.0f, 0.0f), new Vector3(2.0f, 2.0f, 0.0f), // down
	};

	public SectorBuilder(int type)
	{
		if (type == 1)
		{
			sector = new RectangleSector[SectorDataProvider.NUM_SECTORS];
			for (int i = 0; i < SectorDataProvider.NUM_SECTORS; i++)
				sector[i] = new RectangleSector();

			addWalls(sector[0], SectorDataProvider.coords1, new Color(0.0f, 0.0f, 0.9f, 0.5f),
				"blockEmerald");
			addWalls(sector[1], SectorDataProvider.coords2, new Color(0.9f, 0.9f, 0.0f, 0.5f),
				"blockDiamond");
			addWalls(sector[2], SectorDataProvider.coords3, new Color(0.9f, 0.0f, 0.9f, 0.5f),
				"sand");
			addWalls(sector[3], SectorDataProvider.coords4, new Color(0.0f, 0.9f, 0.5f, 0.5f),
				"stonebrick");
			addWalls(sector[4], SectorDataProvider.coords5, new Color(0.1f, 0.4f, 0.6f, 0.5f),
				"clay");
			addWalls(sector[5], SectorDataProvider.coords6, new Color(0.8f, 0.7f, 0.6f, 0.5f),
				"tree_birch");
			addWalls(sector[6], SectorDataProvider.coords7, new Color(0.9f, 0.7f, 0.2f, 0.5f),
				"dirt");
			addWalls(sector[7], SectorDataProvider.coords8, new Color(0.5f, 0.3f, 0.8f, 0.5f),
				"blockLapis");
			addWalls(sector[8], SectorDataProvider.coords9, new Color(0.5f, 0.1f, 0.4f, 0.5f),
				"blockRedstone");

			sector[0].addPortal(new Vector3(-1.0f, 0.0f, 0.0f), //
				new Vector3(1.0f, 2.0f, 0.0f), sector[2]);
			sector[0].addPortal(new Vector3(-3.0f, 0.0f, 4.0f), //
				new Vector3(-2.0f, 2.0f, 6.0f), sector[1]);
			sector[2].addPortal(new Vector3(-8.0f, 0.0f, 0.0f), //
				new Vector3(-8.0f, 2.0f, -3.0f), sector[3]);
			sector[2].addPortal(new Vector3(-1.0f, 0.0f, -8.0f), //
				new Vector3(1.0f, 2.0f, -8.0f), sector[6]);
			sector[2].addPortal(new Vector3(-8.0f, 0.0f, 0.0f), //
				new Vector3(-8.0f, 2.0f, -3.0f), sector[3]);
			sector[2].addPortal(new Vector3(9.0f, 0.0f, -2.0f), //
				new Vector3(9.0f, 2.0f, 0.0f), sector[8]);
			sector[4].addPortal(new Vector3(-13.0f, 0.0f, -3.0f), //
				new Vector3(-14.0f, 2.0f, -2.0f), sector[3]);

			sector[4].addPortal(new Vector3(-12.0f, 0.0f, -8.0f), //
				new Vector3(-14.0f, 2.0f, -8.0f), sector[5]);

			sector[5].addPortal(new Vector3(-14.0f, 0.0f, -8.0f), //
				new Vector3(-12.0f, 2.0f, -8.0f), sector[4]);
			sector[5].addPortal(new Vector3(-7.0f, 0.0f, -11.0f), //
				new Vector3(-7.0f, 2.0f, -13.0f), sector[6]);
			sector[6].addPortal(new Vector3(4.0f, 0.0f, -13.0f), //
				new Vector3(2.0f, 2.0f, -14.0f), sector[7]);
			sector[7].addPortal(new Vector3(11.0f, 0.0f, -11.0f), //
				new Vector3(13.0f, 2.0f, -11.0f), sector[8]);

			// sector[0].addWall(new RectangleWall(new Vector3(0.0f, 0.0f, 0.0f), new Vector3(4.0f, 0.0f, 4.0f)));
		}
		else if (type == 2)
		{
			sector = new RectangleSector[numSectorsX * numSectorsZ];
			Vector3 v1 = new Vector3();
			Vector3 v2 = new Vector3();
			for (int z = 0; z < numSectorsZ; z++)
			{
				for (int x = 0; x < numSectorsX; x++)
				{
					int currentSector = z * numSectorsX + x;
					sector[currentSector] = new RectangleSector(//
						new Vector3(0.0f, 0.0f, 0.0f).add(x * 8.f, 0.0f, z * 8.0f), //
						new Vector3(15.0f, 2.0f, 15.0f).add(x * 8.f, 0.0f, z * 8.0f));
					for (int i = 0; i < 8; i++)
					{
						v1.set(new Vector3(sectorWalls[i * 2])//
							.add(x * 8.f, 0.0f, z * 8.0f));
						v2.set(new Vector3(sectorWalls[i * 2 + 1])//
							.add(x * 8.f, 0.0f, z * 8.0f));
						sector[currentSector].addWall(//
							new RectangleWall(v1, v2, null, "blockIron"));
					}
				}
			}

			// portals should be added *after* all sectors are created
			for (int z = 0; z < numSectorsZ; z++)
			{
				for (int x = 0; x < numSectorsX; x++)
				{
					int currentSector = z * numSectorsX + x;
					if (x < numSectorsX - 1)
					{
						v1.set(new Vector3(sectorPortals[0]).add(x * 8.f, 0.0f, z * 8.0f));
						v2.set(new Vector3(sectorPortals[1]).add(x * 8.f, 0.0f, z * 8.0f));
						sector[currentSector]
							.addPortal(v1, v2, sector[currentSector + 1]);
					}
					if (x >= 1)
					{
						v1.set(new Vector3(sectorPortals[2]).add(x * 8.f, 0.0f, z * 8.0f));
						v2.set(new Vector3(sectorPortals[3]).add(x * 8.f, 0.0f, z * 8.0f));
						sector[currentSector]
							.addPortal(v1, v2, sector[currentSector - 1]);
					}
					if (z < numSectorsZ - 1)
					{
						v1.set(new Vector3(sectorPortals[4]).add(x * 8.f, 0.0f, z * 8.0f));
						v2.set(new Vector3(sectorPortals[5]).add(x * 8.f, 0.0f, z * 8.0f));
						sector[currentSector].addPortal(v1, v2, sector[(z + 1)
							* numSectorsX + x]);
					}
					if (z >= 1)
					{
						v1.set(new Vector3(sectorPortals[6]).add(x * 8.f, 0.0f, z * 8.0f));
						v2.set(new Vector3(sectorPortals[7]).add(x * 8.f, 0.0f, z * 8.0f));
						sector[currentSector].addPortal(v1, v2, sector[(z - 1)
							* numSectorsX + x]);
					}
				}
			}
		}
		else
		{
			sector = new RectangleSector[1];
			sector[0] = new RectangleSector();

			Vector3 edges[] = new Vector3[4];
			edges[0] = new Vector3(0.0f, 0.0f, 0.0f);
			edges[1] = new Vector3(0.0f, 0.0f, 4.0f);
			edges[2] = new Vector3(4.0f, 0.0f, 0.0f);
			edges[3] = new Vector3(4.0f, 0.0f, 4.0f);
			sector[0].addWall(new BaseWall(edges, 0, new Color(0.4f, 0.7f, 0.2f, 1.0f)));
		}
		for (int sectorIndex = 0; sectorIndex < sector.length; sectorIndex++)
			tree.insert(sector[sectorIndex]);
	}

	private void addWalls(final BaseSector sector, final float[] coords,
		final Color color, final String texName)
	{
		for (int i = 0; i < coords.length / 6; i++)
		{
			float x1 = coords[i * 6];
			float y1 = coords[i * 6 + 1];
			float z1 = coords[i * 6 + 2];
			float x2 = coords[i * 6 + 3];
			float y2 = coords[i * 6 + 4];
			float z2 = coords[i * 6 + 5];
			sector.addWall(new RectangleWall(new Vector3(x1, y1, z1), new Vector3(x2, y2,
				z2), color, texName));
		}
	}

	/**
	 * check all sectors for overlaps
	 * @return
	 */
	public int testOverlaps()
	{
		int overlaps = 0;
		
		if (sector.length > 1)
		{
			for (int sectorIndex = 0; sectorIndex < sector.length - 1; sectorIndex++)
			{
				for (int innerIndex = sectorIndex + 1; innerIndex < sector.length; innerIndex++)
				{
					if (sector[sectorIndex].bounds.intersects(sector[innerIndex].bounds))
						overlaps++;
				}
			}
		}
		return overlaps;
	}

	public BaseSector[] getSectors()
	{
		return sector;
	}
	
	public Array<BaseSector> getSector(final Vector3 point)
	{
		return tree.getInstances(point);
	}
	
	public Array<CollidableModelInstance> getTree()
	{
		return tree.getOctreeInstances();
	}
}
