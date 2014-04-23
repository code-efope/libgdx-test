package com.me.mygdxgame.loader;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.me.mygdxgame.gfx.model.CollidableModelInstance;
import com.me.mygdxgame.gfx.octree.BaseOctree;
import com.me.mygdxgame.gfx.octree.OctreeIf;
import com.me.mygdxgame.gfx.structure.SimpleRoom;
import com.me.mygdxgame.gfx.structure.SimpleTiledWall;
import com.me.mygdxgame.util.BinaryRandomizer;
import com.me.mygdxgame.util.DataConverter;
import com.me.mygdxgame.util.NoiseGenerator;
import com.me.mygdxgame.util.Settings;
import com.me.mygdxgame.util.TextureProvider;

public class MapLoader
{
	private final String mapName;
	private Texture texture;

	public MapLoader(String mapName)
	{
		this.mapName = mapName;
	}

	public boolean load(OctreeIf tree)
	{
		boolean res = false;

		if (mapName != null)
		{
			byte mapHeader[] = new byte[12];

			try
			{
				FileHandle file = Gdx.files.internal(mapName);
				file.readBytes(mapHeader, 0, mapHeader.length);
				int sizeX = DataConverter.ByteArrayToInt(mapHeader, 0);
				int sizeY = DataConverter.ByteArrayToInt(mapHeader, 4);
				int sizeZ = DataConverter.ByteArrayToInt(mapHeader, 8);
				Gdx.app.log(this.getClass().getName(), "Size: " + sizeX + "/" + sizeY
					+ "/" + sizeZ);
			}
			catch (GdxRuntimeException e)
			{
				e.printStackTrace();
			}
		}
		return res;
	}

	public void load(BaseOctree<CollidableModelInstance> tree, int type)
	{
		ModelBuilder builder = new ModelBuilder();
		texture = TextureProvider.getTexture(-1);
		Model box = builder.createBox(1.0f, 1.0f, 1.0f, //
			new Material(TextureAttribute.createDiffuse(texture)), //
			Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		box.manageDisposable(texture);

		if (type == 1)
		{
			Map<Long, Integer> modelMap = new HashMap<Long, Integer>();
			int rounds = 0;
			int x, y, z;
			while (modelMap.size() < Settings.getMaxObjectCount())
			{
				x = BinaryRandomizer.get(5) - 16;
				y = BinaryRandomizer.get(5) - 16;
				z = BinaryRandomizer.get(5) - 16;

				long key = x * 1000000 + y * 1000 + z;
				if (!modelMap.containsKey(key))
				{
					modelMap.put(key, 0);
					tree.insert(new CollidableModelInstance(box, new Vector3(x, y, z),
						true));
				}
				else
				{
					rounds++;
					if (rounds > 500)
						break;
				}
			}
			Gdx.app.log(this.getClass().getName(), "objects: " + modelMap.size());
		}
		else if (type == 2)
		{
			for (int x = 0; x < 7; x++)
			{
				for (int y = -3; y <= 4; y++)
				{
					for (int z = -3; z <= 4; z++)
					{
						tree.insert(new CollidableModelInstance(box,
							new Vector3(x, y, z), true));
					}
				}
			}
		}
		else if (type == 3)
		{
			for (int roomX = 0; roomX < 10; roomX++)
			{
				for (int roomZ = 0; roomZ < 10; roomZ++)
				{
					SimpleTiledWall wall = new SimpleTiledWall(-10 + roomX * 11, -1, -10
						+ roomZ * 11, 3, 4, 3);
					SimpleRoom room = new SimpleRoom( //
						-10 + roomX * 9, -1, -10 + roomZ * 9, 7, 4, 7);
					Array<CollidableModelInstance> instances = wall.getInstances();
					for (CollidableModelInstance instance : instances)
						tree.insert(instance);
				}
			}
		}
		else if (type == 4)
		{
			for (int x = 0; x < 10; x++)
			{
				for (int y = 0; y < 10; y++)
				{
					for (int z = 0; z < 10; z++)
					{
						double value = NoiseGenerator.noise(x / 10, y / 10, z / 10);
						Gdx.app.log("map", "" + value);
					}
				}
			}
		}
	}
}
