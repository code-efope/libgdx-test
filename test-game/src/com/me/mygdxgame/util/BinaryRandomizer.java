package com.me.mygdxgame.util;

import java.util.Random;

public class BinaryRandomizer
{
	private static Random rand = new Random();
	
	public static int get(int times)
	{
		int res = rand.nextInt(2);
		for (int i = 1; i < times; i++)
		{
			res <<= 1;
			res += rand.nextInt(2);
		}
		return res;
	}
}
