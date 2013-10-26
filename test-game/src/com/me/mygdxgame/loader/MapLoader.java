package com.me.mygdxgame.loader;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.materials.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.me.mygdxgame.scene.octree.OctreeIf;
import com.me.mygdxgame.scene.octree.StaticOctree;
import com.me.mygdxgame.scene.structure.SimpleRoom;
import com.me.mygdxgame.texture.TextureContainer;
import com.me.mygdxgame.util.BinaryRandomizer;
import com.me.mygdxgame.util.DataConverter;

public class MapLoader
{
	private static final int MAX_MAP_OBJECT = 50000;
	private final String mapName;
	private Texture texture;

	public MapLoader(String mapName)
	{
		this.mapName = mapName;
	}
	
	public boolean load(StaticOctree tree)
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
				Gdx.app.log(this.getClass().getName(), "Size: " + sizeX + "/" + sizeY + "/" + sizeZ);
			}
			catch (GdxRuntimeException e)
			{
				e.printStackTrace();
			}
		}
		return res;
	}

	public void load(OctreeIf tree, int type)
	{
		ModelBuilder builder = new ModelBuilder();
		texture = TextureContainer.getTexture();
		Model box = builder.createBox(1.0f, 1.0f, 1.0f, new Material(TextureAttribute.createDiffuse(texture)),
						Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		box.manageDisposable(texture);

		if (type == 1)
		{
			Map<Long, Integer> modelMap = new HashMap<Long, Integer>();
			int rounds = 0;
			int x, y, z;
			while (modelMap.size() < MAX_MAP_OBJECT)
			{
				x = BinaryRandomizer.get(8) - 128;
				y = BinaryRandomizer.get(8) - 128;
				z = BinaryRandomizer.get(8) - 128;

				long key = x * 1000000 + y * 1000 + z;
				if (!modelMap.containsKey(key))
				{
					modelMap.put(key, 0);
					tree.insert(new ModelInstance(box, x, y, z));
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
			for (int x = -150; x < 150; x += 2)
			{
				for (int z = -150; z < 150; z += 2)
				{
					tree.insert(new ModelInstance(box, x, 0, z));
				}
			}
		}
		else if (type == 3)
		{
			for (int roomX = 0; roomX < 2; roomX++)
			{
				for (int roomZ = 0; roomZ < 2; roomZ++)
				{
					SimpleRoom room = new SimpleRoom(roomX * 9, -1, roomZ * 9, 7, 4, 7);
					Array<ModelInstance> instances = room.getInstances();
					for (ModelInstance instance: instances)
						tree.insert(instance);					
				}
			}
		}
	}
}
